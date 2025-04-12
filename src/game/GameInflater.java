package game;

import areas.Area;
import areas.ItemBall;
import enums.MoveRequirement;
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
import trainer.PartySlot;
import trainer.Trainer;
import ui.GameFrame;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameInflater {

    private final static String NO_JAR_MODE_PREFIX = "bin/";
    private final static String NAME_INPUT_TITLE = "Name Entry";
private final static String NAME_INPUT_MESSAGE = "What is your name?";
    private final static String RIVAL_INPUT_TITLE = "Rival Name";
    private final static String RIVAL_INPUT_MESSAGE = "What is your rival's name?";
    private final static String NAME_KEY = "name";
    private final static String ITEMS_KEY = "items";
    private final static String ITEM_KEY = "item";
    private final static String REQUIREMENT_KEY = "requirement";
    private final static String EVENTS_KEY = "events";
    private final static String START_FLAG_KEY = "startFlag";
    private final static String MOVEMENT_FLAGS_KEY = "movementFlags";
    private final static String EVENT_FLAGS_KEY = "eventFlags";
    private final static String SUB_EVENTS_KEY = "subEvents";
    private final static String TYPE_KEY = "type";
    private final static String PROMPT_KEY = "prompt";
    private final static String LEVEL_KEY = "level";
    private final static String BEFORE_KEY = "before";
    private final static String AFTER_KEY = "after";
    private final static String INTRO_KEY = "intro";
    private final static String WIN_MSG_KEY = "winMsg";
    private final static String LOSE_MSG_KEY = "loseMsg";
    private final static String MONEY_KEY = "money";
    private final static String PARTIES_KEY = "parties";
    private final static String AMOUNT_KEY = "amount";
    private final static String SPECIES_KEY = "species";
    private static final String GREETING_KEY = "greeting";
    private static final String PARTY_KEY = "party";
    private static final String DOUBLE_KEY = "double";
    private static final String MOVESET_KEY = "moveset";
    private static final String BADGES_KEY = "badges";
    private static final String RIVAL_KEY = "rival";
    private static final String OPTIONS_KEY = "options";
    private static final String PC_KEY = "pc";
    private static final String BAG_KEY = "bag";
    private final static String POKÉMON_CHOICE_EVENT = "PokémonChoice";
    private final static String POKÉMON_EVENT = "Pokémon";
    private final static String RIVAL_BATTLE_EVENT = "RivalBattle";
    private final static String ITEM_EVENT = "Item";
    private final static String TRAINER_EVENT = "Trainer";
    private final static String WILD_EVENT = "Wild";

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final JSONParser PARSER = new JSONParser();


    public static Game inflateRegion(List<Area> gameMap, String gameFile, boolean newGame,
                                     boolean JAR_MODE, GameFrame gameFrame) throws IOException, BadNameException {
        String prefix = (JAR_MODE) ? "" : NO_JAR_MODE_PREFIX;
        String filePath = prefix + gameFile;
        try (Scanner scanner = new Scanner(new File(filePath)).useDelimiter("\\Z")) {
            String saveData = scanner.next();
            String decodedSave = new String(DECODER.decode(saveData));
            JSONObject saveObj = (JSONObject) PARSER.parse(decodedSave);
            Player player;
            if (newGame) {
                String playerName = gameFrame.getInputHelper().getString(NAME_INPUT_TITLE, NAME_INPUT_MESSAGE);
                String rivalName = gameFrame.getInputHelper().getString(RIVAL_INPUT_TITLE, RIVAL_INPUT_MESSAGE);
                player = new Player(playerName, rivalName);
            } else {
                JSONObject playerObj = (JSONObject) saveObj.get("player");
                player = parsePlayer(playerObj);
            }
            JSONObject areasObj = (JSONObject) saveObj.get("areas");
            inflateAreas(gameMap, areasObj);
            //When parsing areas, I need to check for an "items" key, and if it doesn't exist pass in an empty list for items
            return new Game(gameFrame, gameMap, player);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Parse exception: " + e.getMessage());
        }
        return null;
    }

    private static void inflateAreas(List<Area> gameMap, JSONObject areasObj) throws BadNameException {
        for (Object obj : areasObj.keySet()) {
            JSONObject areaObj = (JSONObject) areasObj.get(obj);
            String name = (String) areaObj.get(NAME_KEY);
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
        if (areaObj.containsKey(ITEMS_KEY)) {
            JSONArray items = (JSONArray) areaObj.get(ITEMS_KEY);
            parseItems(area, items);
        }
        if (areaObj.containsKey(EVENTS_KEY)) {
            JSONArray events = (JSONArray) areaObj.get(EVENTS_KEY);
            parseEvents(area, events);
        }
    }

    private static void parseEvents(Area area, JSONArray events) throws BadNameException {
        List<GameEvent> gameEvents = new ArrayList<>();
        for (Object obj : events) {
            JSONObject eventObj = (JSONObject) obj;
            GameEventBuilder builder = new GameEventBuilder();
            String startFlag = (String) eventObj.get(START_FLAG_KEY);
            builder.addStartFlag(startFlag);
            if (eventObj.containsKey(EVENT_FLAGS_KEY)) {
                JSONArray eventFlagsArray = (JSONArray) eventObj.get(EVENT_FLAGS_KEY);
                List<String> eventFlags = new ArrayList<String>(eventFlagsArray);
                builder.addEventFlags(eventFlags);
            }
            if (eventObj.containsKey(MOVEMENT_FLAGS_KEY)) {
                JSONArray movementFlagsArray = (JSONArray) eventObj.get(MOVEMENT_FLAGS_KEY);
                List<MovementFlag> movementFlagList = new ArrayList<>();
                for (Object flagObj : movementFlagsArray) {
                    List<String> movementFlag = (List<String>) flagObj;
                    MovementFlag flag = new MovementFlag(movementFlag.get(0), movementFlag.get(1));
                    movementFlagList.add(flag);
                }
                builder.addMovementFlags(movementFlagList);
            }
            JSONArray subEventsArray = (JSONArray) eventObj.get(SUB_EVENTS_KEY);
            LinkedList<SubEvent> subEvents = parseSubEvents(subEventsArray);
            builder.addSubEvents(subEvents);
            gameEvents.add(builder.build());
        }
        area.setEvents(gameEvents);
    }

    private static LinkedList<SubEvent> parseSubEvents(JSONArray subEventsArray) throws BadNameException {

        LinkedList<SubEvent> subEvents = new LinkedList<>();
        for (Object obj : subEventsArray) {
            JSONObject subEventObj = (JSONObject) obj;
            String type = (String) subEventObj.get(TYPE_KEY);;
            SubEvent subEvent;
            if (type.equals(POKÉMON_EVENT)) {
                subEvent = parsePokémonEvent(subEventObj);
            } else if (type.equals(POKÉMON_CHOICE_EVENT)) {
                subEvent = parsePokémonChoiceEvent(subEventObj);
            } else if (type.equals(RIVAL_BATTLE_EVENT)) {
                subEvent = parseRivalBattleEvent(subEventObj);
            } else if (type.equals(TRAINER_EVENT)) {
                subEvent = parseTrainerEvent(subEventObj);
            } else if (type.equals(ITEM_EVENT)) {
                subEvent = parseItemEvent(subEventObj);
            } else {
                subEvent = parseWildEvent(subEventObj);
            }
            subEvents.add(subEvent);
        }
        return subEvents;
    }

    private static SubEvent parsePokémonEvent(JSONObject eventObj) throws BadNameException {

        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String speciesString = (String) eventObj.get(SPECIES_KEY);
        Species species = Species.map(speciesString);
        int level = ((Long) eventObj.get(LEVEL_KEY)).intValue();
        if (eventObj.containsKey(ITEM_KEY)) {
            String itemString = (String) eventObj.get(ITEM_KEY);
            Item item = ItemMapper.map(itemString);
            return new PokémonEvent(species, level, item, before, after);
        }
        return new PokémonEvent(species, level, before, after);
    }

    private static SubEvent parsePokémonChoiceEvent(JSONObject eventObj) throws BadNameException {
        String prompt = (String) eventObj.get(PROMPT_KEY);
        int level = ((Long) eventObj.get(LEVEL_KEY)).intValue();
        JSONArray optionsArray = (JSONArray) eventObj.get(OPTIONS_KEY);
        List<Species> options = new ArrayList<>();
        for (Object obj : optionsArray) {
            String speciesString = (String) obj;
            Species species = Species.map(speciesString);
            options.add(species);
        }
        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        return new PokémonChoiceEvent(prompt, options, level, before, after);
    }

    private static SubEvent parseRivalBattleEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String intro = (String) eventObj.get(INTRO_KEY);
        String winMsg = (String) eventObj.get(WIN_MSG_KEY);
        String loseMSg = (String) eventObj.get(LOSE_MSG_KEY);
        int money = ((Long) eventObj.get(MONEY_KEY)).intValue();
        List<List<PartySlot>> potentialParties = new ArrayList<>();
        JSONArray jsonParties = (JSONArray) eventObj.get(PARTIES_KEY);
        for (Object partyObj : jsonParties) {
            JSONArray jsonParty = (JSONArray) partyObj;
            List<PartySlot> party = parseParty(jsonParty);
            potentialParties.add(party);
        }
        return new RivalBattleEvent(potentialParties, intro, winMsg, loseMSg, money, before, after);
    }

    private static SubEvent parseTrainerEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String type = (String) eventObj.get(TYPE_KEY);
        String name = (String) eventObj.get(NAME_KEY);
        int money = ((Long) eventObj.get(MONEY_KEY)).intValue();
        String winMsg = (String) eventObj.get(WIN_MSG_KEY);
        String loseMsg = (String) eventObj.get(LOSE_MSG_KEY);
        String greeting = (String) eventObj.get(GREETING_KEY);
        JSONArray partySlotsArray = (JSONArray) eventObj.get(PARTY_KEY);
        List<PartySlot> partySlots = parseParty(partySlotsArray);
        boolean isDoubleBattle = (boolean) eventObj.get(DOUBLE_KEY);
        Trainer trainer = new Trainer(type, name, greeting, winMsg, loseMsg, money, isDoubleBattle, partySlots);
        return new TrainerEvent(trainer, before, after);
    }

    private static List<PartySlot> parseParty(JSONArray partySlotsArray) throws BadNameException {
        List<PartySlot> toReturn = new ArrayList<>();
        for (Object obj : partySlotsArray) {
            JSONObject slotObj = (JSONObject) obj;
            String speciesString = (String) slotObj.get(SPECIES_KEY);
            Species species = Species.map(speciesString);
            int level = ((Long) slotObj.get(LEVEL_KEY)).intValue();
            PartySlot slot = new PartySlot(species, level);
            if (slotObj.containsKey(ITEM_KEY)) {
                String itemString = (String) slotObj.get(ITEM_KEY);
                Item item = ItemMapper.map(itemString);
                slot.setItem(item);
            }
            if (slotObj.containsKey(MOVESET_KEY)) {
                //TODO
            }
            toReturn.add(slot);
        }
        return toReturn;
    }

    private static SubEvent parseItemEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String itemString = (String) eventObj.get(ITEM_KEY);
        Item item = ItemMapper.map(itemString);
        int amount = ((Long) eventObj.get(AMOUNT_KEY)).intValue();
        return new ItemEvent(item, amount, before, after);
    }

    private static SubEvent parseWildEvent(JSONObject eventObj) throws BadNameException {
        JSONArray beforeArray = (JSONArray) eventObj.get(BEFORE_KEY);
        List<String> before = new ArrayList<>(beforeArray);
        JSONArray afterArray = (JSONArray) eventObj.get(AFTER_KEY);
        List<String> after = new ArrayList<>(afterArray);
        String speciesString = (String) eventObj.get(SPECIES_KEY);
        Species species = Species.map(speciesString);
        int level = ((Long) eventObj.get(LEVEL_KEY)).intValue();
        if (eventObj.containsKey(ITEM_KEY)) {
            String itemString = (String) eventObj.get(ITEM_KEY);
            Item item = ItemMapper.map(itemString);
            return new WildEvent(species, level, item, before, after);
        }
        return new WildEvent(species, level, before, after);
    }

    private static void parseItems(Area area, JSONArray items) throws BadNameException {
        List<ItemBall> itemBalls = new ArrayList<>();
        for (Object obj : items) {
            JSONObject itemObj = (JSONObject) obj;
            Item item = ItemMapper.map((String) itemObj.get(ITEM_KEY));
            MoveRequirement moveRequirement = MoveRequirement.map((String) itemObj.get(REQUIREMENT_KEY));
            ItemBall itemBall = new ItemBall(item, moveRequirement);
            itemBalls.add(itemBall);
        }
        area.setItems(itemBalls);
    }

    private static Player parsePlayer(JSONObject playerObj) throws BadNameException {
        String name = (String) playerObj.get(NAME_KEY);
        String rival = (String) playerObj.get(RIVAL_KEY);
        JSONArray badgeArray = (JSONArray) playerObj.get(BADGES_KEY);
        ArrayList<String> badges = new ArrayList<>(badgeArray);
        Bag bag = new Bag();
        JSONArray bagJSON = (JSONArray) playerObj.get(BAG_KEY);
        for (Object object : bagJSON) {
            JSONObject jsonItem = (JSONObject) object;
            Item item = ItemMapper.map((String) jsonItem.get(ITEM_KEY));
            int amount = ((Long) jsonItem.get(AMOUNT_KEY)).intValue();
            bag.addItem(item, amount);
        }
        JSONArray partyArray = (JSONArray) playerObj.get(PARTY_KEY);
        ArrayList<Pokémon> party = parseRichMonList(partyArray);
        JSONArray pcArray = (JSONArray) playerObj.get(PC_KEY);
        ArrayList<Pokémon> pc = parseRichMonList(pcArray);
        int money = ((Long) playerObj.get(MONEY_KEY)).intValue();
        return new Player(name, rival, money, badges, party, pc, bag);
    }

    private static ArrayList<Pokémon> parseRichMonList(JSONArray partyArray) {
        //TODO
        return null;
    }
}
