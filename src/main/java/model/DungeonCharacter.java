package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import controller.ProgramFileManager;

/**
 * This class constructs and handles the components of the dungeon characters
 * in the dungeon adventure; including the heroes and monsters.
 */
public abstract class DungeonCharacter extends DamageDealer {

    /**
     * This class constructs a 3D array
     */
    @Serial
    private static final long serialVersionUID = -6220105292148511769L;

    /**
     * The string name of the dungeon adventure character.
     */
    private String myName;

    /**
     * The maximum hit point of the dungeon character.
     */
    private final int myMaxHP;

    /**
     * The hit point of the dungeon character.
     */
    private int myHP;

    /**
     * The block chance of the dungeon character
     */
    private final double myBlockChance;

    /**
     * The resistance data of the dungeon character.
     */
    private final ResistanceData myResistances;

    /**
     * The adjusted stats of the dungeon character
     */
    private final AdjustedCharacterStats myAdjustedStats;

    /**
     * The list of buffs that the dungeon character have.
     */
    private final List<Buff> myBuffs;

    /**
     * Constructor of the dungeon character to creates and accesses
     * the data information of that character including the stats
     * and type of special skill.
     *
     * @param theName           The name of the dungeon character.
     * @param theClass          The class of the dungeon character.
     * @param theMaxHP          The maximum hit point of the
     *                          adventurer's character.
     * @param theMinDamage      The minimum dungeon character damage.
     * @param theMaxDamage      The maximum dungeon character damage.
     * @param theHitChance      The hit chance of the
     *                          dungeon character.
     * @param theDebuffChance   The debuff chance of the
     *                          dungeon character.
     * @param theDebuffDuration The debuff duration of the
     *                          dungeon character.
     * @param theDamageType     The damage type of the
     *                          dungeon character.
     * @param theSpeed          The speed of the dungeon character.
     * @param theBlockChance    The block chance of the
     *                          dungeon character.
     * @param theResistances    The list of resistances of the
     *                          dungeon character.
     */
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

    /**
     * Displays all the essential information of the dungeon character.
     *
     * @return The string descriptions of the dungeon character.
     */
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

    /**
     * Gets the name of the dungeon character
     *
     * @return The string representing the name of the dungeon character.
     */
    final String getName() {
        return myName;
    }

    /**
     * Gets the maximum hit point of the dungeon character
     *
     * @return The integer value representing the maximum hit point
     *         of the dungeon character.
     */
    final int getMaxHP() {
        return myMaxHP;
    }

    /**
     * Gets the hit point of the dungeon character
     *
     * @return The integer value representing the hit point
     *         of the dungeon character.
     */
    final int getHP() {
        return myHP;
    }

    /**
     * Gets the block chance value of the dungeon character
     *
     * @return The double value representing the block chance
     *         value of the dungeon character.
     */
    final double getBlockChance() {
        return myBlockChance;
    }

    /**
     * Gets the resistance data of the dungeon character.
     *
     * @return The resistance data of the dungeon character.
     */
    final ResistanceData getResistances() {
        return myResistances;
    }

    /**
     * Gets the adjusted minimum dungeon character damage value.
     *
     * @return The integer value representing the adjusted minimum damage.
     */
    @Override
    final int getAdjustedMinDamage() {
        return myAdjustedStats.getMinDamage();
    }

    /**
     * Gets the adjusted maximum dungeon character damage value.
     *
     * @return The integer value representing the adjusted maximum damage.
     */
    @Override
    final int getAdjustedMaxDamage() {
        return myAdjustedStats.getMaxDamage();
    }

    /**
     * Gets the adjusted hit chance value of the dungeon character
     *
     * @return The double value representing the adjusted hit chance.
     */
    @Override
    final double getAdjustedHitChance() {
        return myAdjustedStats.getHitChance();
    }

    /**
     * Gets the adjusted speed value of the dungeon character.
     *
     * @return The double value representing the adjusted speed.
     */
    @Override
    final int getAdjustedSpeed() {
        return myAdjustedStats.getSpeed();
    }

    /**
     * Gets the adjusted resistance data value of the dungeon character/
     * the adjusted speed value of the dungeon object.
     *
     * @return The double value representing the adjusted resistance data.
     */
    final double getAdjustedResistance(final DamageType theDamageType) {
        return myAdjustedStats.getResistance(theDamageType);
    }

    /**
     * Sets name for the dungeon character.
     *
     * @param theNewName The name that adventurer want to set.
     */
    final void setName(final String theNewName) {
        myName = theNewName;
    }

    /**
     * Gets the amount of percentage of the dungeon character's hit point.
     *
     * @param thePercent The percentage value.
     * @return           The integer value representing the amount of
     *                   percentage of the dungeon character's hit point.
     */
    final int percentOfMaxHP(final double thePercent) {
        return (int) (myMaxHP * thePercent);
    }

    /**
     * Attempts to heal the dungeon character.
     *
     * @param theAmount The amount of healing.
     * @return          The integer value representing the amount healed.
     */
    final int heal(final int theAmount) {
        clearDebuffs();

        final int sum = myHP + theAmount;
        if (sum > myMaxHP) {
            myHP = myMaxHP;
            return theAmount - (sum - myMaxHP);
        }
        myHP = sum;
        return theAmount;
    }

    /**
     * Executes and applies the damage and buff damage onto the
     * dungeon character.
     *
     * @param theDamageType     The type of damage will be dealt on the
     *                          dungeon character.
     * @param theDamage         The amount of damage will be dealt on the
     *                          dungeon character.
     * @param theDebuffChance   The debuff chance value of the dungeon
     *                          character.
     * @param theDebuffDuration The debuff duration value of the dungeon
     *                          character.
     * @param theIsBlockable    The boolean true or false if the dungeon
     *                          character can block.
     * @return                  The string result representing the damage
     *                          and damage from buff process after attempted
     *                          to deal damage.
     */
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
                try {
                    applyBuff(theDamageType.getDebuffType(), theDebuffDuration);
                    result = AttackResult.HIT_DEBUFF;
                } catch (IllegalArgumentException e) {
                    ProgramFileManager.getInstance().logException(e, false);
                    result = AttackResult.HIT_NO_DEBUFF;
                }
            } else {
                result = AttackResult.HIT_NO_DEBUFF;
            }

            return new AttackResultAndAmount(result, damage);
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.BLOCK);
    }

    /**
     * Executes and applies the effect of the buff onto the dungeon
     * character, the effect may vary depending on the type of buff.
     *
     * @param theBuffType               The buff type or debuff type.
     * @param theDuration               The duration of the buff or debuff.
     *
     * @throws IllegalArgumentException Thrown if the duration is negative.
     */
    final void applyBuff(final BuffType theBuffType,
                         final int theDuration) throws IllegalArgumentException {
        if (theBuffType == BuffType.NONE) {
            return;
        }

        Buff buff = getBuff(theBuffType);
        if (buff != null) {
            buff.changeDuration(theDuration);
        } else {
            buff = BuffFactory.create(theBuffType, theDuration);

            myBuffs.add(buff);
            buff.adjustStats(myAdjustedStats);
        }
    }

    /**
     * Advance the duration of the buff/debuff by 1 turns.
     * If the debuff is still not out of turn, applies damage.
     *
     * @return The attack result and amount from the buff/debuff applied
     *         on the dungeon character.
     */
    final AttackResultAndAmount advanceBuffsAndDebuffs() {
        return advanceBuffs(true);
    }

    /**
     * Advance the duration of the debuff by 1 turns,
     * If the debuff is still not out of turn, applies damage.
     *
     * @return  The attack result and amount from the debuff applied
     *          on the dungeon character.
     */
    final AttackResultAndAmount advanceDebuffs() {
        return advanceBuffs(false);
    }


    /**
     * Advance the duration of the buff by 1 turns,
     * If the debuff is still not out of turn, applies damage.
     *
     * @param theAllBuffs The boolean true or false to check if the buff
     *                    is just a debuff or all buffs.
     * @return            The attack result and amount from the buff/debuff applied
     *                    on the dungeon character.
     */
    private AttackResultAndAmount advanceBuffs(final boolean theAllBuffs) {
        final List<Buff> toRemove = new ArrayList<>();

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

    /**
     * Attempts to reapply the buffs into the buff's list.
     * Re-adjust the character stat.
     */
    private void reapplyBuffs() {
        myAdjustedStats.resetStats();

        for (Buff buff : myBuffs) {
            buff.adjustStats(myAdjustedStats);
        }
    }

    /**
     * Gets the buff type from the buff's list.
     *
     * @param theBuffType The type of buff from the buff's list.
     * @return            The buff, null if not found.
     */
    private Buff getBuff(final BuffType theBuffType) {
        for (Buff buff : myBuffs) {
            if (buff.getType() == theBuffType) {
                return buff;
            }
        }

        return null;
    }

    /**
     * Clears out or deletes debuff from the buff's list when
     * it is out of duration.
     * Reapply the buffs into the buff's list.
     */
    private void clearDebuffs() {
        final int buffCount = myBuffs.size();
        myBuffs.removeIf(buff -> buff.getType().isDebuff());

        if (myBuffs.size() != buffCount) {
            reapplyBuffs();
        }
    }

    /**
     * Attempts to deal damage onto the dungeon character.
     * The hit point of the dungeon character will be adjusted.
     *
     * @param theDamage The amount of damage to be dealt.
     * @return          The boolean true or false if the dungeon
     *                  character is still alive.
     */
    private boolean applyDamage(final int theDamage) {
        myHP -= theDamage;

        return isDead();
    }

    /**
     * Checks if the dungeon character's hit point
     * is 0 (means dead).
     *
     * @return  The boolean true or false if the dungeon character is
     *          still alive.
     */
    private boolean isDead() {
        return myHP <= 0;
    }

    /**
     * Attempts to apply damage from buff onto the dungeon character.
     *
     * @param theBuff The type of buff to get the buff damage.
     * @return        The boolean true or false if there are damages
     *                applied.
     */
    private boolean applyDamageFromBuff(final Buff theBuff) {
        if (theBuff.getDamagePercent() != 0.0) {
            return applyDamage(percentOfMaxHP(theBuff.getDamagePercent()));
        }

        return false; // No damage applied, so if not dead before, can't be now
    }

    /**
     * Inverses the adjusted resistance value to get stats multiplier
     * of the buff type associated with that damage type.
     *
     * @param theDamageType The damage type.
     * @return              The double value representing the
     *                      inverse resistance value.
     */
    private double inverseAdjustedResistance(final DamageType theDamageType) {
        return 1.0 - getAdjustedResistance(theDamageType);
    }

    /**
     * Attempts to apply the adjusted damage
     *
     * @param theBaseDamage The initial minimum dungeon character damage.
     * @param theDamageType The damage type to get its stat multiplier.
     * @return              The integer value representing the damage.
     */
    private int applyAdjustedDamage(final int theBaseDamage,
                                    final DamageType theDamageType) {
        final int damage = (int) (
                theBaseDamage * inverseAdjustedResistance(theDamageType)
        );
        applyDamage(damage);

        return damage;
    }

    /**
     * Adjusts the debuff chance for the dungeon character.
     *
     * @param theBaseDebuffChance   The initial debuff chance value of
     *                              the dungeon character.
     * @param theDamageType         The damage type to get its stat multiplier.
     * @return                      The double value representing the adjusted
     *                              debuff chance.
     */
    private double adjustedDebuffChance(final double theBaseDebuffChance,
                                        final DamageType theDamageType) {
        return theBaseDebuffChance * inverseAdjustedResistance(theDamageType);
    }

    /**
     * Access and formats the debuff's information as string.
     *
     * @return The string representing the debuff's information.
     */
    private String getDebuffInfoAsString() {
        return Util.asPercent(getDebuffChance()) + " Chance to Debuff for " +
               getDebuffDuration() +
               " Turn" + (getDebuffDuration() != 1 ? "s" : "");
    }

    /**
     * Access and formats the buff's information as string.
     *
     * @return The string representing the buff's information.
     */
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

