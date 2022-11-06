package model;

public class AdjustedCharacterStats {

    private int myMinDamage;
    private int myMaxDamage;
    private double myHitChance;
    private double myDebuffChance;
    private int mySpeed;
    private final double[] myResistance;

    AdjustedCharacterStats(DungeonCharacter theCharacter){

    }
    int getMinDamage() {
        return myMinDamage;
    }
    int getMaxDamage() {
        return myMaxDamage;
    }
    double getDebuffChance() {
        return myDebuffChance;
    }
    DamageType getDamageType() {

        return null;
    }
    int getSpeed() {
        return mySpeed;
    }

    double[] getResistance(DamageType theDamageType) {

        return myResistance;
    }
    void setMinDamage(int theMinDamage) {
        this.myMinDamage = theMinDamage;

    }
    void setMaxDamage(int theMaxDamage) {
        this.myMaxDamage = theMaxDamage;

    }
    void setHitChance(double theHitChance) {
        this.myHitChance = theHitChance;

    }
    void setDebuffChance(double theDebuffChance) {
        this.myDebuffChance = theDebuffChance;

    }
    void setSpeed(int theSpeed) {
        this.mySpeed = theSpeed;

    }
    void setResistance(DamageType theDamageType,
                       double theResistance) {


    }
    void resetStats(DungeonCharacter theCharacter){

    }
    void resetResistance(DungeonCharacter theCharacter){

    }


}
