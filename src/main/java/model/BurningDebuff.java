package model;

import java.io.Serial;

/**
 * This class represents burning debuff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 */
public class BurningDebuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -7370213542881606792L;

    /**
     * Constructs the burning debuff withs its effects.
     *
     * @param theDuration The duration of how long the debuff last.
     */
    BurningDebuff(final int theDuration) {
        super(
                BuffType.BURNING,
                "All Resistances",
                0.8,
                0.06,
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
