package game;

import areas.Area;
import areas.AreaBuilder;
//import areas.PokéMart;
import areas.WildSlot;
import enums.MoveRequirement;
import enums.Species;
import exceptions.BadNameException;
import items.Item;
import items.ItemMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.JsonBase64Util;

import java.util.*;

public class Region {

    public final static String NO_JAR_MODE_PREFIX = "bin/";

    private final static String AREAS_KEY = "areas";
    private final static String CONNECTIONS_KEY = "connections";
    private final static String NAME_KEY = "name";
    private final static String OPTIONS_KEY = "options";
    private final static String TALK_KEY = "talk";
    private final static String GRASS_KEY = "grass";
    private final static String SURF_KEY = "surf";
    private final static String FISHING_KEY = "fishing";
    private final static String SPECIES_KEY = "species";
    private final static String MIN_KEY = "min";
    private final static String MAX_KEY = "max";
    private final static String RATE_KEY = "rate";
    private final static String ITEM_KEY = "item";
    private final static String CHANCE_KEY = "chance";
    private final static String FIRST_KEY = "first";
    private final static String SECOND_KEY = "second";
    private final static String REQUIREMENT_KEY = "requirement";
    private final static String HEALING_OPTION = "Healing";
    private final static String FLYING_OPTION = "Flyable";
    private final static String MART_OPTION = "Mart";

    public static ArrayList<Area> createRegion(String filename, boolean JAR_MODE) {
        try {
            String prefix = "data/";
            prefix = (JAR_MODE) ? prefix : NO_JAR_MODE_PREFIX + prefix;
            String filePath = prefix + filename;
            // Use the new utility class to decode the region file
            JSONObject regionObj = JsonBase64Util.decodeBase64FileToJson(filePath);
            JSONObject areasObj = (JSONObject) regionObj.get(AREAS_KEY);
            JSONArray connections = (JSONArray) regionObj.get(CONNECTIONS_KEY);
            ArrayList<Area> regionMap = parseAreas(areasObj);
            addConnections(regionMap, connections);
            return regionMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        //shouldn't happen
    }

    private static ArrayList<Area> parseAreas(JSONObject areasObj) throws BadNameException {
        ArrayList<Area> toReturn = new ArrayList<>();
        for (Object object : areasObj.keySet()) {
            String key = (String) object;
            JSONObject areaObj = (JSONObject) areasObj.get(key);
            Area area = parseArea(areaObj);
            toReturn.add(area);
        }
        return toReturn;
    }

    private static Area parseArea(JSONObject areaObj) throws BadNameException {
        String name = (String) areaObj.get(NAME_KEY);
        AreaBuilder builder = new AreaBuilder(name);
        //Parse options
        if (areaObj.containsKey(OPTIONS_KEY)) {
            JSONArray options = (JSONArray) areaObj.get(OPTIONS_KEY);
            if (options.contains(HEALING_OPTION)) {
                builder.addHealingSpot();
            }
            if (options.contains(FLYING_OPTION)) {
                builder.addFlyable();
            }
            if (options.contains(MART_OPTION)) {
                builder.addMart();
            }
        }
        //Parse townspeople
        if (areaObj.containsKey(TALK_KEY)) {
            ArrayList<String> townspeople = new ArrayList<>();
            JSONArray talkObj = (JSONArray) areaObj.get(TALK_KEY);
            for (Object obj : talkObj) {
                String talkString = (String) obj;
                townspeople.add(talkString);
            }
            builder.addTownsPeople(townspeople);
        }
        //parse tall grass
        if (areaObj.containsKey(GRASS_KEY)) {
            JSONArray grassObj = (JSONArray) areaObj.get(GRASS_KEY);
            TreeMap<Integer, WildSlot> grassSlots = parseWildSlots(grassObj);
            builder.addTallGrassSlots(grassSlots);
        }
        //parse surf
        if (areaObj.containsKey(SURF_KEY)) {
            JSONArray surfObj = (JSONArray) areaObj.get(SURF_KEY);
            TreeMap<Integer, WildSlot> surfSlots = parseWildSlots(surfObj);
            builder.addTallGrassSlots(surfSlots);
        }
        //parse fishing
        if (areaObj.containsKey(FISHING_KEY)) {
            JSONArray fishingObj = (JSONArray) areaObj.get(FISHING_KEY);
            TreeMap<Integer, WildSlot> fishingSlots = parseWildSlots(fishingObj);
            builder.addTallGrassSlots(fishingSlots);
        }
        return builder.build();
    }

    private static TreeMap<Integer, WildSlot> parseWildSlots(JSONArray slots) throws BadNameException {
        ArrayList<WildSlot> wildSlots = new ArrayList<>();
        for (Object obj : slots) {
            JSONObject slot = (JSONObject) obj;
            Species species = Species.map((String) slot.get(SPECIES_KEY));
            int min = ((Long) slot.get(MIN_KEY)).intValue();
            int max = ((Long) slot.get(MAX_KEY)).intValue();
            int rate = ((Long) slot.get(RATE_KEY)).intValue();
            WildSlot wildSlot;
            if (slot.containsKey(ITEM_KEY)) {
                String itemString = (String) slot.get(ITEM_KEY);
                Item item = ItemMapper.map(itemString);
                int itemChance = ((Long) slot.get(CHANCE_KEY)).intValue();
                wildSlot = new WildSlot(species, min, max, rate,  itemChance, item);
            }
            wildSlot = new WildSlot(species, min, max, rate);
            wildSlots.add(wildSlot);
        }
        return treeMapify(wildSlots);
    }

    private static void addConnections(ArrayList<Area> regionMap, JSONArray connections) {
        for (Object obj : connections) {
            JSONObject xObj = (JSONObject) obj;
            String first = (String) xObj.get(FIRST_KEY);
            String second = (String) xObj.get(SECOND_KEY);
            MoveRequirement requirement = MoveRequirement.map((String) xObj.get(REQUIREMENT_KEY));
            //have to initialize to null, but in practice the values should always be updayed
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


    /**
     * Dynamically generate the TreeMap for the WildSlots for a given Area. This needs to be done because there
     * are always a variable number of slots in any given group.
     * @param wildSlots The list of WildSlots that we need to make a TreeMap for
     * @return The TreeMap representing the WildSlots and the probabilities of encounterting them
     */
    private static TreeMap<Integer, WildSlot> treeMapify(ArrayList<WildSlot> wildSlots) {
        TreeMap<Integer, WildSlot> toReturn = new TreeMap<>();
        int currentTotal = 0;
        for (WildSlot slot : wildSlots) {
            currentTotal += slot.getProbability();
            toReturn.put(currentTotal, slot);
        }
        return toReturn;

    }


}
