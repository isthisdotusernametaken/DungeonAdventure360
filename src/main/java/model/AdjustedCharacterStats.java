package model;

public class AdjustedCharacterStats {

    private final DungeonCharacter myCharacter;
    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private double myDebuffChance;
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
        myResistances[theDamageType.ordinal()] = Util.clampFraction(
                theResistance
        );
    }

    void resetStats() {
        myMinDamage = myCharacter.getMinDamage();
        myMaxDamage = myCharacter.getMaxDamage();
        myHitChance = myCharacter.getHitChance();
        myDebuffChance = myCharacter.getDebuffChance();
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
