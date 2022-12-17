package model;

/**
 * This class represents the attack result from the actions performed or
 * occurred both during combat mode and exploration mode.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public enum AttackResult {

    /**
     * No action occurred.
     */
    NO_ACTION,
    /**
     * Takes damage from buff.
     */
    BUFF_DAMAGE,
    /**
     * Gets heal up.
     */
    HEAL,
    /**
     * Gets kill by applied damage
     */
    KILL,
    /**
     * Deals hit with no debuff effect.
     */
    HIT_NO_DEBUFF,
    /**
     * Deals hit with debuff effect.
     */
    HIT_DEBUFF,
    /**
     * Gains extra turn with no debuff effect.
     */
    EXTRA_TURN_NO_DEBUFF,
    /**
     * Gains extra turn with debuff effect.
     */
    EXTRA_TURN_DEBUFF,
    /**
     * Attempts to flee successfully.
     */
    FLED_SUCCESSFULLY,
    /**
     * Attempts to flee unsuccessfully.
     */
    COULD_NOT_FLEE,
    /**
     * Deals damage unsuccessfully.
     */
    MISS,
    /**
     * Blocks damage successfully.
     */
    BLOCK,
    /**
     * Dodge from damage successfully.
     */
    DODGE;

    /**
     * The name of attack result.
     */
    private String myName;

    /**
     * Gets and formats the attack result as string.
     *
     * @return The string representing the attack result.
     */
    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }
}
