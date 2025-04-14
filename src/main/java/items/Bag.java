package items;

import java.util.ArrayList;

public class Bag {

    private static final int MISC_INDEX = 0;
    private static final int HEALING_INDEX = 1;
    private static final int BALLS_INDEX = 2;
    private static final int TM_INDEX = 3;
    private static final int BERRY_INDEX = 4;
    private static final int KEY_ITEM_INDEX = 5;
    private static final int NUMBER_OF_POCKETS = 6;

    private Pocket[] pockets;

    public Bag() {
        pockets = new Pocket[6];
        for (int i = 0; i < pockets.length; i++) {
            pockets[i] = new Pocket();
        }
    }

    public void addItem(Item newItem, int quantity) {
        int indexToAdd;
        if (newItem instanceof HealingItem) {
            indexToAdd = HEALING_INDEX;
        } else if (newItem instanceof Ball) {
            indexToAdd = BALLS_INDEX;
        } else if (newItem instanceof TM) {
            indexToAdd = TM_INDEX;
        } else if (newItem instanceof Berry) {
            indexToAdd = BERRY_INDEX;
        } else if (newItem instanceof Usable) {
            indexToAdd = MISC_INDEX;
        } else {
            indexToAdd = KEY_ITEM_INDEX;
        }

        pockets[indexToAdd].addItem(newItem, quantity);
    }


    /**
     * Finds an item in one of the Pockets by it's name. This should only be called when we know the named item is
     * present
     * @param itemName The String matching the desired Item's name
     * @return The item, or null in the case it cannot be found (shouldn't happen)
     */
    public Item getItemByName(String itemName) {
        Item toReturn = null;
        for (Pocket pocket : pockets) {
            toReturn = pocket.getItemByName(itemName);
            if (toReturn != null) {
                break;
            }
        }
        return toReturn;
    }


    public ArrayList<String> getMiscItems() {
        return pockets[MISC_INDEX].getItemList();
    }

    public ArrayList<String> getHealingItems() {
        return pockets[HEALING_INDEX].getItemList();
    }

    public ArrayList<String> getBalls() {
        return pockets[BALLS_INDEX].getItemList();
    }

    public ArrayList<String> getTMs() {
        return pockets[TM_INDEX].getItemList();
    }

    public ArrayList<String> getBerries() {
        return pockets[BERRY_INDEX].getItemList();
    }

    public ArrayList<String> getKeyItems() {
        return pockets[KEY_ITEM_INDEX].getItemList();
    }

    public void useItem(Item item) {
        int index;
        if (item instanceof HealingItem) {
            index = HEALING_INDEX;
        } else if (item instanceof Ball) {
            index = BALLS_INDEX;
        } else if (item instanceof TM) {
            index = TM_INDEX;
        } else if (item instanceof Berry) {
            index = BERRY_INDEX;
        } else if (item instanceof KeyItem) {
            index = KEY_ITEM_INDEX;
        } else {
            index = MISC_INDEX;
        }
        pockets[index].useItem(item);
    }

    public Pocket[] getPockets() {
        return pockets;
    }
}
