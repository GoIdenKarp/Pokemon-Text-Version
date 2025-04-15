package game;

import areas.Area;
import areas.AreaBuilder;
//import areas.PokéMart;
import areas.WildSlot;
import enums.MoveRequirement;
import enums.Species;
import util.Keys;
import exceptions.BadNameException;
import items.Item;
import items.ItemMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.JsonBase64Util;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Region {

    public final static String NO_JAR_MODE_PREFIX = "bin/";

    private final static String HEALING_OPTION = "Healing";
    private final static String FLYING_OPTION = "Flyable";
    private final static String MART_OPTION = "Mart";

    public static ArrayList<Area> createRegion(String filename, boolean JAR_MODE) {
        try {
            // Get the resource as a stream
            InputStream inputStream = Region.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.out.println("Resource not found. Available resources:");
                try {
                    java.net.URL url = Region.class.getClassLoader().getResource("data");
                    if (url != null) {
                        System.out.println("Found data directory at: " + url);
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
                throw new FileNotFoundException("Resource not found: " + filename);
            }
                        
            // Use the new utility class to decode the region file
            JSONObject regionObj = JsonBase64Util.decodeBase64StreamToJson(inputStream);
            JSONObject areasObj = (JSONObject) regionObj.get(Keys.AREAS_KEY);
            JSONArray connections = (JSONArray) regionObj.get(Keys.CONNECTIONS_KEY);
            ArrayList<Area> regionMap = parseAreas(areasObj);
            addConnections(regionMap, connections);
            return regionMap;
        } catch (Exception e) {
            System.err.println("Error loading region: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load region: " + filename, e);
        }
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
        String name = (String) areaObj.get(Keys.NAME_KEY);
        AreaBuilder builder = new AreaBuilder(name);
        //Parse options
        if (areaObj.containsKey(Keys.OPTIONS_KEY)) {
            JSONArray options = (JSONArray) areaObj.get(Keys.OPTIONS_KEY);
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
        if (areaObj.containsKey(Keys.TALK_KEY)) {
            ArrayList<String> townspeople = new ArrayList<>();
            JSONArray talkObj = (JSONArray) areaObj.get(Keys.TALK_KEY);
            for (Object obj : talkObj) {
                String talkString = (String) obj;
                townspeople.add(talkString);
            }
            builder.addTownsPeople(townspeople);
        }
        //parse tall grass
        if (areaObj.containsKey(Keys.GRASS_KEY)) {
            JSONArray grassObj = (JSONArray) areaObj.get(Keys.GRASS_KEY);
            TreeMap<Integer, WildSlot> grassSlots = parseWildSlots(grassObj);
            builder.addTallGrassSlots(grassSlots);
        }
        //parse surf
        if (areaObj.containsKey(Keys.SURF_KEY)) {
            JSONArray surfObj = (JSONArray) areaObj.get(Keys.SURF_KEY);
            TreeMap<Integer, WildSlot> surfSlots = parseWildSlots(surfObj);
            builder.addTallGrassSlots(surfSlots);
        }
        //parse fishing
        if (areaObj.containsKey(Keys.FISHING_KEY)) {
            JSONArray fishingObj = (JSONArray) areaObj.get(Keys.FISHING_KEY);
            TreeMap<Integer, WildSlot> fishingSlots = parseWildSlots(fishingObj);
            builder.addTallGrassSlots(fishingSlots);
        }
        return builder.build();
    }

    private static TreeMap<Integer, WildSlot> parseWildSlots(JSONArray slots) throws BadNameException {
        ArrayList<WildSlot> wildSlots = new ArrayList<>();
        for (Object obj : slots) {
            JSONObject slot = (JSONObject) obj;
            Species species = Species.map((String) slot.get(Keys.SPECIES_KEY));
            int min = ((Long) slot.get(Keys.MIN_KEY)).intValue();
            int max = ((Long) slot.get(Keys.MAX_KEY)).intValue();
            int rate = ((Long) slot.get(Keys.RATE_KEY)).intValue();
            WildSlot wildSlot;
            if (slot.containsKey(Keys.ITEM_KEY)) {
                String itemString = (String) slot.get(Keys.ITEM_KEY);
                Item item = ItemMapper.map(itemString);
                int itemChance = ((Long) slot.get(Keys.CHANCE_KEY)).intValue();
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
            String first = (String) xObj.get(Keys.FIRST_KEY);
            String second = (String) xObj.get(Keys.SECOND_KEY);
            MoveRequirement requirement = MoveRequirement.map((String) xObj.get(Keys.REQUIREMENT_KEY));
            System.out.println("first: " + first + " second: " + second + " requirement: " + requirement);
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
