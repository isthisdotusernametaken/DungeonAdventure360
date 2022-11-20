package model;

public class AdjustedCharacterStats {
    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private double myDebuffChance;
    private int mySpeed;
    private final double[] myResistances; //***

//    DungeonCharacter theCharacter;

    AdjustedCharacterStats(DungeonCharacter theCharacter) {
        myResistances = new double[DamageType.values().length];
//        this.theCharacter = theCharacter;//***
//        myMinDamage = theCharacter.getMinDamage();
//        myMaxDamage = theCharacter.getMaxDamage();
//        mySpeed = theCharacter.getSpeed();
        resetStats(theCharacter);
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
        this.myMinDamage = theMinDamage;
    }

    void setMaxDamage(final int theMaxDamage) {
        this.myMaxDamage = theMaxDamage;
    }

    void setHitChance(final double theHitChance) {
        this.myHitChance = Util.clampFraction(theHitChance);

    }

    void setDebuffChance(final double theDebuffChance) {
        this.myDebuffChance = Util.clampFraction(theDebuffChance);

    }

    void setSpeed(final int theSpeed) {
        this.mySpeed = Util.clampPositiveInt(theSpeed);
    }

    void setResistance(final DamageType theDamageType,
                       final double theResistance) {


    }

    void resetStats(final DungeonCharacter theCharacter) {
    }

    void resetResistance(final DungeonCharacter theCharacter) {

    }
}
