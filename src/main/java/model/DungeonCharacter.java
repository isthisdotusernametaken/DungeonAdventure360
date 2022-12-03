package model;

import java.util.ArrayList;
import java.util.List;

public abstract class DungeonCharacter extends DamageDealer {

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
        return new StringBuilder(getName()).append('\n')
                .append(" Class: ").append(getClassName()).append('\n')
                .append(" HP: ").append(getHP()).append('\n')
                .append(" Max HP: ").append(getMaxHP()).append('\n')
                .append(" Base Damage: ").append(getMinDamage())
                .append('-')
                .append(getMaxDamage()).append('\n')
                .append(" Hit Chance: ").append(getHitChance()).append('\n')
                .append(" Damage Type: ").append(getDamageType()).append('\n')
                .append(" Debuff Chance: ").append(getDebuffChance()).append('\n')
                .append(" Debuff Duration: ").append(getDebuffDuration()).append('\n')
                .append(" Speed: ").append(getSpeed()).append('\n')
                .append(" Block Chance: ").append(getBlockChance()).append('\n')
                .append(getResistances())
                .toString();
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

    final String viewBuff(final BuffType theBuffType) {
        final Buff buff = getBuff(theBuffType);

        return buff == null ? Util.NONE : buff.toString();
    }

    final void setName(final String theNewName) {
        myName = theNewName;
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
                                          final int theDebuffDuration,
                                          final boolean theIsBlockable) {
        if (!(theIsBlockable && Util.probabilityTest(myBlockChance))) {
            if (applyAdjustedDamage(theDamage, theDamageType)) {
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
        Buff buff = getBuff(theBuffType);
        if (buff != null) {
            buff.changeDuration(theDuration);
        } else {
            buff = BuffFactory.create(theBuffType, theDuration);

            myBuffs.add(buff);
            buff.adjustStats(myAdjustedStats);
        }
    }

    final void clearBuffsAndDebuffs() {
        myBuffs.clear();
        myAdjustedStats.resetStats();
    }


    final void clearDebuffs() {
        int buffCount = myBuffs.size();
        myBuffs.removeIf(buff -> buff.getType().isDebuff());

        if (myBuffs.size() != buffCount) {
            reapplyBuffs();
        }
    }

    final AttackResult advanceBuffsAndDebuffs() {
        return advanceBuffs(true);
    }

    final AttackResult advanceDebuffs() {
        return advanceBuffs(false);
    }

    private AttackResult advanceBuffs(final boolean theAllBuffs) {
        List<Buff> toRemove = new ArrayList<>();

        int myPreviousHP = myHP;
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

        return dead ?
               AttackResult.KILL : myPreviousHP != myHP ?
               AttackResult.BUFF_DAMAGE :
               AttackResult.NO_ACTION;
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

    private boolean applyDamage(final int theDamage) {
        myHP -= theDamage;

        return myHP <= 0;
    }

    private boolean applyDamageFromBuff(final Buff theBuff) {
        if (theBuff.getDamagePercent() != 0.0) {
            return applyDamage((int) (
                    theBuff.getDamagePercent() * myMaxHP
            ));
        }

        return false; // No damage applied, so if not dead before, can't be now
    }

    private double inverseAdjustedResistance(final DamageType theDamageType) {
        return 1.0 - getAdjustedResistance(theDamageType);
    }

    private boolean applyAdjustedDamage(final int theBaseDamage,
                                        final DamageType theDamageType) {
        return applyDamage((int) (
                theBaseDamage *
                inverseAdjustedResistance(theDamageType)
        ));
    }

    private double adjustedDebuffChance(final double theBaseDebuffChance,
                                        final DamageType theDamageType) {
        return theBaseDebuffChance * inverseAdjustedResistance(theDamageType);
    }
}

