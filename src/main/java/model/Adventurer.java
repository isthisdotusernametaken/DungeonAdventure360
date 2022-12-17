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
 * This class represents the Adventurer of the dungeon game and
 * displays the character stats and executes the character's unique skill
 * when it is used.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Adventurer extends DungeonCharacter {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 6769784030706244929L;

    /**
     * The unique special skill of the Adventurer.
     */
    private final SpecialSkill mySpecialSkill;

    /**
     * Constructor of the Adventurer to creates and accesses
     * the data information of that character including the stats
     * and type of special skill.
     *
     * @param theName           The name of the Adventurer.
     * @param theClass          The class of the Adventurer.
     * @param theMaxHP          The maximum hit point of the
     *                          Adventurer.
     * @param theMinDamage      The adventurer's minimum damage.
     * @param theMaxDamage      The adventurer's maximum damage.
     * @param theHitChance      The hit chance of the Adventurer.
     * @param theDebuffChance   The debuff chance of the Adventurer.
     * @param theDebuffDuration The debuff duration of the Adventurer.
     * @param theDamageType     The damage type of the Adventurer.
     * @param theSpeed          The speed of the Adventurer.
     * @param theBlockChance    The block chance of the Adventurer.
     * @param theResistances    The list of resistances of the Adventurer.
     * @param theSpecialSkill   The special skill of the Adventurer.
     */
    Adventurer(final String theName,
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
               final ResistanceData theResistances,
               final SpecialSkill theSpecialSkill) {
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

          mySpecialSkill = theSpecialSkill;
    }

    /**
     * Displays all the essential information of the Adventurer.
     *
     * @return The string description of the Adventurer.
     */
    @Override
    public final String toString() {
        return super.toString() +
               " Special Skill: " + mySpecialSkill +
               '\n';
    }

    /**
     * Gets the unique special of the Adventurer.
     *
     * @return The special skill of the Adventurer.
     */
    final SpecialSkill getSpecialSkill() {
        return mySpecialSkill;
    }

    /**
     * Gets the special skill of the Adventurer as a String.
     *
     * @return The string description of the special skill of
     *         the dungeon character.
     */
    final String viewSpecialSkill() {
        return mySpecialSkill.toString();
    }

    /**
     * Executes and applies the Adventurer's special skill
     * on the enemy or the Adventurer.
     *
     * @param theEnemy The monster in the dungeon game.
     * @return         The type of attack result and amount after
     *                 the special skill is executed.
     */
    final AttackResultAndAmount useSpecialSkill(final DungeonCharacter theEnemy) {
        return mySpecialSkill.use(this, theEnemy);
    }
}
