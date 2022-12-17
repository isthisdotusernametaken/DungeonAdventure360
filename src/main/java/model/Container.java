package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ProgramFileManager;

/**
 * This class utilizes, handles, and accesses the collectable items in the
 * dungeon adventure.
 */
public class Container implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 1474934278105133346L;

    /**
     * String message representing the unusable item.
     */
    private static final String UNUSABLE = "That item cannot be used.";

    /**
     * The list of collected items.
     */
    private final List<Item> myItems;

    /**
     * Constructor to constructs the array list for the collectable items;
     *
     * @param theItems
     */
    Container(final Item ... theItems) {
        myItems = new ArrayList<>(Arrays.asList(theItems));
    }

    /**
     * Formats and displays the collected item from inventory
     * in the string array.
     *
     * @return The string array of collected items from inventory.
     */
    String[] viewItemsAsStrings() {
        return myItems.stream().map(Item::toString).toArray(String[]::new);
    }

    /**
     * Accesses and views the collected items from inventory.
     *
     * @return The items in the inventory.
     */
    Item[] viewItems() {
        final Item[] items = new Item[myItems.size()];

        int i = 0;
        for (Item item : myItems) {
            items[i++] = item.copy();
        }

        return items;
    }

    /**
     * Checks and validates if there are any items in
     * the list/from the inventory.
     *
     * @return The boolean true or false if there are any
     *         item in the list/from the inventory
     */
    boolean hasItems() {
        return myItems.size() != 0;
    }

    /**
     * Accesses and checks if the collected item from the inventory
     * is available to use or is usable.
     *
     * @param theIndex      The index location of the item in the list.
     * @param theIsInCombat The boolean true or false to check if
     *                      currently in combat mode.
     * @return              The boolean true or false if the item is available
     *                      to use or is usable.
     *
     */
    boolean canUse(final int theIndex, final boolean theIsInCombat) {
        return Util.isValidIndex(theIndex, myItems.size()) &&
               (!theIsInCombat ||
                       myItems.get(theIndex) instanceof CharacterApplicableItem);
    }

    /**
     * Accesses, uses and executes the item's effect on the dungeon character
     * when the adventurer chose to use that item.
     *
     * @param theIndex      The index location of the item in the list.
     * @param theTarget     The dungeon character that the item will be used on.
     * @param theMap        The current map location.
     * @param theRoom       The current room location.
     * @param theCoords     The current coordinate positions of the adventurer.
     * @param theIsInCombat The boolean true or false to check if currently in
     *                      combat mode.
     * @return              The string result describing  the process if the
     *                      item has been used or applied.
     */
    String useItem(final int theIndex,
                   final DungeonCharacter theTarget,
                   final Map theMap,
                   final Room theRoom,
                   final RoomCoordinates theCoords,
                   final boolean theIsInCombat) {
        final Item selectedItem = myItems.get(theIndex);
        final String result;

        if (selectedItem instanceof CharacterApplicableItem) {
            try {
                result = ((CharacterApplicableItem) selectedItem).use(theTarget);
            } catch (IllegalArgumentException e) {
                ProgramFileManager.getInstance().logException(e, false);
                return UNUSABLE;
            }
        } else if (selectedItem instanceof MapApplicableItem) {
            result = theIsInCombat ?
                     Util.NONE :
                     ((MapApplicableItem) selectedItem).use(theMap, theCoords);
        } else if (selectedItem instanceof RoomApplicableItem) {
            result = theIsInCombat ?
                     Util.NONE :
                     ((RoomApplicableItem) selectedItem).use(theRoom);
        } else {
            return UNUSABLE;
        }

        if (selectedItem.getCount() <= 0) {
            myItems.remove(selectedItem);
        }
        return result;
    }


    /**
     * Adds the collected item to the item's list or
     * the adventurer's inventory.
     *
     * @param theItem The collected item.
     */
    void addItem(final Item theItem) {
        for (Item existingItem : myItems) {
            if (theItem.isSameType(existingItem)) {
                existingItem.addToStack(theItem.getCount());
                return;
            }
        }

        myItems.add(theItem.copy());
    }

    /**
     * Adds the collected item to the item's list or
     * the adventurer's inventory.
     *
     * @param theItems The current list of collected item
     *                 from adventurer's inventory.
     */
    void addItems(final Item[] theItems) {
        for (Item item : theItems) {
            addItem(item);
        }
    }

    /**
     * Clears out or deletes all the items in the item's list
     */
    void clearItems() {
        myItems.clear();
    }

    /**
     * Accesses and checks if the current item's list or from the
     * adventurer's inventory contains any of the
     * four OOP pillars of the game.
     *
     * @return The boolean true or false if found any of the
     *          four OOP pillars current item's list or from the
     *          adventurer's inventory
     */
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
