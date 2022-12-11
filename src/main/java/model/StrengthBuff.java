package model;

import java.io.Serial;

public class StrengthBuff extends Buff {

    @Serial
    private static final long serialVersionUID = -9067197301769819767L;

    StrengthBuff(final int theDuration) {
        super(
                BuffType.STRENGTH,
                "Min Damage, Max Damage",
                1.5,
                0.0,
                theDuration
        );
    }

    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
    }
}
