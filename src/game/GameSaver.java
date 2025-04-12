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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import ui.GameFrame;

public class GameSaver {

    private static String encode(String toEncode) {
        return encoder.encodeToString(toEncode.getBytes());
    }

    public static final String FILETYPE= ".ptvsav";

    private static final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Converts the game state to an encoded string and writes it to a file
     * @param game
     * @param saveName
     * @return
     */
    public static boolean save(Game game, String saveName) {
        String filename = saveName + FILETYPE;
        try {
            PrintWriter writer = new PrintWriter(filename);
            JSONObject saveData = new JSONObject();
            saveData.put("player", writePlayer(game.getPlayer()));
            saveData.put("game", writeGameState(game));

            String saveString = encode(saveData.toJSONString());
            writer.println(saveString);
            writer.flush();
            writer.close();

        } catch (FileNotFoundException | BadNameException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
        areaObj.put("events", writeEvents(area.getEvents()));
        areaObj.put("trainers", writeTrainers(area.getTrainers()));
        areaObj.put("defeated", writeTrainers(area.getDefeatedTrainers()));
        areaObj.put("items", writeAreaItems(area.getItems()));
        areaObj.put("movements", writeMovements(area.getMovePermissions()));
        return areaObj;

    }

    private static JSONArray writeAreaItems(List<ItemBall> items) {
        JSONArray areaItems = new JSONArray();
        for (ItemBall itemBall : items) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("item", itemBall.getItem().getName());
            itemObj.put("requirement", itemBall.getObtainRequirement().ordinal());
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
        trainerObj.put("type", trainer.getType());
        trainerObj.put("name", trainer.getName());
        trainerObj.put("greeting", trainer.getGreeting());
        trainerObj.put("winMsg", trainer.getWinMsg());
        trainerObj.put("loseMsg", trainer.getLoseMsg());
        trainerObj.put("money", trainer.getPrizeMoney());
        trainerObj.put("party", writeTrainerMonList(trainer.getParty()));
        trainerObj.put("double", trainer.isDoubleBattle());
        return trainerObj;
    }

    private static JSONObject writeWildEvent(WildEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put("type", "Wild");
        eventObj.put("species", event.getMon().ordinal());
        eventObj.put("level", event.getLevel());
        if (event.getItem() != null) {
            eventObj.put("item", event.getItem().getName());
        }
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
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
        eventObj.put("subEvents", writeSubEvents(event.getMasterList()));
        eventObj.put("movementFlags", event.getMovementFlagsToLift());
        eventObj.put("eventFlags", event.getEventFlagsToLift());
        eventObj.put("reset", event.resetOnFail());
        if (!event.getCanStartFlag().isEmpty()) {
            eventObj.put("canStart", event.getCanStartFlag());
        }
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
        eventObj.put("type", "Item");
        eventObj.put("item", event.getItem().getName());
        eventObj.put("quantity", event.getQuantity());
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writePokémonEvent(PokémonEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put("type", "Pokémon");
        eventObj.put("species", event.getMon().ordinal());
        eventObj.put("level", event.getLevel());
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writePokémonChoiceEvent(PokémonChoiceEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put("type", "PokémonChoice");
        eventObj.put("level", event.getLevel());
        ArrayList<Integer> ordinalList = new ArrayList<>();
        for (Species species : event.getSpecies()) {
            ordinalList.add(species.ordinal());
        }
        eventObj.put("species", ordinalList);
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
        return eventObj;
    }

    private static JSONObject writeRivalBattleEvent(RivalBattleEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put("type", "RivalBattle");
        eventObj.put("winMsg", event.getWinMsg());
        eventObj.put("loseMsg", event.getLoseMsg());
        eventObj.put("money", event.getPrizeMoney());
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
        JSONArray metaList = new JSONArray();
        for (List<PartySlot> list : event.getPotentialParties()) {
            metaList.add(writeTrainerMonList(list));
        }
        return eventObj;
    }

    private static JSONObject writeTrainerEvent(TrainerEvent event) {
        JSONObject eventObj = new JSONObject();
        eventObj.put("type", "Trainer");
        eventObj.put("trainer", writeTrainer(event.getTrainer()));
        eventObj.put("before", event.getBeforeMain());
        eventObj.put("after", event.getAfterMain());
        return eventObj;
    }

    /**
     * Writes all necessary info about a Pokémon to the buffer
     * @param mon
     */
    private static JSONObject writeMon(Pokémon mon) throws BadNameException {
        JSONObject monObj = new JSONObject();
        monObj.put("species", Species.map(mon.getSpeciesName()).ordinal());
        monObj.put("name", mon.getNickname());
        monObj.put("ability", mon.getAbility().ordinal());
        monObj.put("nature", mon.getNature().ordinal());
        monObj.put("status", mon.getStatus().ordinal());
        monObj.put("owner", mon.getOwner().ordinal());
        monObj.put("gender", mon.getGender().ordinal());
        monObj.put("evs", listify((mon.getEVs())));
        monObj.put("stats", listify(mon.getStats()));
        monObj.put("currHP", mon.getCurrentHP());
        monObj.put("level", mon.getLevel());
        monObj.put("currXP", mon.getCurrentXP());
        JSONArray moveList = new JSONArray();
        for (Move move : mon.getMoveSet()) {
            JSONObject moveObj = new JSONObject();
            moveObj.put("name", move.getName());
            moveObj.put("currPP", move.getCurrPP());
            moveObj.put("currMaxPP", move.getCurrMaxPP());
            moveList.add(moveObj);
        }

        monObj.put("moveset", moveList);
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
            slotObj.put("species", slot.getMon().ordinal());
            slotObj.put("level", slot.getLevel());
            if (slot.getItem() != null) {
                slotObj.put("item", slot.getItem().getName());
            }
            if (slot.getMoveSet() != null) {
                JSONArray moveList = new JSONArray();
                for (Move move : slot.getMoveSet()) {
                    JSONObject moveObj = new JSONObject();
                    moveObj.put("name", move.getName());
                    moveObj.put("currPP", move.getCurrPP());
                    moveObj.put("currMaxPP", move.getCurrMaxPP());
                    moveList.add(moveObj);
                }

                slotObj.put("moveset", moveList);
            }
            jsonList.add(slotObj);
        }
        return jsonList;
    }

    private static JSONObject writePlayer(Player player) throws BadNameException {

        JSONObject playerObject = new JSONObject();
        playerObject.put("name", player.getPlayerName());
        playerObject.put("rival", player.getRivalName());
        playerObject.put("money", player.getMoney());
        playerObject.put("badges", player.getBadges());
        playerObject.put("party", writeMonList(player.getParty()));
        playerObject.put("bag", writeBag(player.getBag()));
        playerObject.put("PC", writeMonList(player.getPC()));

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
                itemObj.put("item", item.getName());
                itemObj.put("quantity", hashMap.get(item));
                bagList.add(itemObj);
            }
        }
        return bagList;
    }

    public static void main(String[] args) throws ParseException, BadNameException {
        GameFrame frame = new GameFrame(true);
        Player ari = new Player("Ari", "Evil Ari");
        PokémonFactory factory = new PokémonFactory(frame.getInputHelper(), frame.getGamePrinter());
        JSONParser parser = new JSONParser();
        ArrayList<Pokémon> monList = new ArrayList<Pokémon>();
        Pokémon pikachu = factory.makePokémon(Species.PIKACHU, 5, Owner.PLAYER);
        monList.add(pikachu);
        Pokémon blastoise = factory.makePokémon(Species.BLASTOISE, 50, Owner.PLAYER);
        monList.add(blastoise);
        Pokémon beedrill = factory.makePokémon(Species.BEEDRILL, 36, Owner.PLAYER);
        monList.add(beedrill);

        ari.setParty(monList);
        ari.addBadge("Boulder Badge");


        JSONObject playerObj = writePlayer(ari);
        //System.out.println(playerObj);
        String playerEncoded = encode(playerObj.toJSONString());
      //  System.out.println(playerEncoded);
        String decoded = new String(Base64.getDecoder().decode(playerEncoded));
        JSONObject decodedPlayer = (JSONObject) parser.parse(decoded);
        //System.out.println(decodedPlayer);

        ArrayList<String> test = new ArrayList<>(Arrays.asList("foo", "bar", "baz", "qux"));
        JSONObject string = new JSONObject();
        string.put("test", test);
        System.out.println(string);

        String encoded = encode(string.toJSONString());
        String decodedList = new String(Base64.getDecoder().decode(encoded));
        JSONObject decodedString = (JSONObject) parser.parse(decodedList);
        System.out.println(decodedString);


//        JSONArray array = writeMonList(monList);
//        System.out.println(array);
//        String encodedArray = encode(array.toJSONString());
//        System.out.println(encodedArray);
//        String decodedString = new String(Base64.getDecoder().decode(encodedArray));
//        System.out.println(decodedString);
//        JSONArray decodedArray = (JSONArray) parser.parse(decodedString);
//        System.out.println(((JSONObject) decodedArray.get(0)).get("moveset"));
    }
}
