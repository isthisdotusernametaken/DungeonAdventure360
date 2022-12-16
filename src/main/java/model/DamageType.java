package model;

/**
 * This class constructs the damage type of the dungeon character
 * or the damage type of the dungeon object.
 */
public enum DamageType {

    /**
     * Normal damage type with debuff effect.
     */
    NORMAL(BuffType.NONE),
    /**
     * Sharp damage type with bleeding debuff effect.
     */
    SHARP(BuffType.BLEEDING),
    /**
     * Blunt damage type with broken-bone debuff effect.
     */
    BLUNT(BuffType.BROKEN_BONE),
    /**
     * Fire damage type with burning debuff effect.
     */
    FIRE(BuffType.BURNING),
    /**
     * Poison damage type with poisoned debuff effect.
     */
    POISON(BuffType.POISONED);

    /**
     * The type of the debuff.
     */
    private final BuffType myDebuffType;

    /**
     * The name of the debuff.
     */
    private String myName;

    /**
     * Constructor to construct the damage type with its debuff effect.
     *
     * @param theDebuffType The type of the debuff.
     */
    DamageType(final BuffType theDebuffType) {
       myDebuffType = theDebuffType;
    }

    /**
     * ToString method to format and display the damage type with
     * its debuff effect information.
     *
     * @return The string representing the damage type with its
     *          debuff effect information.
     */
    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }

    /**
     * Gets the type of the debuff.
     *
     * @return The type of the debuff.
     */
    BuffType getDebuffType() {
        return myDebuffType;
    }
}

