package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container implements Serializable {

    @Serial
    private static final long serialVersionUID = 1474934278105133346L;

    private static final String UNKNOWN_TYPE = "Unknown item type selected. " +
                                               "Cannot be used.";

    private final List<Item> myItems;

    Container(final Item ... theItems) {
        myItems = new ArrayList<>(Arrays.asList(theItems));
    }

    String[] viewItemsAsStrings() {
        return myItems.stream().map(Item::toString).toArray(String[]::new);
    }

    Item[] viewItems() {
        Item[] items = new Item[myItems.size()];

        int i = 0;
        for (Item item : myItems) {
            items[i++] = item.copy();
        }

        return items;
    }

    boolean hasItems() {
        return myItems.size() != 0;
    }

    boolean canUse(final int theIndex, final boolean theIsInCombat) {
        return Util.isValidIndex(theIndex, myItems.size()) &&
               myItems.get(theIndex).canChangeCount() &&
               (!theIsInCombat ||
                       myItems.get(theIndex) instanceof CharacterApplicableItem);
    }

    String useItem(final int theIndex,
                   final DungeonCharacter theTarget,
                   final Map theMap,
                   final Room theRoom,
                   final RoomCoordinates theCoords,
                   final boolean theIsInCombat) {
        final Item selectedItem = myItems.get(theIndex);
        String result;

        if (selectedItem instanceof CharacterApplicableItem) {
            result = ((CharacterApplicableItem) selectedItem).use(theTarget);
        } else if (selectedItem instanceof MapApplicableItem) {
            result = theIsInCombat ?
                     Util.NONE :
                     ((MapApplicableItem) selectedItem).use(theMap, theCoords);
        } else if (selectedItem instanceof RoomApplicableItem) {
            result = theIsInCombat ?
                     Util.NONE :
                     ((RoomApplicableItem) selectedItem).use(theRoom);
        } else {
            throw new IllegalArgumentException(UNKNOWN_TYPE);
        }

        if (selectedItem.getCount() <= 0) {
            myItems.remove(selectedItem);
        }
        return result;
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

    void addItems(final Item[] theItems) {
        for (Item item : theItems) {
            addItem(item);
        }
    }

    void clearItems() {
        myItems.clear();
    }

    boolean hasAllPillars() {
        final Pillar[] pillars = Pillar.createPillars();
        int pillarCount = 0;

        for (Pillar pillar : pillars) {
            for (Item item : myItems) {
                if (pillar.getType() == item.getType()) {
                    pillarCount++;
                }
            }
        }

        return pillarCount >= pillars.length;
    }
}
