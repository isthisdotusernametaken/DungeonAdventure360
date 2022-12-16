package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class handles, gets and adjusts the dungeon character's stats whenever
 * a buff is applied on the character.
 */
public class AdjustedCharacterStats implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -3232722312066269409L;

    /**
     * The dungeon character to be adjusted in the stats.
     */
    private final DungeonCharacter myCharacter;

    /**
     * The minimum dungeon character damage.
     */
    private int myMinDamage;

    /**
     * The maximum dungeon character damage.
     */
    private int myMaxDamage;

    /**
     * The hit chance of the dungeon character.
     */
    private double myHitChance;

    /**
     * The speed of the dungeon character.
     */
    private int mySpeed;

    /**
     * The list of resistance percentage of the dungeon character to
     * certain damage types.
     */
    private final double[] myResistances;

    /**
     * Constructor of AdjustedCharacterStats to handles the stats and
     * resets the dungeon character's stats whenever it is called.
     *
     * @param theCharacter The dungeon character that will be adjusted in stats.
     */
    AdjustedCharacterStats(final DungeonCharacter theCharacter) {
        myResistances = new double[DamageType.values().length];
        myCharacter = theCharacter;

        resetStats();
    }

    /**
     * Generates and displays the dungeon character's list of resistances as string.
     *
     * @return The string list of resistances.
     */
    String getResistancesAsString() {
        final StringBuilder builder = new StringBuilder(" Resistances - ");

        int i = 0;
        for (DamageType type : DamageType.values()) {
            builder.append(type).append(": ")
                   .append(Util.asPercent(myResistances[i++])).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());

        return builder.toString();
    }

    /**
     * Gets the minimum dungeon character damage.
     *
     * @return The minimum dungeon character damage.
     */
    int getMinDamage() {
        return myMinDamage;
    }

    /**
     * Gets the maximum dungeon character damage.
     *
     * @return The maximum dungeon character damage.
     */
    int getMaxDamage() {
        return myMaxDamage;
    }

    /**
     * Gets the hit chance of the dungeon character.
     *
     * @return The hit chance of the dungeon.
     */
    double getHitChance() {
        return myHitChance;
    }

    /**
     * Gets the speed of the dungeon character.
     *
     * @return The speed of the dungeon.
     */
    int getSpeed() {
        return mySpeed;
    }

    /**
     * Get the value of the dungeon character's resistance of the
     * certain damage types.
     *
     * @param theDamageType The damage type to indicate which resistance value
     *                      is associated with that damage type.
     * @return The double value of the dungeon character's resistance.
     */
    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    /**
     * Gets and adjusts the dungeon character's damage stats.
     *
     * @param theMultiplier The multiplier value to adjust
     *                      the damage stats.
     */
    void multiplyDamage(final double theMultiplier) {
        myMinDamage = Util.clampPositiveInt((int) (myMinDamage * theMultiplier));
        myMaxDamage = Util.clampPositiveInt((int) (myMaxDamage * theMultiplier));
    }

    /**
     * Gets and adjusts the dungeon character's hit chance stat.
     *
     * @param theMultiplier The multiplier value to adjust
     *                      the hit chance stat.
     */
    void multiplyHitChance(final double theMultiplier) {
        myHitChance = Util.clampFraction(myHitChance * theMultiplier);
    }

    /**
     * Gets and adjusts the dungeon character's speed stat.
     *
     * @param theMultiplier The multiplier value to adjust
     *                      the speed stat.
     */
    void multiplySpeed(final double theMultiplier) {
        mySpeed = Util.clampPositiveInt((int) (mySpeed * theMultiplier));
    }

    /**
     * Gets and adjusts the dungeon character's resistance stats.
     *
     * @param theMultiplier The multiplier value to adjust
     *                      the resistance stats.
     */
    void multiplyResistances(final double theMultiplier) {
        for (int i = 0; i < myResistances.length; i++) {
            myResistances[i] = Util.clampFraction(
                    myResistances[i] * theMultiplier
            );
        }
    }

    /**
     * Adjusts and resets the dungeon character's stats to
     * its base stats.
     */
    void resetStats() {
        myMinDamage = myCharacter.getMinDamage();
        myMaxDamage = myCharacter.getMaxDamage();
        myHitChance = myCharacter.getHitChance();
        mySpeed = myCharacter.getSpeed();

        resetResistances();
    }

    /**
     * Adjusts and resets the dungeon character's resistance stats to
     * its base stats.
     */
    private void resetResistances() {
        final ResistanceData baseResistances = myCharacter.getResistances();
        for (int i = 0; i < myResistances.length; i++) {
            myResistances[i] = baseResistances.getResistance(i);
        }
    }
}
