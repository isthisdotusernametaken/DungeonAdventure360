/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a template to construct and handle all the items
 * in the dungeon adventure game.
 */
public abstract class Item implements CharRepresentable, Serializable {

    /**
     * The integer value representing the allowed maximum counts for
     * an item.
     */
    static final int MAX_STACK_SIZE = 999;

    /**
     * Alerts the adventurer that the item can not be used right now.
     */
    static final String CANNOT_USE_HERE = "This item cannot be used here.\n";

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -3510320815857469564L;

    /**
     * The char representation of the item.
     */
    private final char myRepresentation;

    /**
     * The type of the item.
     */
    private final ItemType myType;

    /**
     * The boolean true or false if the item can change count.
     */
    private final boolean myCanChangeCount;

    /**
     * The counts of the item.
     */
    private int myCount;

    /**
     * Constructor to construct the item for the dungeon
     * character to use.
     *
     * @param theRepresentation The character representation of the item.
     * @param theType           The type of the item.
     * @param theCanChangeCount The boolean true or false if the item can
     *                          change count.
     * @param theCount          The counts of the item.
     */
    Item(final char theRepresentation,
         final ItemType theType,
         final boolean theCanChangeCount,
         final int theCount) {
        myRepresentation = theRepresentation;
        myType = theType;
        myCanChangeCount = theCanChangeCount;
        myCount = Util.clampPositiveInt(theCount, MAX_STACK_SIZE);
    }

    /**
     * Creates and formats the type name of the item.
     *
     * @param theItem The item to get its type.
     * @return        The string name of the type of the item.
     */
    static String createNameFromType(final Item theItem) {
        return Util.createNameFromEnumName(theItem.myType);
    }

    /**
     * ToString method to display the item's name and the
     * item's count.
     *
     * @return The string representing the item's name and
     *         the item's count.
     */
    @Override
    public final String toString() {
        return getName() + ": " + myCount;
    }

    /**
     * Gets the character representation of the item.
     *
     * @return The character representation of the item.
     */
    public final char charRepresentation() {
        return myRepresentation;
    }

    /**
     * Gets the type of the item.
     *
     * @return The type of the item.
     */
    final ItemType getType() {
        return myType;
    }

    /**
     * Gets the count of the item.
     *
     * @return The integer value representing the
     *         count of the item.
     */
    final int getCount() {
        return myCount;
    }

    /**
     * Increments the count of the item.
     *
     * @param theCount The count of the item.
     */
    final void addToStack(final int theCount) {
        if (myCanChangeCount) {
            myCount = Util.addAndClampInt(
                    0, MAX_STACK_SIZE,
                    myCount, theCount
            );
        }
    }

    /**
     * Decrements the cont of the item.
     */
    final void consume() {
        if (myCanChangeCount) {
            myCount--;
        }
    }

    /**
     * Compares and checks if the item is the same item type.
     *
     * @param theOther The other item.
     * @return         The boolean true or false if the two
     *                 items are the same item.
     */
    final boolean isSameType(final Item theOther) {
        return myType == theOther.myType &&
               (
                       myType != ItemType.BUFF_POTION ||
                       ((BuffPotion) this).getBuffType() ==
                               ((BuffPotion) theOther).getBuffType()
               );
    }

    /**
     * Template for copy method.
     *
     * @return The copied item.
     */
    abstract Item copy();

    /**
     * Template for getName method.
     *
     * @return The item's name.
     */
    abstract String getName();
}
