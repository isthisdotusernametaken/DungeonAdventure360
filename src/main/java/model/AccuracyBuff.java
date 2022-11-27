package model;

public class AccuracyBuff extends Buff {

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
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyHitChance(getStatMultiplier());
    }
}
