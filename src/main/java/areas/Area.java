package areas;

import enums.MoveRequirement;
import enums.Weather;
import events.GameEvent;
import trainer.Trainer;

import java.util.*;

public class Area {

    public final String name;
    protected List<Area> connections;
    protected Map<Area, MoveRequirement> movePermissions;
    protected List<GameEvent> events;
    protected List<ItemBall> items;
    protected List<Trainer> trainers;
    protected List<Trainer> defeatedTrainers;
    protected TreeMap<Integer, WildSlot> tallGrassSlots;
    protected TreeMap<Integer, WildSlot> surfSlots;
    protected TreeMap<Integer, WildSlot> fishingSlots;
    protected boolean healingSpot;
    protected boolean flyable;
    protected boolean hasMart;
    //protected PokéMart mart;
    protected List<String> townsPeople;
    protected int townsPeopleCounter;
    protected Weather weather;


    public Area(String name) {
        this.name = name;
        connections = new ArrayList<>();
        movePermissions = new HashMap<>();
        events = new ArrayList<>();
        healingSpot = false;
        flyable = false;
        hasMart = false;
        weather = Weather.NONE;
        trainers = new ArrayList<>();
        defeatedTrainers = new ArrayList<>();
        items = new ArrayList<>();
//        tallGrassSlots = null;
//        surfSlots = null;
//        fishingSlots = null;
//        townsPeople = null;
//        townsPeopleMasterList = null;
    }
    public String getName() {
        return name;
    }

    public List<Area> getConnections() {
        return connections;
    }

    public void setConnections(List<Area> connections) {
        this.connections = connections;
    }

    public Map<Area, MoveRequirement> getMovePermissions() {
        return movePermissions;
    }

    public void setMovePermissions(Map<Area, MoveRequirement> movePermissions) {
        this.movePermissions = movePermissions;
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public void setEvents(List<GameEvent> events) {
        this.events = events;
    }

    public void addConnection(Area area, MoveRequirement requirement) {
        if (!connections.contains(area)) {
            connections.add(area);
        }
        //We always want this to run because the MoveRequirement could be getting updated
        movePermissions.put(area, requirement);

    }

    public void setItems(List<ItemBall> items) {
        this.items = items;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
    public void setDefeatedTrainers(List<Trainer> trainers) {
        this.defeatedTrainers = trainers;
    }

    public void resetTrainers() {
        ArrayList<Trainer> newList = new ArrayList<>();
        newList.addAll(defeatedTrainers);
        newList.addAll(trainers);
        trainers = newList;
        defeatedTrainers = new ArrayList<>();
    }


    public void setTallGrassSlots(TreeMap<Integer, WildSlot> tallGrassSlots) {
        this.tallGrassSlots = tallGrassSlots;
    }

    public void setSurfSlots(TreeMap<Integer, WildSlot> surfSlots) {
        this.surfSlots = surfSlots;
    }

    public void setFishingSlots(TreeMap<Integer, WildSlot> fishingSlots) {
        this.fishingSlots = fishingSlots;
    }

    public void setHealingSpot(boolean healingSpot) {
        this.healingSpot = healingSpot;
    }

    public void addMart() {
        this.hasMart = true;
    }

    public List<ItemBall> getItems() {
        return items;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public TreeMap<Integer, WildSlot> getTallGrassSlots() {
        return tallGrassSlots;
    }

    public TreeMap<Integer, WildSlot> getSurfSlots() {
        return surfSlots;
    }

    public TreeMap<Integer, WildSlot> getFishingSlots() {
        return fishingSlots;
    }

    public boolean isHealingSpot() {
        return healingSpot;
    }

    public boolean hasMart() {
        return hasMart;
    }

    public List<String> getTownsPeople() {
        return townsPeople;
    }

    public void setTownsPeople(List<String> townsPeople) {
        this.townsPeople = townsPeople;
        townsPeopleCounter = 0;
    }

    public boolean isFlyable() {
        return flyable;
    }

    public void setFlyable(boolean flyable) {
        this.flyable = flyable;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String toString() {
        return name;
    }

    public void trainerDefeated() {
        Trainer trainer = trainers.remove(0);
        defeatedTrainers.add(trainer);
    }

    public String getTalkString() {
        String toReturn = townsPeople.get(townsPeopleCounter);
        townsPeopleCounter++;
        if (townsPeopleCounter == townsPeople.size()) {
            townsPeopleCounter = 0;
        }
        return toReturn;
    }

    public List<Trainer> getDefeatedTrainers() {
        return defeatedTrainers;
    }
}
