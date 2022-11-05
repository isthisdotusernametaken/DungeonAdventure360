package model;

public abstract class DungeonCharacter extends DamageDealer {

    private final String myName;
    private final int myMaxHP;
    private int myHP;
    private final double myBlockChance;
//    private final ResistancesData myResistances; //Need ResistanceData class
//    private final AdjustedCharacterStats myAdjustedStats; //Need AdjustedCharacterStats class
//    private final List<Buff> myBuffs; //Need Buff class

    DungeonCharacter(final String theName,
                     final int theMaxHP,
                     final int theMinDamage,
                     final int theMaxDamage,
                     final double theHitChance,
                     final double theDebuffChance,
                     final int theDebuffDuration,
                     final DamageType theDamageType,
                     final int theSpeed,
                     final double theBlockChance/*,
                     final ResistanceData theResistances*/) { //Need ResistanceData class
        super(theMinDamage,
              theMaxDamage,
              theHitChance,
              theDebuffChance,
              theDebuffDuration,
              theDamageType,
              theSpeed
        );

        myName = theName;
        myMaxHP = theMaxHP;
        myHP = myMaxHP;
        myBlockChance = theBlockChance;
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

    @Override
    final int getAdjustedMinDamage() {
        return 0;
    }

    @Override
    final int getAdjustedMaxDamage() {
        return 0;
    }

    @Override
    final double getAdjustedHitChance() {
        return 0;
    }

    @Override
    final double getAdjustedDebuffChance() {
        return 0;
    }

    @Override
    final int getAdjustedSpeed() {
        return 0;
    }

    final double getAdjustedResistance(final DamageType theDamageType) {
        return 0;
    }

    final int heal(final int theAmount) {
        final int theSum = myHP + theAmount;

        if (theSum > myMaxHP) {
            myHP = myMaxHP;
            return theAmount - (theSum - myMaxHP);
        }
        myHP = theSum;
        return theAmount;
    }

    final AttackResult applyDamageAndBuff(final DamageType theDamageType,
                                          final int theDamage,
                                          final double theDebuffChance,
                                          final boolean theIsBlockable,
                                          final int theDebuffDuration) {
        if (!(theIsBlockable && Util.probabilityTest(myBlockChance))) {
            if (applyDamage(theDamage, theDamageType)) {
                return AttackResult.KILL;
            }

            if (Util.probabilityTest(
                    adjustedDebuffChance(theDebuffChance, theDamageType))
            ) {
                applyBuff(theDamageType.getDebuffType(), theDebuffDuration);
                return AttackResult.HIT_DEBUFF;
            }

            return AttackResult.HIT_NO_DEBUFF;
        }
        return AttackResult.BLOCK;
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

//    private Buff getBuff(final BuffType theBuffType) { //Need Buff Class
//        return null;
//    }

    private void advanceBuffs() {

    }

    private double inverseAdjustedResistance(final DamageType theDamageType) {
        return 1.0 - getAdjustedResistance(theDamageType);
    }

    private boolean applyDamage(final int theBaseDamage,
                             final DamageType theDamageType) {
        myHP -= adjustedDamage(theBaseDamage, theDamageType);

        return myHP <= 0;
    }

    private int adjustedDamage(final int theBaseDamage,
                               final DamageType theDamageType) {
        return (int) (
                theBaseDamage *
                inverseAdjustedResistance(theDamageType)
        );
    }

    private double adjustedDebuffChance(final double theBaseDebuffChance,
                                        final DamageType theDamageType) {
        return theBaseDebuffChance * inverseAdjustedResistance(theDamageType);
    }
}

