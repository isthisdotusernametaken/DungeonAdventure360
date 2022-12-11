package model;

public class BuffFactory {

    static Buff create(final BuffType theType, final int theDuration)
            throws IllegalArgumentException {
        final int duration = Util.clampPositiveInt(theDuration);
        return switch (theType) {
            case STRENGTH -> new StrengthBuff(duration);
            case SPEED -> new SpeedBuff(duration);
            case ACCURACY -> new AccuracyBuff(duration);
            case RESISTANCE -> new ResistanceBuff(duration);
            case BROKEN_BONE -> new BrokenBoneDebuff(duration);
            case BURNING -> new BurningDebuff(duration);
            case BLEEDING -> new BleedingDebuff(duration);
            case POISONED -> new PoisonedDebuff(duration);
            default -> throw new IllegalArgumentException(
                    "Invalid buff type: " + theType
            );
        };
    }
}
