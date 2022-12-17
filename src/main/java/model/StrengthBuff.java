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
 * This class represents strength buff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 */
public class StrengthBuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -9067197301769819767L;

    /**
     * Constructs the strength buff withs its effects.
     *
     * @param theDuration The duration of how long the buff last.
     */
    StrengthBuff(final int theDuration) {
        super(
                BuffType.STRENGTH,
                "Min Damage, Max Damage",
                1.5,
                0.0,
                theDuration
        );
    }

    /**
     * Applies the buff and adjusts the dungeon character's stats.
     *
     * @param theStats  The dungeon character's class.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
    }
}
