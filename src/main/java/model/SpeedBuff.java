package model;

import java.io.Serial;

/**
 * This class represents speed buff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
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
     * Applies the buff and adjusts the character's speed.
     *
     * @param theStats The character's stats object to add the changes to.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
