package model;

import java.io.Serial;
import java.io.Serializable;

public class AdjustedCharacterStats implements Serializable {

    @Serial
    private static final long serialVersionUID = -3232722312066269409L;

    private final DungeonCharacter myCharacter;
    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private int mySpeed;
    private final double[] myResistances;

    AdjustedCharacterStats(final DungeonCharacter theCharacter) {
        myResistances = new double[DamageType.values().length];
        myCharacter = theCharacter;

        resetStats();
    }

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

    int getMinDamage() {
        return myMinDamage;
    }

    int getMaxDamage() {
        return myMaxDamage;
    }

    double getHitChance() {
        return myHitChance;
    }

    int getSpeed() {
        return mySpeed;
    }

    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    void multiplyDamage(final double theMultiplier) {
        myMinDamage = Util.clampPositiveInt((int) (myMinDamage * theMultiplier));
        myMaxDamage = Util.clampPositiveInt((int) (myMaxDamage * theMultiplier));
    }

    void multiplyHitChance(final double theMultiplier) {
        myHitChance = Util.clampFraction(myHitChance * theMultiplier);
    }

    void multiplySpeed(final double theMultiplier) {
        mySpeed = Util.clampPositiveInt((int) (mySpeed * theMultiplier));
    }

    void multiplyResistances(final double theMultiplier) {
        for (int i = 0; i < myResistances.length; i++) {
            myResistances[i] = Util.clampFraction(
                    myResistances[i] * theMultiplier
            );
        }
    }

    void resetStats() {
        myMinDamage = myCharacter.getMinDamage();
        myMaxDamage = myCharacter.getMaxDamage();
        myHitChance = myCharacter.getHitChance();
        mySpeed = myCharacter.getSpeed();

        resetResistances();
    }

    public void resetResistances() {
        final ResistanceData baseResistances = myCharacter.getResistances();
        for (int i = 0; i < myResistances.length; i++) {
            myResistances[i] = baseResistances.getResistance(i);
        }
    }
}
