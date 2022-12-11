package model;

import java.io.Serial;

public class BleedingDebuff extends Buff {

    @Serial
    private static final long serialVersionUID = 300175452328245916L;

    BleedingDebuff(final int theDuration) {
        super(
                BuffType.BLEEDING,
                "All Resistances",
                0.7,
                0.08,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
