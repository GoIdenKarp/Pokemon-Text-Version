package events;

import enums.Species;
import items.Item;

import java.util.List;

//Event where the player is given a Pokémon
public class PokémonEvent extends SubEvent {

    private Species mon;
    private int level;
    private Item item;

    public PokémonEvent(Species mon, int level, List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.mon = mon;
        this.level = level;
        this.item = null;
    }

    public PokémonEvent(Species mon, int level, Item item, List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.mon = mon;
        this.level = level;
        this.item = item;
    }

    public Species getMon() {
        return mon;
    }

    public void setMon(Species mon) {
        this.mon = mon;
    }

    public int getLevel() {
        return level;
    }

    public Item getItem() {
        return item;
    }
}
