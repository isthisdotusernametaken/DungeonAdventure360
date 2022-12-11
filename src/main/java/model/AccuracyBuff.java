package model;

import java.io.Serial;

public class AccuracyBuff extends Buff {

    @Serial
    private static final long serialVersionUID = -8402496808168975310L;

    AccuracyBuff(final int theDuration) {
        super(
                BuffType.ACCURACY,
                "Hit Chance",
                1.3,
                0.0,
                theDuration
        );
    }

    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyHitChance(getStatMultiplier());
    }
}
