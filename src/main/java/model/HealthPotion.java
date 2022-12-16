package model;

import java.io.Serial;

public class HealthPotion extends CharacterApplicableItem {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 3436290390297215184L;

    /**
     * The string representing the name of the potion item
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
     * @param theCount The quantitative of healing potion.
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
     * @param theTarget The dungeon character that the potion will be
     *                  applied on.
     * @return          The string result representing the effect process
     *                  when the potion is applied.
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
     * @return The buff potion item.
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
