package areas;

import enums.Weather;
import items.Item;
import trainer.Trainer;

import java.util.List;
import java.util.TreeMap;

public class AreaBuilder {

    private Area area;


    public AreaBuilder(String name) {
        area = new Area(name);

    }

    public void addHealingSpot() {
        area.setHealingSpot(true);
    }


    public void addTrainers(List<Trainer> trainers) {
        area.setTrainers(trainers);
    }

    public void addItems(List<ItemBall> items) {
        area.setItems(items);
    }

    public void addTallGrassSlots(TreeMap<Integer, WildSlot> slots) {
        area.setTallGrassSlots(slots);
    }

    public void addSurfSlots(TreeMap<Integer, WildSlot> slots) {
        area.setSurfSlots(slots);
    }

    public void addFishingSlots(TreeMap<Integer, WildSlot> slots) {
        area.setFishingSlots(slots);
    }

    public void addMart() {
        area.addMart();
    }

    public void addTownsPeople(List<String> townsPeople) {
        area.setTownsPeople(townsPeople);
    }

    public void addFlyable() {
        area.setFlyable(true);
    }

    public void addWeather(Weather weather) {
        area.setWeather(weather);
    }

    public Area build() {
        return area;
    }
}
