package model;

import java.io.Serial;

/**
 * This class represents Accuracy Buff that will be applied on and used by the
 * adventurer both in exploration mode and combat mode.
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
     * Applies the buff and adjusts the dungeon character's stats.
     *
     * @param theStats  The dungeon character's class.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyHitChance(getStatMultiplier());
    }
}
