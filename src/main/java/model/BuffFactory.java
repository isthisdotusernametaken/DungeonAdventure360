package model;

public class BuffFactory {

    static Buff create(final BuffType theType, final int theDuration) {
        return switch (theType) {
            case STRENGTH -> new StrengthBuff(theDuration);
            case SPEED -> new SpeedBuff(theDuration);
            case ACCURACY -> new AccuracyBuff(theDuration);
            case RESISTANCE -> new ResistanceBuff(theDuration);
            case BROKEN_BONE -> new BrokenBoneDebuff(theDuration);
            case BURNING -> new BurningDebuff(theDuration);
            case BLEEDING -> new BleedingDebuff(theDuration);
            case POISONED -> new PoisonedDebuff(theDuration);
            default -> throw new IllegalStateException(
                    "Invalid buff type: " + theType
            );
        };
    }
}
