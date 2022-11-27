package model;

public class BurningDebuff extends Buff {

    BurningDebuff(final int theDuration) {
        super(BuffType.BURNING, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setResistance(DamageType.FIRE, 0.1);

        while (!isCompleted()) {
            advance();
        }

        theStats.resetResistances();
    }
}
