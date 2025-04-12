package trainer;

import enums.Species;
import items.Item;
import moves.Move;

import java.util.List;

public class PartySlot {

    private Species mon;
    private int level;
    private Item item;
    private List<Move> moveSet;

    public PartySlot(Species mon, int level) {
        this.mon = mon;
        this.level = level;
        this.item = null;
        this.moveSet = null;
    }

    public PartySlot(Species mon, int level, Item item) {
        this.mon = mon;
        this.level = level;
        this.item = item;
        this.moveSet = null;
    }

    public PartySlot(Species mon, int level, List<Move> moveset) {
        this.mon = mon;
        this.level = level;
        this.item = null;
        this.moveSet = moveset;
    }

    public PartySlot(Species mon, int level, Item item, List<Move> moveset) {
        this.mon = mon;
        this.level = level;
        this.item = item;
        this.moveSet = moveset;
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

    public List<Move> getMoveSet() {
        return moveSet;
    }

    public void setMoveSet(List<Move> moveset) {
        this.moveSet = moveset;
    }
}
