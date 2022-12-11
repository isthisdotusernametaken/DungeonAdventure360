package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public abstract class DungeonCharacter extends DamageDealer {

    @Serial
    private static final long serialVersionUID = -6220105292148511769L;

    private String myName;
    private final int myMaxHP;
    private int myHP;
    private final double myBlockChance;
    private final ResistanceData myResistances;
    private final AdjustedCharacterStats myAdjustedStats;
    private final List<Buff> myBuffs;

    DungeonCharacter(final String theName,
                     final String theClass,
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
        super(theClass,
              theMinDamage,
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
        myResistances = theResistances;
        myAdjustedStats = new AdjustedCharacterStats(this);
        myBuffs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return myName + '\n' +
               ' ' + getClassName() + ", " +
               myHP + '/' + myMaxHP + " HP" +
               '\n' +

               " Base Damage: " + getAdjustedMinDamage() + '-' +
                   getAdjustedMaxDamage() + ", " +
               getDamageType() + ", " +
               Util.asPercent(getAdjustedHitChance()) + " Accuracy" +
               '\n' +

               ' ' + getDebuffInfoAsString() +
               '\n' +

               " Speed: " + getAdjustedSpeed() + ", " +
               Util.asPercent(myBlockChance) + " Block Chance" +
               '\n' +

               myAdjustedStats.getResistancesAsString() +
               '\n' +

               getBuffsAsString();
    }

    final String getName() {
        return myName;
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
        return myAdjustedStats.getMinDamage();
    }

    @Override
    final int getAdjustedMaxDamage() {
        return myAdjustedStats.getMaxDamage();
    }

    @Override
    final double getAdjustedHitChance() {
        return myAdjustedStats.getHitChance();
    }

    @Override
    final int getAdjustedSpeed() {
        return myAdjustedStats.getSpeed();
    }

    final double getAdjustedResistance(final DamageType theDamageType) {
        return myAdjustedStats.getResistance(theDamageType);
    }

    final void setName(final String theNewName) {
        myName = theNewName;
    }

    final int percentOfMaxHP(final double thePercent) {
        return (int) (myMaxHP * thePercent);
    }

    final int heal(final int theAmount) {
        clearDebuffs();

        final int theSum = myHP + theAmount;
        if (theSum > myMaxHP) {
            myHP = myMaxHP;
            return theAmount - (theSum - myMaxHP);
        }
        myHP = theSum;
        return theAmount;
    }

    final AttackResultAndAmount applyDamageAndBuff(final DamageType theDamageType,
                                                   final int theDamage,
                                                   final double theDebuffChance,
                                                   final int theDebuffDuration,
                                                   final boolean theIsBlockable) {
        if (!(theIsBlockable && Util.probabilityTest(myBlockChance))) {
            final int damage = applyAdjustedDamage(theDamage, theDamageType);

            AttackResult result;
            if (isDead()) {
                result = AttackResult.KILL;
            } else if (Util.probabilityTest(
                    adjustedDebuffChance(theDebuffChance, theDamageType))
            ) {
                applyBuff(theDamageType.getDebuffType(), theDebuffDuration);
                result = AttackResult.HIT_DEBUFF;
            } else {
                result = AttackResult.HIT_NO_DEBUFF;
            }

            return new AttackResultAndAmount(result, damage);
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.BLOCK);
    }

    final void applyBuff(final BuffType theBuffType,
                         final int theDuration) {
        Buff buff = getBuff(theBuffType);
        if (buff != null) {
            buff.changeDuration(theDuration);
        } else {
            buff = BuffFactory.create(theBuffType, theDuration);

            myBuffs.add(buff);
            buff.adjustStats(myAdjustedStats);
        }
    }

    final AttackResultAndAmount advanceBuffsAndDebuffs() {
        return advanceBuffs(true);
    }

    final AttackResultAndAmount advanceDebuffs() {
        return advanceBuffs(false);
    }

    private AttackResultAndAmount advanceBuffs(final boolean theAllBuffs) {
        List<Buff> toRemove = new ArrayList<>();

        final int previousHP = myHP;
        boolean dead = false;
        for (Buff buff : myBuffs) {
            if (theAllBuffs || buff.getType().isDebuff()) {
                buff.advance();
                dead = applyDamageFromBuff(buff) || dead;

                if (buff.isCompleted()) {
                    toRemove.add(buff);
                }
            }
        }

        myBuffs.removeAll(toRemove);
        if (!toRemove.isEmpty()) {
            reapplyBuffs();
        }

        final int damage = previousHP - myHP;
        return damage == 0 ?
               AttackResultAndAmount.getNoAmount(
                       AttackResult.NO_ACTION
               ) :
               dead ?
                       new AttackResultAndAmount(AttackResult.KILL, damage) :
                       new AttackResultAndAmount(
                               AttackResult.BUFF_DAMAGE, damage
                       );
    }

    private void reapplyBuffs() {
        myAdjustedStats.resetStats();

        for (Buff buff : myBuffs) {
            buff.adjustStats(myAdjustedStats);
        }
    }

    private Buff getBuff(final BuffType theBuffType) {
        for (Buff buff : myBuffs) {
            if (buff.getType() == theBuffType) {
                return buff;
            }
        }

        return null;
    }

    private void clearDebuffs() {
        int buffCount = myBuffs.size();
        myBuffs.removeIf(buff -> buff.getType().isDebuff());

        if (myBuffs.size() != buffCount) {
            reapplyBuffs();
        }
    }

    private boolean applyDamage(final int theDamage) {
        myHP -= theDamage;

        return isDead();
    }

    private boolean isDead() {
        return myHP <= 0;
    }

    private boolean applyDamageFromBuff(final Buff theBuff) {
        if (theBuff.getDamagePercent() != 0.0) {
            return applyDamage(percentOfMaxHP(theBuff.getDamagePercent()));
        }

        return false; // No damage applied, so if not dead before, can't be now
    }

    private double inverseAdjustedResistance(final DamageType theDamageType) {
        return 1.0 - getAdjustedResistance(theDamageType);
    }

    private int applyAdjustedDamage(final int theBaseDamage,
                                    final DamageType theDamageType) {
        final int damage = (int) (
                theBaseDamage * inverseAdjustedResistance(theDamageType)
        );
        applyDamage(damage);

        return damage;
    }

    private double adjustedDebuffChance(final double theBaseDebuffChance,
                                        final DamageType theDamageType) {
        return theBaseDebuffChance * inverseAdjustedResistance(theDamageType);
    }

    private String getDebuffInfoAsString() {
        return Util.asPercent(getDebuffChance()) + " Chance to Debuff for " +
               getDebuffDuration() +
               " Turn" + (getDebuffDuration() != 1 ? "s" : "");
    }

    private String getBuffsAsString() {
        if (!myBuffs.isEmpty()) {
            final StringBuilder builder = new StringBuilder(" Buffs:\n");

            for (Buff buff : myBuffs) {
                builder.append("  ").append(buff).append('\n');
            }

            return builder.toString();
        }

        return " No buffs\n";
    }
}

