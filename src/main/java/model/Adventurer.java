package model;

import java.io.Serial;

/**
 * This class represents the adventurer's character of the dungeon game and
 * displays the character stats and executes the character's unique skill
 * when it is used.
 */
public class Adventurer extends DungeonCharacter {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 6769784030706244929L;

    /**
     * The unique special skill of the adventurer's character.
     */
    private final SpecialSkill mySpecialSkill;

    /**
     * Constructor of the adventurer's character to creates and accesses
     * the data information of that character including the stats
     * and type of special skill.
     *
     * @param theName           The name of the adventurer.
     * @param theClass          The class of the adventurer's character.
     * @param theMaxHP          The maximum hit point of the
     *                          adventurer's character.
     * @param theMinDamage      The minimum adventurer's character damage.
     * @param theMaxDamage      The maximum adventurer's character damage.
     * @param theHitChance      The hit chance of the
     *                          adventurer's character.
     * @param theDebuffChance   The debuff chance of the
     *                          adventurer's character.
     * @param theDebuffDuration The debuff duration of the
     *                          adventurer's character.
     * @param theDamageType     The damage type of the
     *                          adventurer's character.
     * @param theSpeed          The speed of the adventurer's character.
     * @param theBlockChance    The block chance of the
     *                          adventurer's character.
     * @param theResistances    The list of resistances of the
     *                          adventurer's character.
     * @param theSpecialSkill   The special skill of the
     *                          adventurer's character.
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
     * Displays all the essential information of the dungeon's character.
     *
     * @return The string descriptions of the dungeon's character.
     */
    @Override
    public final String toString() {
        return super.toString() +
               " Special Skill: " + mySpecialSkill +
               '\n';
    }

    /**
     * Gets the unique special of the dungeon's character.
     *
     * @return The special skill type of the dungeon's character.
     */
    final SpecialSkill getSpecialSkill() {
        return mySpecialSkill;
    }

    /**
     * Displays the special skill of the dungeon's character.
     *
     * @return The string description of the special skill of
     *         the dungeon's character.
     */
    final String viewSpecialSkill() {
        return mySpecialSkill.toString();
    }

    /**
     * Executes and applies the dungeon's character's special skill
     * on the enemy.
     *
     * @param theEnemy The monster in the dungeon game.
     * @return         The type of attack result and amount after
     *                 the special skill is executed.
     */
    final AttackResultAndAmount useSpecialSkill(final DungeonCharacter theEnemy) {
        return mySpecialSkill.use(this, theEnemy);
    }
}
