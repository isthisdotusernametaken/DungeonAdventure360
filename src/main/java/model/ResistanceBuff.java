package model;

import java.io.Serial;

/**
 * This class represents resistance buff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class ResistanceBuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -7682020618949096957L;

    /**
     * Constructs the resistance buff withs its effects.
     *
     * @param theDuration The duration of how long the debuff last.
     */
    ResistanceBuff(final int theDuration) {
        super(
                BuffType.RESISTANCE,
                "All Resistances",
                2.5,
                0.0,
                theDuration
        );
    }

    /**
     * Applies the buff and adjusts the character's resistances.
     *
     * @param theStats The character's stats object to add the changes to.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
