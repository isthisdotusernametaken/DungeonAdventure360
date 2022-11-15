package model;

import java.util.Arrays;
import java.util.List;

public class Container {

    private static final String UNKNOWN_TYPE = "Unknown item type selected. " +
                                               "Cannot be used.";

    private final List<Item> myItems;

    Container(final Item ... theItems) {
        myItems = Arrays.asList(theItems);
    }

    Item[] viewItems() {
        Item[] items = new Item[myItems.size()];

        int i = 0;
        for (Item item : myItems) {
            items[i] = item.copy();
        }

        return items;
    }

    String useItem(final int theIndex,
                   final DungeonCharacter theTarget,
                   final Map theMap,
                   final Room theRoom,
                   final RoomCoordinates theCoords) {
        Item selectedItem = myItems.get(theIndex);

        if (selectedItem instanceof CharacterApplicableItem) {
            return ((CharacterApplicableItem) selectedItem).use(theTarget);
        }
        if (selectedItem instanceof MapApplicableItem) {
            return ((MapApplicableItem) selectedItem).use(theMap, theCoords);
        }
        if (selectedItem instanceof RoomApplicableItem) {
            return ((RoomApplicableItem) selectedItem).use(theRoom);
        }

        throw new IllegalArgumentException(UNKNOWN_TYPE);
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
