package model;

public class SpeedBuff extends Buff {

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
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
