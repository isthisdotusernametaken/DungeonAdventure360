package model;

import java.io.Serial;

public class PoisonedDebuff extends Buff {

    @Serial
    private static final long serialVersionUID = 587409466602432990L;

    PoisonedDebuff(final int theDuration) {
        super(
                BuffType.POISONED,
                "Min Damage, Max Damage, All Resistances",
                0.8,
                0.05,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
        theStats.multiplyResistances(getStatMultiplier());
    }
}
