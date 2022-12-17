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
 * This class represents poisoned debuff that will be applied on the
 * adventurer both in exploration mode and combat mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class PoisonedDebuff extends Buff {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 587409466602432990L;

    /**
     * Constructs the poisoned debuff withs its effects.
     *
     * @param theDuration The duration of how long the debuff last.
     */
    PoisonedDebuff(final int theDuration) {
        super(
                BuffType.POISONED,
                "Min Damage, Max Damage, All Resistances",
                0.8,
                0.05,
                theDuration
        );
    }

    /**
     * Applies the buff and adjusts the character's resistances and damage.
     *
     * @param theStats The character's stats object to add the changes to.
     */
    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
        theStats.multiplyResistances(getStatMultiplier());
    }
}
