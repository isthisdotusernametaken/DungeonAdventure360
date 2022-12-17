package model;

/**
 * This factory produces Buffs from BuffTypes.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class BuffFactory {

    /**
     * Creates a Buff of the specified type and with the specified duration.
     *
     * @param theType The type of the buff/debuff.
     * @param theDuration The duration of the buff/debuff.
     * @return The constructed Buff.
     *
     * @throws IllegalArgumentException Indicates an invalid BuffType was
     *                                  provided
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
