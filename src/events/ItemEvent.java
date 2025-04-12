package events;

import items.Item;

import java.util.List;

public class ItemEvent extends SubEvent {

    private Item item;
    private int quantity;

    public ItemEvent(Item item, int quantity, List<String> beforeMain, List<String> aftermain) {
        super(beforeMain, aftermain);
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
