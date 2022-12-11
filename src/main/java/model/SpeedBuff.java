package model;

import java.io.Serial;

public class SpeedBuff extends Buff {

    @Serial
    private static final long serialVersionUID = -5133267484160054349L;

    SpeedBuff(final int theDuration) {
        super(
                BuffType.SPEED,
                "Speed",
                2.0,
                0.0,
                theDuration
        );
    }

    @Override
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
