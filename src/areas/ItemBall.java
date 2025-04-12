package areas;

import enums.MoveRequirement;
import items.Item;

//Represents an item on the map, which may or may not have a specific requirement to obtain
public class ItemBall {

    private Item item;
    private MoveRequirement obtainRequirement;

    public ItemBall(Item item, MoveRequirement obtainRequirement) {
        this.item = item;
        this.obtainRequirement = obtainRequirement;
    }

    public Item getItem() {
        return item;
    }

    public MoveRequirement getObtainRequirement() {
        return obtainRequirement;
    }
}
