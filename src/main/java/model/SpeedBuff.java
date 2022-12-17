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
 * This class represents speed buff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 */
public class SpeedBuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -5133267484160054349L;

    /**
     * Constructs the speed buff withs its effects.
     *
     * @param theDuration The duration of how long the buff last.
     */
    SpeedBuff(final int theDuration) {
        super(
                BuffType.SPEED,
                "Speed",
                2.0,
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
        theStats.multiplySpeed(getStatMultiplier());
    }
}
