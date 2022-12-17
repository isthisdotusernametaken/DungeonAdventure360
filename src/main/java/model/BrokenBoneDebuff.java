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
 * This class represents brokenBone debuff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 */
public class BrokenBoneDebuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -2110100001071953615L;

    /**
     * Constructs the broken bone debuff withs its effects.
     *
     * @param theDuration   The duration of how long the debuff last.
     */
    BrokenBoneDebuff(final int theDuration) {
        super(
                BuffType.BROKEN_BONE,
                "Speed",
                0.7,
                0.01,
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
