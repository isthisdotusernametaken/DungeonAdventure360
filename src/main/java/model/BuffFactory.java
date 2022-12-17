package model;

/**
 * This factory class helps to create buff objects and let subclasses
 * to decided which class object to instantiate to prevent duplication
 * of code.
 */
public class BuffFactory {

    /**
     * Creates the buff objects as options for subclass methods to
     * access and use.
     *
     * @param theType                   The type of the buff/debuff.
     * @param theDuration               The duration of the buff/debuff.
     * @return                          The buff object that the subclass called.
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
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
