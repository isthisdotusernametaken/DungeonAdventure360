package model;

public class SpeedBuff extends Buff {

    SpeedBuff(final int theDuration) {
        super(BuffType.SPEED, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setSpeed(10);

        while (!isCompleted()) {
            advance();
        }

        theStats.resetStats();
    }
}
