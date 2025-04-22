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
import moves.Move;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pokémon.Pokémon;
import pokémon.PokémonFactory;
import trainer.PartySlot;
import trainer.Trainer;
import util.Keys;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import ui.GameFrame;
import java.io.File;

public class GameSaver {

    private static String encode(String toEncode) {
        return encoder.encodeToString(toEncode.getBytes());
    }

    public static final String FILETYPE = ".ptvsav";
    public static final String JSON_EXTENSION = ".json";

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final String SAVE_DIR = System.getProperty("user.home") + "/.pokemon-text-version/saves/";

    private static String getSaveFilePath(String filename) {
        // Create save directory if it doesn't exist
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        return SAVE_DIR + filename;
    }

    /**
     * Converts the game state to an encoded string and writes it to a file
     * @param game
     * @param saveName
     * @return
     */
    public static boolean save(Game game, String saveName) {
        String filename = saveName + FILETYPE;
        try {
            String savePath = getSaveFilePath(filename);
            
            PrintWriter writer = new PrintWriter(savePath);
            JSONObject saveData = new JSONObject();
            saveData.put(Keys.PLAYER_KEY, writePlayer(game.getPlayer(), game.getCurrentArea()));
            saveData.put(Keys.AREAS_KEY, writeGameState(game));
            saveData.put(Keys.CONNECTIONS_KEY, writeConnections(game.getWorldMap()));
            saveData.put(Keys.EXTRAS_KEY, writeExtras(game));
            
            String saveString = encode(saveData.toJSONString());
            writer.println(saveString);
            writer.flush();
            writer.close();

            // Also save a readable JSON version for debugging
            String jsonPath = getSaveFilePath(saveName + JSON_EXTENSION);
            PrintWriter jsonWriter = new PrintWriter(jsonPath);
            jsonWriter.println(saveData.toJSONString());
            jsonWriter.flush();
            jsonWriter.close();

            return true;
        } catch (FileNotFoundException | BadNameException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static JSONObject writeExtras(Game game) {
        JSONObject extrasObj = new JSONObject();
        extrasObj.put(Keys.LAST_VISITED_KEY, game.getLastHealingArea().getName());
        extrasObj.put(Keys.FLYABLE_KEY, game.getFlyOptions().stream().map(Area::getName).toArray(String[]::new));
        return extrasObj;
    }

    //Connections have to be written as part of save in case we have passed events that lift movement restrictions
    private static JSONArray writeConnections(Map<String, Area> worldMap) {
        JSONArray connections = new JSONArray();
        Set<String> processedConnections = new HashSet<>();
        
        for (Area area : worldMap.values()) {
            String areaName = area.getName();
            
            for (Area connectedArea : area.getConnections()) {
                String connectedName = connectedArea.getName();
                
                // Create a unique key for this connection
                String connectionKey = areaName.compareTo(connectedName) < 0 
                    ? areaName + "|" + connectedName 
                    : connectedName + "|" + areaName;
                    
                // Only process if we haven't seen this connection before
                if (!processedConnections.contains(connectionKey)) {
                    processedConnections.add(connectionKey);
                    
                    JSONObject connectionObj = new JSONObject();
                    // Always put the alphabetically earlier name first
                    if (areaName.compareTo(connectedName) < 0) {
                        connectionObj.put(Keys.FIRST_KEY, areaName);
                        connectionObj.put(Keys.SECOND_KEY, connectedName);
                        connectionObj.put(Keys.REQUIREMENT_KEY, 
                            area.getMovePermissions().get(connectedArea).toString());
                    } else {
                        connectionObj.put(Keys.FIRST_KEY, connectedName);
                        connectionObj.put(Keys.SECOND_KEY, areaName);
                        connectionObj.put(Keys.REQUIREMENT_KEY, 
                            connectedArea.getMovePermissions().get(area).toString());
                    }
                    connections.add(connectionObj);
                }
            }
        }
        return connections;
    }

    private static JSONObject writeGameState(Game game) {
        JSONObject gameObj = new JSONObject();
        for (Area area : game.getWorldMap().values()) {
            gameObj.put(area.getName(), writeArea(area));
        }
        return gameObj;
    }

    private static JSONObject writeArea(Area area) {
        JSONObject areaObj = new JSONObject();
        areaObj.put(Keys.NAME_KEY, area.getName());
        areaObj.put(Keys.EVENTS_KEY, writeEvents(area.getEvents()));
        areaObj.put(Keys.TRAINERS_KEY, writeTrainers(area.getTrainers()));
        //areaObj.put(Keys.DEFEATED_KEY, writeTrainers(area.getDefeatedTrainers()));
        areaObj.put(Keys.ITEMS_KEY, writeAreaItems(area.getItems()));
        //areaObj.put(Keys.MOVEMENTS_KEY, writeMovements(area.getMovePermissions()));
        return areaObj;

    }

    private static JSONArray writeAreaItems(List<ItemBall> items) {
        JSONArray areaItems = new JSONArray();
        for (ItemBall itemBall : items) {
            JSONObject itemObj = new JSONObject();
            itemObj.put(Keys.ITEM_KEY, itemBall.getItem().getEncodedName());
            itemObj.put(Keys.REQUIREMENT_KEY, itemBall.getObtainRequirement().toString());
            areaItems.add(itemObj);
        }
        return areaItems;
    }

    private static JSONArray writeMovements(Map<Area, MoveRequirement> movePermissions) {
        JSONArray movements = new JSONArray();
        for (Area area : movePermissions.keySet()) {
            JSONObject permObj = new JSONObject();
            permObj.put(area.getName(), movePermissions.get(area).ordinal());
            movements.add(permObj);
        }
        return movements;
    }

    private static JSONArray writeTrainers(List<Trainer> trainers) {
        JSONArray trainerList = new JSONArray();
        for (Trainer trainer : trainers) {
            trainerList.add(writeTrainer(trainer));
        }
        return trainerList;
    }

    private static JSONObject writeTrainer(Trainer trainer) {
        JSONObject trainerObj = new JSONObject();
        trainerObj.put(Keys.TYPE_KEY, trainer.getType());
        trainerObj.put(Keys.NAME_KEY, trainer.getName());
        trainerObj.put(Keys.GREETING_KEY, trainer.getGreeting());
        trainerObj.put(Keys.WIN_MSG_KEY, trainer.getWinMsg());
        trainerObj.put(Keys.LOSE_MSG_KEY, trainer.getLoseMsg());
        trainerObj.put(Keys.MONEY_KEY, trainer.getPrizeMoney());
        trainerObj.put(Keys.PARTY_KEY, writeTrainerMonList(trainer.getParty()));
        trainerObj.put(Keys.DOUBLE_KEY, trainer.isDoubleBattle());
        return trainerObj;
    }

    private static JSONObject writeWildEvent(WildEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.WILD_EVENT);
        eventObj.put(Keys.SPECIES_KEY, event.getMon().ordinal());
        eventObj.put(Keys.LEVEL_KEY, event.getLevel());
        if (event.getItem() != null) {
            eventObj.put(Keys.ITEM_KEY, event.getItem().getEncodedName());
        }
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        return eventObj;
    }

    private static JSONArray writeEvents(List<GameEvent> events) {
        JSONArray eventList = new JSONArray();
        for (GameEvent event : events) {
            eventList.add(writeEvent(event));
        }
        return eventList;
    }

    private static JSONObject writeEvent(GameEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.SUB_EVENTS_KEY, writeSubEvents(event.getMasterList()));
        eventObj.put(Keys.EVENT_FLAGS_KEY, event.getEventFlagsToLift());
        eventObj.put(Keys.RESET_ON_FAIL_KEY, event.resetOnFail());
        eventObj.put(Keys.IGNORE_FAIL_KEY, event.ignoreFail());
        if (!event.getCanStartFlag().isEmpty()) {
            eventObj.put(Keys.CAN_START_KEY, event.getCanStartFlag());
        }
        JSONArray JSONmovementFlags = new JSONArray();
        for (MovementFlag movementFlag : event.getMovementFlagsToLift()) {
            JSONArray movementFlagArray = new JSONArray();
            movementFlagArray.add(movementFlag.getAreaOne());
            movementFlagArray.add(movementFlag.getAreaTwo());
            JSONmovementFlags.add(movementFlagArray);
        }
        eventObj.put(Keys.MOVEMENT_FLAGS_KEY, JSONmovementFlags);
        return eventObj;
    }

    private static JSONArray writeSubEvents(List<SubEvent> list) {
        JSONArray subEList = new JSONArray();
        for (SubEvent event : list) {
          if  (event instanceof ItemEvent) {
              subEList.add(writeItemEvent((ItemEvent) event));
          } else if (event instanceof PokémonEvent) {
              subEList.add(writePokémonEvent((PokémonEvent) event));
          } else if (event instanceof PokémonChoiceEvent) {
              subEList.add(writePokémonChoiceEvent((PokémonChoiceEvent) event));
          } else if (event instanceof RivalBattleEvent) {
              subEList.add(writeRivalBattleEvent((RivalBattleEvent) event));
          } else if (event instanceof TrainerEvent) {
              subEList.add(writeTrainerEvent((TrainerEvent) event));
            } else {
              subEList.add(writeWildEvent((WildEvent) event));
          }
        }
        return subEList;
    }

    private static JSONObject writeItemEvent(ItemEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.ITEM_EVENT);
        String itemName = event.getItem() == null ? "NONE" : event.getItem().getEncodedName();
        eventObj.put(Keys.ITEM_KEY, itemName);
        eventObj.put(Keys.AMOUNT_KEY, event.getQuantity());
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writePokémonEvent(PokémonEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.POKÉMON_EVENT);
        eventObj.put(Keys.SPECIES_KEY, event.getMon().ordinal());
        eventObj.put(Keys.LEVEL_KEY, event.getLevel());
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writePokémonChoiceEvent(PokémonChoiceEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.POKÉMON_CHOICE_EVENT);
        eventObj.put(Keys.LEVEL_KEY, event.getLevel());
        ArrayList<String> speciesList = new ArrayList<>();
        for (Species species : event.getSpecies()) {
            speciesList.add(species.toString());
        }
        eventObj.put(Keys.OPTIONS_KEY, speciesList);
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writeRivalBattleEvent(RivalBattleEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.RIVAL_BATTLE_EVENT);
        eventObj.put(Keys.WIN_MSG_KEY, event.getWinMsg());
        eventObj.put(Keys.LOSE_MSG_KEY, event.getLoseMsg());
        eventObj.put(Keys.MONEY_KEY, event.getPrizeMoney());
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        JSONArray metaList = new JSONArray();
        for (List<PartySlot> list : event.getPotentialParties()) {
            metaList.add(writeTrainerMonList(list));
        }
        eventObj.put(Keys.PARTIES_KEY, metaList);
        return eventObj;
    }

    private static JSONObject writeTrainerEvent(TrainerEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put(Keys.TYPE_KEY, Keys.TRAINER_EVENT);
        eventObj.put(Keys.TRAINERS_KEY, writeTrainer(event.getTrainer()));
        eventObj.put(Keys.BEFORE_KEY, event.getBeforeMain());
        eventObj.put(Keys.AFTER_KEY, event.getAfterMain());
        return eventObj;
    }

    /**
     * Writes all necessary info about a Pokémon to the buffer
     * @param mon
     */
    private static JSONObject writeMon(Pokémon mon) throws BadNameException {
        JSONObject monObj = new JSONObject();
        monObj.put(Keys.SPECIES_KEY, mon.getSpeciesName().toUpperCase());
        monObj.put(Keys.NAME_KEY, mon.getNickname());
        monObj.put(Keys.ABILITY_KEY, mon.getAbility().ordinal());
        monObj.put(Keys.NATURE_KEY, mon.getNature().ordinal());
        monObj.put(Keys.STATUS_KEY, mon.getStatus().ordinal());
        monObj.put(Keys.OWNER_KEY, mon.getOwner().ordinal());
        monObj.put(Keys.GENDER_KEY, mon.getGender().ordinal());
        monObj.put(Keys.EVS_KEY, listify((mon.getEVs())));
        monObj.put(Keys.STATS_KEY, listify(mon.getStats()));
        monObj.put(Keys.CURR_HP_KEY, mon.getCurrentHP());
        monObj.put(Keys.LEVEL_KEY, mon.getLevel());
        monObj.put(Keys.CURR_XP_KEY, mon.getCurrentXP());
        JSONArray moveList = new JSONArray();
        for (Move move : mon.getMoveSet()) {
            JSONObject moveObj = new JSONObject();
            moveObj.put("name", move.getName());
            moveObj.put("currPP", move.getCurrPP());
            moveObj.put("currMaxPP", move.getCurrMaxPP());
            moveList.add(moveObj);
        }

        monObj.put(Keys.MOVESET_KEY, moveList);
        return monObj;
    }

    private static List<Integer> listify(int[] list) {
        return Arrays.stream(list).boxed().collect(Collectors.toList());
    }

    private static JSONArray writeMonList(List<Pokémon> list) throws BadNameException {

        JSONArray jsonList = new JSONArray();

        for (Pokémon mon : list) {
            jsonList.add(writeMon(mon));
        }
        return jsonList;
    }

    private static JSONArray writeTrainerMonList(List<PartySlot> list) {
        JSONArray jsonList = new JSONArray();
        for (PartySlot slot : list) {
            JSONObject slotObj = new JSONObject();
            slotObj.put(Keys.SPECIES_KEY, slot.getMon().toString());
            slotObj.put(Keys.LEVEL_KEY, slot.getLevel());
            if (slot.getItem() != null) {
                slotObj.put(Keys.ITEM_KEY, slot.getItem().getEncodedName());
            }
            if (slot.getMoveSet() != null) {
                JSONArray moveList = new JSONArray();
                for (Move move : slot.getMoveSet()) {
                    JSONObject moveObj = new JSONObject();
                    moveObj.put(Keys.NAME_KEY, move.getName());
                    moveList.add(moveObj);
                }

                slotObj.put(Keys.MOVESET_KEY, moveList);
            }
            jsonList.add(slotObj);
        }
        return jsonList;
    }

    private static JSONObject writePlayer(Player player, Area currentArea) throws BadNameException {

        JSONObject playerObject = new JSONObject();
        playerObject.put(Keys.NAME_KEY, player.getPlayerName());
        playerObject.put(Keys.RIVAL_KEY, player.getRivalName());
        playerObject.put(Keys.MONEY_KEY, player.getMoney());
        playerObject.put(Keys.BADGES_KEY, player.getBadges());
        playerObject.put(Keys.PARTY_KEY, writeMonList(player.getParty()));
        playerObject.put(Keys.BAG_KEY, writeBag(player.getBag()));
        playerObject.put(Keys.PC_KEY, writeMonList(player.getPC()));
        playerObject.put(Keys.CURR_AREA_KEY, currentArea.getName());
        return playerObject;
    }

    private static JSONArray writePC(ArrayList<Pokémon> pc) throws BadNameException {
        return writeMonList(pc);
    }

    private static JSONArray writeBag(Bag bag) {
        JSONArray bagList = new JSONArray();
        for (int i = 0; i < bag.getPockets().length; i++) {
            HashMap<Item, Integer> hashMap = bag.getPockets()[i].getItemListRaw();
            for (Item item : hashMap.keySet()) {
                JSONObject itemObj = new JSONObject();
                itemObj.put(Keys.ITEM_KEY, item.getEncodedName());
                itemObj.put(Keys.AMOUNT_KEY, hashMap.get(item));
                bagList.add(itemObj);
            }
        }
        return bagList;
    }
}
