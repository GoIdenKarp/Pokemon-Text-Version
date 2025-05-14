package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pocket {

    private HashMap<Item, Integer> itemList;

    public Pocket() {
        itemList = new HashMap<>();
    }

    public void addItem(Item newItem, int amt) {
        Item existingItem = getItemByName(newItem.getName());
        if (existingItem != null) {
            // Item with same name already exists, update its quantity
            itemList.put(existingItem, itemList.get(existingItem) + amt);
        } else {
            // This is a new item type, add it to the map
            itemList.put(newItem, amt);
        }
    }

    public Item getItemByName(String itemName) {
        for (Item item: itemList.keySet()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<String> getItemList() {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : itemList.entrySet()) {
            String stringifiedItem = entry.getKey().getName() + " | " + entry.getValue().toString();
            toReturn.add(stringifiedItem);
        }
        return toReturn;
    }

    public void useItem(Item item) {
        if (itemList.get(item) == 1) {
            itemList.remove(item);
        } else {
            itemList.put(item, itemList.get(item) - 1);
        }
    }

    public HashMap<Item, Integer> getItemListRaw() {
        return itemList;
    }

}
