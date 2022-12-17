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
 * This class is a template to construct and handle all the special skills
 * of the heroes in the dungeon adventure game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class SpecialSkill implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -164477215720427212L;

    /**
     * The string representing the name of the special skill.
     */
    private final String myName;

    /**
     * The integer value representing the cool down duration of
     * the special skill.
     */
    private final int myCooldown;

    /**
     * The integer value representing the remaining cool down duration of
     * the special skill.
     */
    private int myRemainingCooldown;

    /**
     * Constructor to construct the special skill.
     *
     * @param theCooldown The cool down duration of the special skill.
     */
    SpecialSkill(final int theCooldown) {
        myName = Util.createNameFromClassName(this);
        myCooldown = theCooldown;
    }

    /**
     * ToString method to constructs and formats the string
     * describing all essential information about the special
     * skill, including its name and its durations.
     *
     * @return The string describing all essential information
     *         about the special skill.
     */
    @Override
    public final String toString() {
        return myRemainingCooldown == 0 ?
               myName :
               myName + " (can use in " + myRemainingCooldown + " turns)";
    }

    /**
     * Advances the cool down duration of the special skill by 1.
     */
    final void advance() {
        if (myRemainingCooldown > 0) {
            myRemainingCooldown--;
        }
    }

    /**
     * Checks and validates if the special skill's cool down duration is over.
     *
     * @return  The boolean true or false if the special skill's cool down
     *          duration is over.
     */
    final boolean canUse() {
        return myRemainingCooldown == 0;
    }

    /**
     * Executes and applies the effect of the special skill onto the
     * monster.
     *
     * @param theSelf   The dungeon character or the adventurer's character
     *                  to get and apply its unique special skill's effect onto
     *                  the monster.
     * @param theEnemy  The dungeon character or the monster that the special
     *                  skill will be applied on.
     * @return          The string result representing the damage and effect
     *                  process after the skill has been applied or executed.
     */
    final AttackResultAndAmount use(final DungeonCharacter theSelf,
                                    final DungeonCharacter theEnemy) {
        if (myRemainingCooldown == 0 && theEnemy != null) {
            myRemainingCooldown = myCooldown;

            return apply(theSelf, theEnemy);
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }

    abstract AttackResultAndAmount apply(DungeonCharacter theSelf,
                                         DungeonCharacter theEnemy);
}
