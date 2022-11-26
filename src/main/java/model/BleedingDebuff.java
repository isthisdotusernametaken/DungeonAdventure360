package model;

public class BleedingDebuff extends Buff {

    BleedingDebuff(final int theDuration) {
        super(BuffType.BLEEDING, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
