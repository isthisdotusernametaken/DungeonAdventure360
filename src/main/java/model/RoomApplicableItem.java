/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;

/**
 * This class represents the items that are applicable only for the
 * dungeon room.
 * This class is a template class to create and construct methods
 * and let the subclasses implement and use the methods to handle
 * and modify the items that are applicable for the dungeon room.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class RoomApplicableItem extends Item {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -9020731178990496158L;

    /**
     * Constructor to construct the room applicable items.
     *
     * @param theRepresentation The character representation of the item.
     * @param theType           The type of the item.
     * @param theCanChangeCount The boolean true or false if the item can
     *                          change count.
     * @param theCount          The count of the item.
     */
    RoomApplicableItem(final char theRepresentation,
                       final ItemType theType,
                       final boolean theCanChangeCount,
                       final int theCount) {
        super(
                theRepresentation,
                theType,
                theCanChangeCount,
                theCount
        );
    }

    /**
     * Accesses, uses and executes the item's effect on the dungeon room
     * when the adventurer chose to use that item.
     *
     * @param theTarget The dungeon room that the item's effect will be
     *                  applied on.
     * @return          The string results representing the effect process
     *                  after the item has been selected to use.
     */
    final String use(final Room theTarget) {
        final String result = applyEffect(theTarget);
        if (!Util.NONE.equals(result)) {
            consume();
            return result;
        }
        return Item.CANNOT_USE_HERE;
    }

    /**
     * Abstract method for applyEffect method.
     *
     * @param theTarget The dungeon room that the item's effect will be
     *                  applied on.
     * @return          The boolean true or false.
     */
    abstract String applyEffect(Room theTarget);
}
