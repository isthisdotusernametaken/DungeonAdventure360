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
 * This class constructs, handles, and applies the vision potion item that
 * will be used by the adventurer's character.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class VisionPotion extends MapApplicableItem {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 5502632545052449037L;

    /**
     * The string representing the name of the vision potion.
     */
    private static final String NAME = createNameFromType(new VisionPotion(0));

    /**
     * The character representing the character symbol of
     * a vision potion.
     */
    private static final char REPRESENTATION = 'V';

    /**
     * Constructor to construct the vision potion.
     *
     * @param theCount The counts of the vision potion.
     */
    VisionPotion(final int theCount) {
        super(
                REPRESENTATION,
                ItemType.VISION_POTION,
                true,
                theCount
        );
    }

    /**
     * Executes and applies the effect of the vision potion.
     *
     * @param theTarget                 The dungeon character that the potion
     *                                  will be applied on.
     * @return                          The string result representing the
     *                                  effect process when the potion is
     *                                  applied.
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    @Override
    boolean applyEffect(final Map theTarget, final RoomCoordinates theCoords) {
        boolean anyUnexplored = false;
        for (int i = theCoords.getX() - 1; i <= theCoords.getX() + 1; i++) {
            for (int j = theCoords.getY() - 1; j <= theCoords.getY() + 1; j++) {
                anyUnexplored = anyUnexplored || (
                        theTarget.isInBounds(theCoords.getFloor(), i, j) &&
                        !theTarget.isExplored(theCoords.getFloor(), i, j)
                );
                theTarget.explore(theCoords.getFloor(), i, j);
            }
        }

        return anyUnexplored;
    }

    /**
     * Creates a copy of the vision potion.
     *
     * @return The vision potion.
     */
    @Override
    Item copy() {
        return new VisionPotion(getCount());
    }

    /**
     * Gets the name of the vision potion.
     *
     * @return The name of the vision potion.
     */
    @Override
    String getName() {
        return NAME;
    }
}
