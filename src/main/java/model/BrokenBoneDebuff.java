package model;

import java.io.Serial;

/**
 * This class represents brokenBone debuff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
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
     * Applies the buff and adjusts the character's speed.
     *
     * @param theStats The character's stats object to add the changes to.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
