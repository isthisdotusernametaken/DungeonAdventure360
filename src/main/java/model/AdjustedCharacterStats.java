package model;

public class AdjustedCharacterStats {

    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private double myDebuffChance;
    private int mySpeed;
    private final double[] myResistances;
    private final DungeonCharacter myCharacter;

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

    double getDebuffChance() {
        return myDebuffChance;
    }

    int getSpeed() {
        return mySpeed;
    }

    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    void setMinDamage(final int theMinDamage) {
        myMinDamage = theMinDamage;
    }

    void setMaxDamage(final int theMaxDamage) {
        myMaxDamage = theMaxDamage;
    }

    void setHitChance(final double theHitChance) {
        myHitChance = Util.clampFraction(theHitChance);
    }

    void setDebuffChance(final double theDebuffChance) {
        myDebuffChance = Util.clampFraction(theDebuffChance);

    }

    void setSpeed(final int theSpeed) {
        this.mySpeed = Util.clampPositiveInt(theSpeed);
    }

    void setResistance(final DamageType theDamageType,
                       final double theResistance) {
        myResistances[theDamageType.ordinal()] = Util.clampFraction(
                theResistance
        );
    }

    void resetStats() {
    }

    void resetResistance() {

    }
}
