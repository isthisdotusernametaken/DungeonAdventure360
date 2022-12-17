package model;

import java.io.Serial;

/**
 * This class constructs, handles, and applies the planks that will be used by the
 * adventurer's character.
 */
public class Planks extends RoomApplicableItem {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -4102401273626104641L;

    /**
     * The string representing the name of the planks.
     */
    private static final String NAME = createNameFromType(new Planks(0));

    /**
     * The character representing the character symbol of
     * a plank.
     */
    private static final char REPRESENTATION = '=';

    /**
     * Alerts the planks have been applied successfully.
     */
    private static final String SUCCESS_MSG = "Trap boarded.\n";

    /**
     * Constructor to construct the planks.
     *
     * @param theCount The counts of the planks.
     */
    Planks(final int theCount) {
        super(
                REPRESENTATION,
                ItemType.PLANKS,
                true,
                theCount
        );
    }

    /**
     * Executes and applies the effect of the planks.
     *
     * @param theTarget                 The dungeon character that the item
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
    String applyEffect(final Room theTarget) {
        return theTarget.boardTrap() ?
               SUCCESS_MSG :
               Util.NONE;
    }

    /**
     * Creates a copy of the plank.
     *
     * @return The plank item.
     */
    @Override
    Item copy() {
        return new Planks(getCount());
    }

    /**
     * Gets the name of the plank.
     *
     * @return The name of the plank item.
     */
    @Override
    String getName() {
        return NAME;
    }
}
