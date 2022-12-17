/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a template to construct and handle all the dungeon characters or objects
 * in the dungeon adventure; including the heroes, monsters, and traps.
 */
public abstract class DamageDealer implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -9039191023674738936L;

    /**
     * The class type of the dungeon character/the class type of
     * the dungeon object.
     */
    private final String myClass;

    /**
     * The minimum dungeon character damage/the minimum dungeon
     * object damage.
     */
    private final int myMinDamage;

    /**
     * The maximum dungeon character damage/the maximum dungeon
     * object damage.
     */
    private final int myMaxDamage;

    /**
     * The hit chance of the dungeon character/the hit chance of
     * the dungeon object.
     */
    private final double myHitChance;

    /**
     * The debuff chance of the dungeon character/the debuff chance of
     * the dungeon object.
     */
    private final double myDebuffChance;

    /**
     * The debuff duration of the dungeon character/the debuff duration of
     * the dungeon object.
     */
    private final int myDebuffDuration;

    /**
     * The damage type of the dungeon character/the damage type of
     * the dungeon object.
     */
    private final DamageType myDamageType;

    /**
     * The speed of the dungeon character/the speed of
     * the dungeon object.
     */
    private final int mySpeed;

    /**
     * Constructor template to construct the dungeon character/dungeon object
     * with its stats.
     *
     * @param theClass          The class type of the dungeon character/
     *                          the class type of the dungeon object.
     * @param theMinDamage      The minimum dungeon character damage/
     *                          the minimum dungeon object damage.
     * @param theMaxDamage      The maximum dungeon character damage/
     *                          the minimum dungeon object damage.
     * @param theHitChance      The hit chance of the dungeon character/
     *                          the hit chance of the dungeon object.
     * @param theDebuffChance   The debuff chance of the dungeon character/
     *                          the debuff chance of the dungeon object.
     * @param theDebuffDuration The debuff duration of the dungeon character/
     *                          the debuff duration of the dungeon object.
     * @param theDamageType     The damage type of the dungeon character/
     *                          the damage type of the dungeon object.
     * @param theSpeed          The speed of the dungeon character/
     *                          the speed of the dungeon object.
     */
    DamageDealer(final String theClass,
                 final int theMinDamage,
                 final int theMaxDamage,
                 final double theHitChance,
                 final double theDebuffChance,
                 final int theDebuffDuration,
                 final DamageType theDamageType,
                 final int theSpeed) {
        myClass = theClass;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myHitChance = theHitChance;
        myDebuffChance = theDebuffChance;
        myDebuffDuration = theDebuffDuration;
        myDamageType = theDamageType;
        mySpeed = theSpeed;
    }

    /**
     * Gets the class type name of the dungeon character/
     * the class type name of the dungeon object.
     *
     * @return The string representing the class type name.
     */
    final String getClassName() {
        return myClass;
    }

    /**
     * Gets the minimum dungeon character damage value/
     * the minimum dungeon object damage value.
     *
     * @return The integer value representing the minimum damage.
     */
    final int getMinDamage() {
        return myMinDamage;
    }

    /**
     * Gets the maximum dungeon character damage value/
     * the maximum dungeon object damage value.
     *
     * @return The integer value representing the maximum damage.
     */
    final int getMaxDamage() {
        return myMaxDamage;
    }

    /**
     * Gets the hit chance value of the character/
     * the hit chance value of the dungeon object.
     *
     * @return The double value representing the hit chance.
     */
    final double getHitChance() {
        return myHitChance;
    }


    /**
     * Gets the debuff chance value of the character/
     * the debuff chance value of the dungeon object.
     *
     * @return The double value representing the debuff chance.
     */
    final double getDebuffChance() {
        return myDebuffChance;
    }

    /**
     * Gets the debuff duration value of the character/
     * the debuff duration value of the dungeon object.
     *
     * @return The integer value representing the debuff duration.
     */
    final int getDebuffDuration() {
        return myDebuffDuration;
    }

    /**
     * Gets the damage type of the dungeon character/
     * the damage type of the dungeon object.
     *
     * @return The damage type.
     */
    final DamageType getDamageType() {
        return myDamageType;
    }

    /**
     * Gets the speed value of the character/
     * the speed value of the dungeon object.
     *
     * @return The integer value representing the speed.
     */
    final int getSpeed() {
        return mySpeed;
    }

    /**
     * Gets the adjusted minimum dungeon character damage value/
     * the adjusted minimum dungeon object damage value.
     *
     * @return The integer value representing the adjusted minimum damage.
     */
    int getAdjustedMinDamage() {
        return getMinDamage();
    }

    /**
     * Gets the adjusted maximum dungeon character damage value/
     * the maximum dungeon object damage value.
     *
     * @return The integer value representing the adjusted maximum damage.
     */
    int getAdjustedMaxDamage() {
        return getMaxDamage();
    }

    /**
     * Gets the adjusted hit chance value of the dungeon character/
     * the adjusted hit chance value of the dungeon object.
     *
     * @return The double value representing the adjusted hit chance.
     */
    double getAdjustedHitChance() {
        return getHitChance();
    }

    /**
     * Gets the adjusted speed value of the dungeon character/
     * the adjusted speed value of the dungeon object.
     *
     * @return The double value representing the adjusted speed.
     */
    int getAdjustedSpeed() {
        return getSpeed();
    }

    /**
     * Executes and applies the damage onto the dungeon character.
     *
     * @param theTarget         The dungeon character that the damage
     *                          will be applied on.
     * @param theIsBlockable    The boolean true or false to check if
     *                          the dungeon character is block-able.
     * @return                  The string result representing the damage process
     *                          after attempting to deal damage.
     */
    final AttackResultAndAmount attemptDamage(final DungeonCharacter theTarget,
                                              final boolean theIsBlockable) {
        if (Util.probabilityTest(getAdjustedHitChance())) {
            return theTarget.applyDamageAndBuff(
                    myDamageType,
                    Util.randomIntInc(getAdjustedMinDamage(), getAdjustedMaxDamage()),
                    myDebuffChance,
                    myDebuffDuration,
                    theIsBlockable
            );
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
