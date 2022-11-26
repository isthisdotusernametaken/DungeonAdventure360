package model;

public class PoisonedDebuff extends Buff {

    PoisonedDebuff(final int theDuration) {
        super(BuffType.POISONED, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
