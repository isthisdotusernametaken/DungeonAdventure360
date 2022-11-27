package model;

public class BrokenBoneDebuff extends Buff {

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
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplySpeed(getStatMultiplier());
    }
}
