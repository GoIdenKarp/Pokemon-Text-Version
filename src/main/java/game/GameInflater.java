package game;

import areas.Area;
import areas.ItemBall;
import enums.MoveRequirement;
import enums.Owner;
import enums.Species;
import events.*;
import exceptions.BadNameException;
import items.Bag;
import items.Item;
import items.ItemMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pokémon.Pokémon;
import pokémon.PokémonFactory;
import trainer.PartySlot;
import trainer.Trainer;
import ui.GameFrame;
import util.Keys;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.*;

public class GameInflater {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final JSONParser PARSER = new JSONParser();
    private final static String PALLET_TOWN = "Pallet Town";
    private static final String SAVE_DIR = System.getProperty("user.home") + "/.pokemon-text-version/saves/";

    private static String getSaveFilePath(String filename) {
        // Create save directory if it doesn't exist
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        return SAVE_DIR + filename;
    }

    public static Game inflateRegion(List<Area> gameMap, String gameFile, boolean newGame,
                                     boolean JAR_MODE, GameFrame gameFrame) throws IOException, BadNameException {
        System.out.println("Loading game file: " + gameFile);
        try {
            InputStream inputStream;
            if (newGame) {
                // For new games, load from resources
                inputStream = GameInflater.class.getClassLoader().getResourceAsStream(gameFile);
                if (inputStream == null) {
                    try {
                        java.net.URL url = GameInflater.class.getClassLoader().getResource("data");
                        if (url != null) {
                            java.io.File dataDir = new java.io.File(url.toURI());
                            if (dataDir.exists() && dataDir.isDirectory()) {
                                for (String file : dataDir.list()) {
                                    System.out.println("  - " + file);
                                }
                            }
                        } else {
                            System.out.println("data directory not found");
                        }
                    } catch (Exception e) {
                        System.out.println("Error checking resources: " + e.getMessage());
                    }
                    throw new FileNotFoundException("Resource not found: " + gameFile);
                }
            } else {
                // For saved games, load from save directory
                String savePath = getSaveFilePath(gameFile);
                inputStream = new FileInputStream(savePath);
            }
                        
            try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\Z")) {
                PokémonFactory factory = new PokémonFactory(gameFrame.getInputHelper(), gameFrame.getGamePrinter());
                String saveData = scanner.next();
                String decodedSave = new String(DECODER.decode(saveData));
                JSONObject saveObj = (JSONObject) PARSER.parse(decodedSave);
                //If we are a new game, we create the player later
                Player player = null;
                String currentArea = PALLET_TOWN;
                //If we are not a new game, we never need the prologue
                List<String> prologue = null;
                String lastVisited = "";
                HashSet<String> flyOptions = new HashSet<>();
                Set<String> eventFlags = new HashSet<>(Collections.singleton(""));
                if (newGame) {
                    JSONArray prologueObj = (JSONArray) saveObj.get(Keys.PROLOGUE_KEY);
                    prologue = new ArrayList<>(prologueObj);
                } else {
                    JSONObject playerObj = (JSONObject) saveObj.get(Keys.PLAYER_KEY);
                    player = parsePlayer(playerObj, factory);
                    currentArea = (String) playerObj.get(Keys.CURR_AREA_KEY);
                    JSONObject extrasObj = (JSONObject) saveObj.get(Keys.EXTRAS_KEY);
                    lastVisited = (String) extrasObj.get(Keys.LAST_VISITED_KEY);
                    JSONArray flyOptionsArray = (JSONArray) extrasObj.get(Keys.FLYABLE_KEY);
                    for (Object option : flyOptionsArray) {
                        flyOptions.add((String) option);
                    }
                    JSONArray eventFlagsArray = (JSONArray) extrasObj.get(Keys.EVENT_FLAGS_KEY);
                    for (Object flag : eventFlagsArray) {
                        eventFlags.add((String) flag);
                    }
                }
                JSONObject areasObj = (JSONObject) saveObj.get(Keys.AREAS_KEY);
                //When parsing areas, I need to check for an "items" key, and if it doesn't exist pass in an empty list for items
                inflateAreas(gameMap, areasObj);
                JSONArray connectionsObj = (JSONArray) saveObj.get(Keys.CONNECTIONS_KEY);
                addConnections(gameMap, connectionsObj);
                String saveFile = newGame ? "" : gameFile.split("\\.")[0];
                System.out.println("FOO1 eventFlags: " + eventFlags);
                Game game = new Game(gameFrame, gameMap, player, prologue, currentArea, saveFile, lastVisited, flyOptions, eventFlags);
                return game;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Parse exception: " + e.getMessage());
            throw new RuntimeException("Failed to parse game file: " + gameFile, e);
        }
    }

    private static void addConnections(List<Area> regionMap, JSONArray connections) {
        for (Object obj : connections) {
            JSONObject xObj = (JSONObject) obj;
            String first = (String) xObj.get(Keys.FIRST_KEY);
            String second = (String) xObj.get(Keys.SECOND_KEY);
            MoveRequirement requirement = MoveRequirement.map((String) xObj.get(Keys.REQUIREMENT_KEY));
            //have to initialize to null, but in practice the values should always be updated
            Area areaOne = null;
            Area areaTwo = null;
            for (Area area : regionMap) {
                if (area.getName().equals(first)) {
                    areaOne = area;
                } else if (area.getName().equals(second)) {
                    areaTwo = area;
                }
            }
            areaOne.addConnection(areaTwo, requirement);
            areaTwo.addConnection(areaOne, requirement);
        }
    }

    private static void inflateAreas(List<Area> gameMap, JSONObject areasObj) throws BadNameException {
        for (Object obj : areasObj.keySet()) {
            JSONObject areaObj = (JSONObject) areasObj.get(obj);
            String name = (String) areaObj.get(Keys.NAME_KEY);
            Area toInflate = null;
            for (Area area : gameMap) {
                if (name.equals(area.getName())) {
                    toInflate = area;
                    break;
                }
            }
            if (toInflate != null) {
                inflateArea(toInflate, areaObj);
            } else {
                throw new Error("No Area found for " + name);
            }
        }
    }

    private static void inflateArea(Area area, JSONObject areaObj) throws BadNameException {
        if (areaObj.containsKey(Keys.ITEMS_KEY)) {
            JSONArray items = (JSONArray) areaObj.get(Keys.ITEMS_KEY);
            parseItems(area, items);
        }
        if (areaObj.containsKey(Keys.EVENTS_KEY)) {
            JSONArray events = (JSONArray) areaObj.get(Keys.EVENTS_KEY);
            parseEvents(area, events);
        }
    }

    private static void parseEvents(Area area, JSONArray events) throws BadNameException {
        List<GameEvent> gameEvents = new ArrayList<>();
        for (Object obj : events) {
            JSONObject eventObj = (JSONObject) obj;
            GameEventBuilder builder = new GameEventBuilder();
            String startFlag = (String) eventObj.get(Keys.START_FLAG_KEY);
            builder.addStartFlag(startFlag);
            if (eventObj.containsKey(Keys.EVENT_FLAGS_KEY)) {
                JSONArray eventFlagsArray = (JSONArray) eventObj.get(Keys.EVENT_FLAGS_KEY);
                List<String> eventFlags = new ArrayList<String>(eventFlagsArray);
                builder.addEventFlags(eventFlags);
            }
            if (eventObj.containsKey(Keys.MOVEMENT_FLAGS_KEY)) {
                JSONArray movementFlagsArray = (JSONArray) eventObj.get(Keys.MOVEMENT_FLAGS_KEY);
                List<MovementFlag> movementFlagList = new ArrayList<>();
                for (Object flagObj : movementFlagsArray) {
                    List<String> movementFlag = (List<String>) flagObj;
                    MovementFlag flag = new MovementFlag(movementFlag.get(0), movementFlag.get(1));
                    movementFlagList.add(flag);
                }
                builder.addMovementFlags(movementFlagList);
            }
            JSONArray subEventsArray = (JSONArray) eventObj.get(Keys.SUB_EVENTS_KEY);
            LinkedList<SubEvent> subEvents = parseSubEvents(subEventsArray);
            builder.addSubEvents(subEvents);
            boolean resetOnFail = (boolean) eventObj.getOrDefault(Keys.RESET_ON_FAIL_KEY, true);
            boolean ignoreFail = (boolean) eventObj.getOrDefault(Keys.IGNORE_FAIL_KEY, false);
            builder.addResetOnFail(resetOnFail);
            builder.addIgnoreFail(ignoreFail);
            gameEvents.add(builder.build());
        }
        area.setEvents(gameEvents);
    }

    private static LinkedList<SubEvent> parseSubEvents(JSONArray subEventsArray) throws BadNameException {

        LinkedList<SubEvent> subEvents = new LinkedList<>();
        for (Object obj : subEventsArray) {
            JSONObject subEventObj = (JSONObject) obj;
            String type = (String) subEventObj.get(Keys.TYPE_KEY);;
            SubEvent subEvent;
            if (type.equals(Keys.POKÉMON_EVENT)) {
                subEvent = parsePokémonEvent(subEventObj);
            } else if (type.equals(Keys.POKÉMON_CHOICE_EVENT)) {
                subEvent = parsePokémonChoiceEvent(subEventObj);
            } else if (type.equals(Keys.RIVAL_BATTLE_EVENT)) {
                subEvent = parseRivalBattleEvent(subEventObj);
            } else if (type.equals(Keys.TRAINER_EVENT)) {
                subEvent = parseTrainerEvent(subEventObj);
            } else if (type.equals(Keys.ITEM_EVENT)) {
                subEvent = parseItemEvent(subEventObj);
            } else {
                subEvent = parseWildEvent(subEventObj);
            }
            subEvents.add(subEvent);
        }
        return subEvents;
    }

    private static SubEvent parsePokémonEvent(JSONObject eventObj) throws BadNameException {

        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String speciesString = (String) eventObj.get(Keys.SPECIES_KEY);
        Species species = Species.map(speciesString);
        int level = ((Long) eventObj.get(Keys.LEVEL_KEY)).intValue();
        if (eventObj.containsKey(Keys.ITEM_KEY)) {
            String itemString = (String) eventObj.get(Keys.ITEM_KEY);
            Item item = ItemMapper.map(itemString);
            return new PokémonEvent(species, level, item, before, after);
        }
        return new PokémonEvent(species, level, before, after);
    }

    private static SubEvent parsePokémonChoiceEvent(JSONObject eventObj) throws BadNameException {
        String prompt = (String) eventObj.get(Keys.PROMPT_KEY);
        int level = ((Long) eventObj.get(Keys.LEVEL_KEY)).intValue();
        JSONArray optionsArray = (JSONArray) eventObj.get(Keys.OPTIONS_KEY);
        List<Species> options = new ArrayList<>();
        for (Object obj : optionsArray) {
            String speciesString = (String) obj;
            Species species = Species.map(speciesString);
            options.add(species);
        }
        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        return new PokémonChoiceEvent(prompt, options, level, before, after);
    }

    private static SubEvent parseRivalBattleEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String intro = (String) eventObj.get(Keys.INTRO_KEY);
        String winMsg = (String) eventObj.get(Keys.WIN_MSG_KEY);
        String loseMSg = (String) eventObj.get(Keys.LOSE_MSG_KEY);
        int money = ((Long) eventObj.get(Keys.MONEY_KEY)).intValue();
        List<List<PartySlot>> potentialParties = new ArrayList<>();
        JSONArray jsonParties = (JSONArray) eventObj.get(Keys.PARTIES_KEY);
        for (Object partyObj : jsonParties) {
            JSONArray jsonParty = (JSONArray) partyObj;
            List<PartySlot> party = parseParty(jsonParty);
            potentialParties.add(party);
        }
        return new RivalBattleEvent(potentialParties, intro, winMsg, loseMSg, money, before, after);
    }

    private static SubEvent parseTrainerEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String type = (String) eventObj.get(Keys.TYPE_KEY);
        String name = (String) eventObj.get(Keys.NAME_KEY);
        int money = ((Long) eventObj.get(Keys.MONEY_KEY)).intValue();
        String winMsg = (String) eventObj.get(Keys.WIN_MSG_KEY);
        String loseMsg = (String) eventObj.get(Keys.LOSE_MSG_KEY);
        String greeting = (String) eventObj.get(Keys.GREETING_KEY);
        JSONArray partySlotsArray = (JSONArray) eventObj.get(Keys.PARTY_KEY);
        List<PartySlot> partySlots = parseParty(partySlotsArray);
        boolean isDoubleBattle = (boolean) eventObj.get(Keys.DOUBLE_KEY);
        Trainer trainer = new Trainer(type, name, greeting, winMsg, loseMsg, money, isDoubleBattle, partySlots);
        return new TrainerEvent(trainer, before, after);
    }

    private static List<PartySlot> parseParty(JSONArray partySlotsArray) throws BadNameException {
        List<PartySlot> toReturn = new ArrayList<>();
        for (Object obj : partySlotsArray) {
            JSONObject slotObj = (JSONObject) obj;
            String speciesString = (String) slotObj.get(Keys.SPECIES_KEY);
            Species species = Species.map(speciesString);
            int level = ((Long) slotObj.get(Keys.LEVEL_KEY)).intValue();
            PartySlot slot = new PartySlot(species, level);
            if (slotObj.containsKey(Keys.ITEM_KEY)) {
                String itemString = (String) slotObj.get(Keys.ITEM_KEY);
                Item item = ItemMapper.map(itemString);
                slot.setItem(item);
            }
            if (slotObj.containsKey(Keys.MOVESET_KEY)) {
                //TODO
            }
            toReturn.add(slot);
        }
        return toReturn;
    }

    private static SubEvent parseItemEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String itemString = (String) eventObj.get(Keys.ITEM_KEY);
        Item item = ItemMapper.map(itemString);
        int amount = ((Long) eventObj.get(Keys.AMOUNT_KEY)).intValue();
        return new ItemEvent(item, amount, before, after);
    }

    private static SubEvent parseWildEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(Keys.BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(Keys.AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String speciesString = (String) eventObj.get(Keys.SPECIES_KEY);
        Species species = Species.map(speciesString);
        int level = ((Long) eventObj.get(Keys.LEVEL_KEY)).intValue();
        if (eventObj.containsKey(Keys.ITEM_KEY)) {
            String itemString = (String) eventObj.get(Keys.ITEM_KEY);
            Item item = ItemMapper.map(itemString);
            return new WildEvent(species, level, item, before, after);
        }
        return new WildEvent(species, level, before, after);
    }

    private static void parseItems(Area area, JSONArray items) throws BadNameException {
        List<ItemBall> itemBalls = new ArrayList<>();
        for (Object obj : items) {
            JSONObject itemObj = (JSONObject) obj;
            Item item = ItemMapper.map((String) itemObj.get(Keys.ITEM_KEY));
            MoveRequirement moveRequirement = MoveRequirement.map((String) itemObj.get(Keys.REQUIREMENT_KEY));
            ItemBall itemBall = new ItemBall(item, moveRequirement);
            itemBalls.add(itemBall);
        }
        area.setItems(itemBalls);
    }

    private static Player parsePlayer(JSONObject playerObj, PokémonFactory factory) throws BadNameException {
        String name = (String) playerObj.get(Keys.NAME_KEY);
        String rival = (String) playerObj.get(Keys.RIVAL_KEY);
        JSONArray badgeArray = (JSONArray) playerObj.get(Keys.BADGES_KEY);
        ArrayList<String> badges = new ArrayList<>(badgeArray);
        Bag bag = new Bag();
        JSONArray bagJSON = (JSONArray) playerObj.get(Keys.BAG_KEY);
        for (Object object : bagJSON) {
            JSONObject jsonItem = (JSONObject) object;
            Item item = ItemMapper.map((String) jsonItem.get(Keys.ITEM_KEY));
            int amount = ((Long) jsonItem.get(Keys.AMOUNT_KEY)).intValue();
            bag.addItem(item, amount);
        }
        JSONArray partyArray = (JSONArray) playerObj.get(Keys.PARTY_KEY);
        ArrayList<Pokémon> party = parseRichMonList(partyArray, factory);
        JSONArray pcArray = (JSONArray) playerObj.get(Keys.PC_KEY);
        ArrayList<Pokémon> pc = parseRichMonList(pcArray, factory);
        int money = ((Long) playerObj.get(Keys.MONEY_KEY)).intValue();
        return new Player(name, rival, money, badges, party, pc, bag);
    }

    private static ArrayList<Pokémon> parseRichMonList(JSONArray partyArray, PokémonFactory factory) throws BadNameException {
        ArrayList<Pokémon> toReturn = new ArrayList<>();
        for (Object obj : partyArray) {
            JSONObject monObj = (JSONObject) obj;
            Pokémon mon = parseRichMon(monObj, factory);
            toReturn.add(mon);
        }
        return toReturn;
    }

    private static Pokémon parseRichMon(JSONObject monObj, PokémonFactory factory) throws BadNameException {
        String speciesString = (String) monObj.get(Keys.SPECIES_KEY);
        Species species = Species.map(speciesString);
        String name = (String) monObj.get(Keys.NAME_KEY);
        int level = ((Long) monObj.get(Keys.LEVEL_KEY)).intValue();
        return factory.makePokémon(species, level, Owner.PLAYER);
    }
}
