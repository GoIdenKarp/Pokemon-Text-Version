package events;

import enums.Species;
import items.Item;

import java.util.List;

public class WildEvent extends SubEvent {

    private Species mon;
    private int level;
    private Item item;

    public WildEvent(Species mon, int level, List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.mon = mon;
        this.level = level;
        this.item = null;
    }

    public WildEvent(Species mon, int level, Item item, List<String> beforeMain, List<String> afterMain) {
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

    public void setLevel(int level) {
        this.level = level;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
