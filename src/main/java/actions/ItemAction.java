package actions;

import battle.BattleSlot;
import items.Item;

public class ItemAction extends Action {

    private static final int PRIORIRTY = 7;

    private Item item;




    public ItemAction(BattleSlot slot, Item item) {
        super(slot);
        this.item = item;
    }

    @Override
    public int getPriority() {
        return PRIORIRTY;
    }

    public Item getItem() {
        return item;
    }
}
