package model;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private final List<Item> myItems;

    Container(final List<Item> theItems) {
        myItems = new ArrayList<>(theItems);
    }

    Item[] viewItems() {
        Item[] items = new Item[myItems.size()];

        int i = 0;
        for (Item item : myItems) {
            items[i] = item.copy();
        }

        return items;
    }

    List<Item> viewItemsAsList() {
        return List.of(viewItems());
    }

    void useItem(final int theIndex) {
        myItems.get(theIndex).consume();
    }

    void addItem(final Item theItem) {
        for (Item existingItem : myItems) {
            if (theItem.isSameType(existingItem)) {
                existingItem.addToStack(theItem.getCount());
                return;
            }
        }

        myItems.add(theItem.copy());
    }

    Item removeItem(final int theIndex) {
        return myItems.remove(theIndex);
    }
}
