/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.util.Arrays;

/**
 * This class constructs the buff type for buff and debuff along
 * with its representation.
 */
public enum BuffType implements CharRepresentable {

    /**
     * No buff, representation should not be used.
     */
    NONE(false, ' '),
    /**
     * The strength buff.
     */
    STRENGTH(false, 'S'),
    /**
     * The speed buff.
     */
    SPEED(false, '>'),
    /**
     * The accuracy buff.
     */
    ACCURACY(false, '^'),
    /**
     * The resistance buff.
     */
    RESISTANCE(false, 'R'),
    /**
     * The broken-bone debuff.
     */
    BROKEN_BONE(true, '{'),
    /**
     * The burning debuff.
     */
    BURNING(true, 'F'),
    /**
     * The bleeding debuff.
     */
    BLEEDING(true, 'B'),
    /**
     * The poisoned debuff.
     */
    POISONED(true, 'C');

    /**
     * Array of buff type that contains only the positive buffs.
     */
    private static final BuffType[] POSITIVE_TYPES =
            Arrays.stream(values()).filter(
                    type -> type != NONE && !type.isDebuff()
            ).toArray(BuffType[]::new);

    /**
     * Boolean to check if the buff is a debuff.
     */
    private final boolean myIsDebuff;

    /**
     * The character representing the character symbol of the buff/debuff.
     */
    private final char myRepresentation;

    /**
     * The string representing the name of the buff/debuff.
     */
    private String myName;

    /**
     * Constructor to construct the buff type and its representation symbol.
     *
     * @param theIsDebuff The boolean true or false if the buff is a debuff.
     * @param theRepresentation The character symbol of the buff/debuff.
     */
    BuffType(final boolean theIsDebuff,
             final char theRepresentation) {
        myIsDebuff = theIsDebuff;
        myRepresentation = theRepresentation;
    }

    /**
     * Gets and obtains all the positive buffs.
     *
     * @return The buff type array containing all positive buffs.
     */
    static BuffType[] getAllPositiveBuffTypes() {
        return POSITIVE_TYPES.clone();
    }

    /**
     * ToString method to get the buff type's name.
     *
     * @return The buff type's name.
     */
    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }

    /**
     * Gets the character representation of the buff/debuff.
     *
     * @return The character representation of the buff/debuff.
     */
    @Override
    public char charRepresentation() {
        return myRepresentation;
    }

    /**
     * Assigns boolean value depending on the bufftype,
     * if it is a debuff or not.
     *
     * @return The boolean true or false if the buff is a debuff.
     */
    boolean isDebuff() {
        return myIsDebuff;
    }
}


