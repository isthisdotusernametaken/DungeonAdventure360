package model;

import java.io.Serial;
import java.util.Arrays;

/**
 * This class represents Buff potion item that will be applied on and used by the
 * adventurer both in exploration mode and combat mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class BuffPotion extends CharacterApplicableItem {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 8841704767797465686L;

    /**
     * The string array representing all the names of the buff type
     * that the buff potion may have.
     */
    private static final String[] NAMES =
            Arrays.stream(BuffType.getAllPositiveBuffTypes())
            .map(buffType -> buffType.toString() + " Potion")
            .toArray(String[]::new);

    /**
     * The integer value representing the minimum duration of the potion.
     */
    private static final int MIN_DURATION = 2;

    /**
     * The integer value representing the maximum duration of the potion.
     */
    private static final int MAX_DURATION = 5;

    /**
     * A buff type that the buff potion contains.
     */
    private final BuffType myBuffType;

    /**
     * Constructor to construct the buff potion with its effect
     *
     * @param theCount    The counts of the potion.
     * @param theBuffType The buff type that the buff potion contains.
     */
    BuffPotion(final int theCount, final BuffType theBuffType) {
        super(
                theBuffType.charRepresentation(),
                ItemType.BUFF_POTION,
                true,
                theCount
        );

        myBuffType = theBuffType;
    }

    /**
     * Gets the buff type that the buff potion contains.
     *
     * @return The buff type that the buff potion contains.
     */
    BuffType getBuffType() {
        return myBuffType;
    }

    /**
     * Executes and applies the effect of the buff potion depending on
     * which type of buff it contains.
     *
     * @param theTarget                 The dungeon character that the potion
     *                                  will be applied on.
     * @return                          The string result representing the
     *                                  effect process when the potion is
     *                                  applied.
     *
     * @throws IllegalArgumentException Thrown if theTarget is null.
     */
    @Override
    String applyEffect(final DungeonCharacter theTarget)
            throws IllegalArgumentException {
        final int duration = Util.randomIntInc(MIN_DURATION, MAX_DURATION);
        theTarget.applyBuff(myBuffType, duration);

        return myBuffType + " +" + duration + " turns";
    }

    /**
     * Creates a copy of the buff potion.
     *
     * @return The buff potion item.
     */
    @Override
    Item copy() {
        return new BuffPotion(getCount(), myBuffType);
    }

    /**
     * Gets the name of the buff type potion.
     *
     * @return The name of the buff type potion.
     */
    @Override
    String getName() {
        return NAMES[myBuffType.ordinal() - 1];
    }
}
