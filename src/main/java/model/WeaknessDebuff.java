package model;

public class WeaknessDebuff extends Buff {

    WeaknessDebuff(final int theDuration) {
        super(BuffType.WEAKNESS, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
