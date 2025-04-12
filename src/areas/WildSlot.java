package areas;

import enums.Species;
import items.Item;

/**
 * A class representing one wild Pokémon slot for an Area
 */
public class WildSlot {

    private Species species;
    private int minLevel;
    private int maxLevel;
    private int itemChance;
    private Item item;
    private int probability;

    public WildSlot(Species species, int minLevel, int maxLevel, int probability ) {
        this.species = species;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.itemChance = 0;
        this.probability = probability;
        this.item = null;
    }

    public WildSlot(Species species, int minLevel, int maxLevel, int probability, int itemChance, Item item) {
        this.species = species;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.itemChance = itemChance;
        this.probability = probability;
        this.item = item;

    }


    public Species getSpecies() {
        return species;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getItemChance() {
        return itemChance;
    }

    public int getProbability() {
        return probability;
    }

    public Item getItem() {
        return item;
    }
}
