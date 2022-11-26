package model;

public class BrokenBoneDebuff extends Buff {

    BrokenBoneDebuff(final int theDuration) {
        super(BuffType.BROKEN_BONE, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
