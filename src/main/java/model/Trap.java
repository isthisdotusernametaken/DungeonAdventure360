package model;

import java.io.Serial;

/**
 * This class represents the trap of the dungeon game and
 * displays the trap stats.
 */
public class Trap extends DamageDealer implements CharRepresentable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 5633394522695385651L;

    /**
     * The character representing the character symbol of the trap.
     */
    private final char myCharRepresentation;

    /**
     * The boolean true or false if the trap is single use.
     */
    private final boolean myIsSingleUse;

    /**
     * The boolean true or false if the trap is broken.
     */
    private boolean myIsBroken;

    /**
     * The boolean true or false if the trap is boardable.
     */
    private final boolean myIsBoardable;

    /**
     * Constructor of the adventurer's character to creates and accesses
     * the data information of that character including the stats
     * and type of special skill.
     *
     * @param theClass              The class of the trap.
     * @param theIsSingleUse        The boolean true or false if the trap
     *                              is single use.
     * @param theIsBoardable        The boolean true or false if the trap
     *                              is boardable.
     * @param theMinDamage          The minimum trap damage.
     * @param theMaxDamage          The maximum trap damage.
     * @param theHitChance          The hit chance of the trap.
     * @param theDebuffChance       The debuff chance of the trap.
     * @param theDebuffDuration     The debuff duration of the trap.
     * @param theDamageType         The damage type of the trap.
     * @param theSpeed              The speed of the trap.
     * @param theCharRepresentation The character representing the character
     *                              symbol of the trap.
     */
    Trap(final String theClass,
         final boolean theIsSingleUse,
         final boolean theIsBoardable,
         final int theMinDamage,
         final int theMaxDamage,
         final double theHitChance,
         final double theDebuffChance,
         final int theDebuffDuration,
         final DamageType theDamageType,
         final int theSpeed,
         final char theCharRepresentation) {

        super(
                theClass,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theDamageType,
                theSpeed
        );

        myCharRepresentation = theCharRepresentation;
        myIsSingleUse = theIsSingleUse;
        myIsBroken = false;
        myIsBoardable = theIsBoardable;
    }

    /**
     * Gets the character representation of the trap.
     *
     * @return  The character representation of the trap.
     */
    public final char charRepresentation() {
        return myCharRepresentation;
    }

    /**
     * Checks if the trap is single use only.
     *
     * @return  The boolean true or false if the trap is single use only.
     */
    final boolean isSingleUse() {
        return myIsSingleUse;
    }

    /**
     * Checks if the trap is boardable.
     *
     * @return  The boolean true or false if the trap is boardable.
     */
    final boolean isBoardable() {
        return myIsBoardable;
    }

    /**
     * Checks if the trap is broken.
     *
     * @return  The boolean true or false if the trap is broken.
     */
    final boolean isBroken() {
        return myIsBroken;
    }

    /**
     * Attempts to board the trap.
     *
     * @return  The boolean true or false if the trap is boarded successfully.
     */
    final boolean board() {
        if (myIsBoardable && !myIsBroken) {
            myIsBroken = true;
            return true;
        }
        return false;
    }

    /**
     * Attempts to activate the trap and applies the trap's damage onto
     * the dungeon character.
     *
     * @param theTarget The dungeon character that the trap's damage will be
     *                  dealt on.
     *
     * @return         The type of attack result and amount after
     *                 the skill is executed.
     */
    final AttackResultAndAmount activate(final DungeonCharacter theTarget) {
        if (myIsBroken) {
            return AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
        }
        if (myIsSingleUse) {
            myIsBroken = true;
        }

        return Util.probabilityTest(SpeedTest.evaluate(theTarget, this)) ?
               attemptDamage(theTarget, false) :
               AttackResultAndAmount.getNoAmount(AttackResult.DODGE);
    }
}
