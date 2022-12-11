package model;

import java.io.Serial;

public class BurningDebuff extends Buff {

    @Serial
    private static final long serialVersionUID = -7370213542881606792L;

    BurningDebuff(final int theDuration) {
        super(
                BuffType.BURNING,
                "All Resistances",
                0.8,
                0.06,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
