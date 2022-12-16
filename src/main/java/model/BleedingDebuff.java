package model;

import java.io.Serial;

/**
 * This class represents bleeding debuff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 */
public class BleedingDebuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 300175452328245916L;

    /**
     * Constructs the bleeding debuff withs its effects.
     *
     * @param theDuration The duration of how long the debuff last.
     */
    BleedingDebuff(final int theDuration) {
        super(
                BuffType.BLEEDING,
                "All Resistances",
                0.7,
                0.08,
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
        theStats.multiplyResistances(getStatMultiplier());
    }
}
