/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;

/**
 * This class represents the dungeon monster of the dungeon game and
 * displays the monster stats and executes the monster's unique healing
 * skill when it is used.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Monster extends DungeonCharacter {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -1143618060094549512L;

    /**
     * The double value representing the healing percentage that
     * the monster can heal.
     */
    private static final double HEAL_PERCENT = 0.1;

    /**
     * The double value representing the healing chance of the monster.
     */
    private final double myHealChance;

    /**
     * Constructor of the adventurer's character to creates and accesses
     * the data information of that character including the stats
     * and type of special skill.
     *
     * @param theName           The name of the monster.
     * @param theClass          The class of the monster.
     * @param theMaxHP          The maximum hit point of the monster.
     * @param theMinDamage      The minimum monster damage.
     * @param theMaxDamage      The maximum monster damage.
     * @param theHitChance      The hit chance of the monster.
     * @param theDebuffChance   The debuff chance of the monster.
     * @param theDebuffDuration The debuff duration of the monster.
     * @param theDamageType     The damage type of the monster.
     * @param theSpeed          The speed of the monster.
     * @param theBlockChance    The block chance of the monster.
     * @param theHealChance     The healing chance of the monster.
     * @param theResistances    The list of resistances of the monster.
     */
    Monster(final String theName,
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
            final double theHealChance,
            final ResistanceData theResistances) {
        super(
                theName,
                theClass,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theDamageType,
                theSpeed,
                theBlockChance,
                theResistances
        );

        myHealChance = theHealChance;
    }

    /**
     * Displays all the essential information of the monster.
     *
     * @return The string descriptions of the monster.
     */
    @Override
    public final String toString() {
        return super.toString() +
               " " + Util.asPercent(myHealChance) + " Heal Chance per Turn" +
               '\n';
    }

    /**
     * Gets the healing chance of the monster.
     *
     * @return The double value representing the healing chance of
     *         the monster.
     */
    double getHealChance() {
        return myHealChance;
    }

    /**
     * Executes and applies the monster healing skill.
     *
     * @return         The type of attack result and amount after
     *                 the skill is executed.
     */
    AttackResultAndAmount attemptHeal() {
        return Util.probabilityTest(myHealChance) ?
               new AttackResultAndAmount(
                       AttackResult.HEAL,
                       heal((int) (HEAL_PERCENT * getMaxHP()))
               ) :
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }
}
