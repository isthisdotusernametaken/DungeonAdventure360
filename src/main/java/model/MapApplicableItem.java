package model;

import java.io.Serial;

/**
 * This class represents the items that are applicable only for the
 * dungeon map.
 * This class is a template class to create and construct methods
 * and let the subclasses implement and use the methods to handle
 * and modify the items that are applicable for the dungeon map.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class MapApplicableItem extends Item {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 3961450036630628818L;

    /**
     * Alerts the map has been updated.
     */
    private static final String MAP_UPDATED = "Map updated.";

    /**
     * Constructor to construct the map applicable items.
     *
     * @param theRepresentation The character representation of the item.
     * @param theType           The type of the item.
     * @param theCanChangeCount The boolean true or false if the item can
     *                          change count.
     * @param theCount          The count of the item.
     */
    MapApplicableItem(final char theRepresentation,
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
     * Accesses, uses and executes the item's effect on the dungeon map
     * when the adventurer chose to use that item.
     *
     * @param theTarget The dungeon map that the item's effect will be
     *                  applied on.
     * @param theCoords The coordinates of the room.
     * @return          The string results representing the effect process
     *                  after the item has been selected to use.
     */
    final String use(final Map theTarget,
                     final RoomCoordinates theCoords) {
        if (applyEffect(theTarget, theCoords)) {
            consume();
            return MAP_UPDATED;
        }

        return Item.CANNOT_USE_HERE;
    }

    /**
     * Abstract method for applyEffect method.
     *
     * @param theTarget The dungeon map that the item's effect will be
     *                  applied on.
     * @param theCoords The coordinates of the room.
     * @return          The boolean true or false.
     */
    abstract boolean applyEffect(Map theTarget, RoomCoordinates theCoords);
}
