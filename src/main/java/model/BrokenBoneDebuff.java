package model;

import java.io.Serial;

public class BrokenBoneDebuff extends Buff {

    @Serial
    private static final long serialVersionUID = -2110100001071953615L;

    BrokenBoneDebuff(final int theDuration) {
        super(
                BuffType.BROKEN_BONE,
                "Speed",
                0.7,
                0.01,
                theDuration
        );
    }

    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
