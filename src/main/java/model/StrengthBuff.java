package model;

public class StrengthBuff extends Buff {

    StrengthBuff(final int theDuration) {
        super(BuffType.STRENGTH, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
