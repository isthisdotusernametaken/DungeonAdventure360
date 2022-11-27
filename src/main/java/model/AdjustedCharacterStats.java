package model;

import java.io.Serializable;

public class AdjustedCharacterStats implements Serializable {

    private final DungeonCharacter myCharacter;
    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private int mySpeed;
    private final double[] myResistances;

    AdjustedCharacterStats(DungeonCharacter theCharacter) {
        myResistances = new double[DamageType.values().length];
        myCharacter = theCharacter;

        resetStats();
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
        myMinDamage *= theMultiplier;
        myMaxDamage *= theMultiplier;
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

    void resetResistances() {
        final ResistanceData baseResistances = myCharacter.getResistances();
        for (int i = 0; i < myResistances.length; i++) {
            myResistances[i] = baseResistances.getResistance(i);
        }
    }
}
