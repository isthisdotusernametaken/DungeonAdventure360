package model;

public class BrokenBoneDebuff extends Buff {

    BrokenBoneDebuff(final int theDuration) {
        super(BuffType.BROKEN_BONE, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setResistance(DamageType.BLUNT, 0.1);

        while (!isCompleted()) {
            advance();
        }

        theStats.resetResistances();
    }
}
