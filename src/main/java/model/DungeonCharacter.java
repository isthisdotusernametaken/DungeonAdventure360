package model;

public abstract class DungeonCharacter extends DamageDealer {

    private final String myName;
    private int myMaxHP;
    private int myHP;
//    private final ResistancesData myResistances; //Need ResistanceData class
//    private final AdjustedCharacterStats myAdjustedStats; //Need AdjustedCharacterStats class
//    private final List<Buff> myBuffs; //Need Buff class

    DungeonCharacter(final String theName,
                     final int theMaxHP,
                     final int theHP,
                     final int theMinDamage,
                     final int theMaxDamage,
                     final double theHitChance,
                     final double theDebuffChance,
                     final DamageType theDamageType,
                     final int theSpeed/*,
                     final ResistanceData theResistances*/) { //Need ResistanceData class
        super(theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDamageType,
                theSpeed);

        myName = theName;
        myMaxHP = theMaxHP;
        myHP = theHP;
//        myResistances = theResistances; //Need ResistanceData class
    }

    final String getName() {
        return myName;
    }

    final int getMyMaxHP() {
        return myMaxHP;
    }

    final int getMyHP() {
        return myHP;
    }

//    final ResistancesData getMyResistances() { //Need ResistanceData class
//        return myResistances;
//    }

    final int getAdjustedMinDamage() {
        return 0;
    }

    final int getAdjustedMaxDamage() {
        return 0;
    }

    final double getAdjustedHitChance() {
        return 0;
    }

    final double getAdjustedDebuffChance() {
        return 0;
    }

    final int getAdjustedSpeed() {
        return 0;
    }

    final double getAdjustedResistance(final DamageType theDamageType) {
        return 0;
    }

    final void heal(final int theAmount) {

    }

    final AttackResult applyDamageAndBuff(final DamageType theDamageType,
                                          final int theDamage,
                                          final double theDebuffChance,
                                          final boolean theIsBlockable) {
        return null;
    }

    final void applyBuff(final BuffType theBuffType,
                         final int theDuration) {

    }

    final void clearBuffsAndDebuffs() {

    }

    final void clearBuffs() {

    }

    final void clearDebuffs() {

    }

    @Override
    final double getDebuffChance() {
    return 0;
    }

//    private Buff getBuff(final BuffType theBuffType) { //Need Buff Class
//        return null;
//    }

    final void advanceBuffs() {

    }
}

