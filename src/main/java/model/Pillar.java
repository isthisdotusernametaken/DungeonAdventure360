/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;
import java.util.Arrays;

/**
 * This class construct and handles the four OOP pillar items that the adventurer
 * must collect in order to win the game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public final class Pillar extends Item {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -1853421696253825055L;

    /**
     * The string array representing the name of the pillar.
     */
    private static final String[] NAMES =
            Arrays.stream(createPillars())
            .map(Item::createNameFromType)
            .toArray(String[]::new);

    /**
     * The character representing the character symbol of
     * an abstraction pillar.
     */
    private static final char ABSTRACTION_REPRESENTATION = 'A';

    /**
     * The character representing the character symbol of
     * an encapsulation pillar.
     */
    private static final char ENCAPSULATION_REPRESENTATION = 'E';

    /**
     * The character representing the character symbol of
     * an inheritance pillar.
     */
    private static final char INHERITANCE_REPRESENTATION = 'I';

    /**
     * The character representing the character symbol of
     * a polymorphism pillar.
     */
    private static final char POLYMORPHISM_REPRESENTATION = 'P';

    /**
     * Constructor to construct the pillar.
     *
     * @param theType               The type of the pillar.
     * @param theCharRepresentation The character representation of the pillar.
     */
    private Pillar(final ItemType theType,
                   final char theCharRepresentation) {
        super(
                theCharRepresentation,
                theType,
                false,
                1
        );
    }

    /**
     * Constructs the four OOP pillars.
     *
     * @return  The pillar array containing all four of the OOP pillars.
     */
    static Pillar[] createPillars() {
        return new Pillar[]{
                new Pillar(ItemType.ABSTRACTION, ABSTRACTION_REPRESENTATION),
                new Pillar(ItemType.ENCAPSULATION, ENCAPSULATION_REPRESENTATION),
                new Pillar(ItemType.INHERITANCE, INHERITANCE_REPRESENTATION),
                new Pillar(ItemType.POLYMORPHISM, POLYMORPHISM_REPRESENTATION)
        };
    }

    /**
     * Creates a copy of the pillar.
     *
     * @return The pillar item.
     */
    @Override
    Item copy() {
        return this;
    }

    /**
     * Gets the name of the pillar.
     *
     * @return The name of the pillar.
     */
    @Override
    String getName() {
        return NAMES[getType().ordinal()];
    }
}
