package model;

import java.util.ArrayList;
import java.util.List;

public abstract class DungeonCharacter extends DamageDealer {

    private final int myMaxHP;
    private int myHP;
    private final double myBlockChance;
    private final ResistanceData myResistances;
    private final AdjustedCharacterStats myAdjustedStats;
    private final List<Buff> myBuffs;

    DungeonCharacter(final String theName,
                     final int theMaxHP,
                     final int theMinDamage,
                     final int theMaxDamage,
                     final double theHitChance,
                     final double theDebuffChance,
                     final int theDebuffDuration,
                     final DamageType theDamageType,
                     final int theSpeed,
                     final double theBlockChance,
                     final ResistanceData theResistances) {
        super(theName,
              theMinDamage,
              theMaxDamage,
              theHitChance,
              theDebuffChance,
              theDebuffDuration,
              theDamageType,
              theSpeed
        );

        myMaxHP = theMaxHP;
        myHP = myMaxHP;
        myBlockChance = theBlockChance;
        myResistances = theResistances;
        myAdjustedStats = new AdjustedCharacterStats(this);
        myBuffs = new ArrayList<>();
    }

    final int getMaxHP() {
        return myMaxHP;
    }

    final int getHP() {
        return myHP;
    }

    final double getBlockChance() {
        return myBlockChance;
    }

    final ResistanceData getResistances() {
        return myResistances;
    }

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

