package model;

public class PoisonedDebuff extends Buff {

    PoisonedDebuff(final int theDuration) {
        super(BuffType.POISONED, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setResistance(DamageType.POISON,0.1);

        while(!isCompleted()) {
            advance();
        }

        theStats.resetResistances();
    }
}
