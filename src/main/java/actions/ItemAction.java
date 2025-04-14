package actions;

import battle.BattleSlot;
import items.Item;

public class ItemAction extends Action {

    private Item item;

    public ItemAction(BattleSlot slot, Item item) {
        super(slot);
        this.item = item;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public Item getItem() {
        return item;
    }
}
