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
 * This class constructs, handles, and applies the health potion item that
 * will be used by the adventurer's character.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class HealthPotion extends CharacterApplicableItem {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 3436290390297215184L;

    /**
     * The string representing the name of the potion.
     */
    private static final String NAME = createNameFromType(
            new HealthPotion(0)
    );

    /**
     * The character representing the character symbol of
     * a healing potion.
     */
    private static final char REPRESENTATION = 'H';

    /**
     * The integer value representing the minimum healing
     * amount.
     */
    private static final int MIN_HEAL = 15;

    /**
     * The integer value representing the maximum healing
     * amount.
     */
    private static final int MAX_HEAL = 30;

    /**
     * Constructor to construct the healing potion.
     *
     * @param theCount The counts of the healing potion.
     */
    HealthPotion(final int theCount) {
        super(
                REPRESENTATION,
                ItemType.HEALTH_POTION,
                true,
                theCount
        );
    }

    /**
     * Executes and applies the effect of the healing potion.
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
    String applyEffect(final DungeonCharacter theTarget) {
        final int amountHealed = theTarget.heal(
                Util.randomIntInc(MIN_HEAL, MAX_HEAL)
        );

        return amountHealed == 0 ?
               Util.NONE :
               "Healed " + amountHealed + " hp and cleared all debuffs.";
    }

    /**
     * Creates a copy of the buff potion.
     *
     * @return The buff potion.
     */
    @Override
    Item copy() {
        return new HealthPotion(getCount());
    }

    /**
     * Gets the name of the buff type potion.
     *
     * @return The name of the buff type potion.
     */
    @Override
    String getName() {
        return NAME;
    }
}
