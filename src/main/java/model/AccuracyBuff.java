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
 * This Buff increases the Adventurer's hit chance to decrease their miss rate.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class AccuracyBuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -8402496808168975310L;

    /**
     * Constructs the accuracy buff withs its effects.
     *
     * @param theDuration The duration of how long the buff last.
     */
    AccuracyBuff(final int theDuration) {
        super(
                BuffType.ACCURACY,
                "Hit Chance",
                1.3,
                0.0,
                theDuration
        );
    }

    /**
     * Applies the buff and adjusts the character's hit chance.
     *
     * @param theStats The character's stats object to add the changes to.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyHitChance(getStatMultiplier());
    }
}
