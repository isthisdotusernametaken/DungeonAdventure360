/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.util.SplittableRandom;

/**
 * This class contains helper methods for the dungeon adventure games
 * and allow classes in the model to use.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Util {

    /**
     * Empty string.
     */
    public static final String NONE = "";

    /**
     * The allowed maximum integer value.
     */
    static final int MAX_INT = 1000;

    /**
     * Sets random split table.
     */
    private static final SplittableRandom RANDOM = new SplittableRandom();

    /**
     * Checks and validates if index is valid.
     *
     * @param theIndex  The integer value representing the index.
     * @param theSize   The integer value representing the size.
     * @return          The boolean true or false if index is valid.
     */
    static boolean isValidIndex(final int theIndex, final int theSize) {
        return theIndex >= 0 && theIndex < theSize;
    }

    /**
     * Checks and validates if probability value is
     * greater than random double value (with 1.0 bound).
     *
     * @param theProbability The double value representing the probability.
     * @return               The boolean true or false if probability value
     *                       is greater than random double value
     *                       (with 1.0 bound).
     */
    static boolean probabilityTest(final double theProbability) {
        return randomDouble() < theProbability;
    }

    /**
     * Generates random double value with bound of 1.0.
     *
     * @return The random double value.
     */
    static double randomDouble() {
        return RANDOM.nextDouble(1.0);
    }

    /**
     * Calculates and gets the fraction value using clamp fraction math
     * algorithm method.
     *
     * @param theFraction The double value representing the fraction.
     * @return            The double value representing the result.
     */
    static double clampFraction(final double theFraction) {
        return Math.max(
                0.0,
                Math.min(theFraction, 1.0)
        );
    }

    /**
     * Generates random integer value exclusive with bound value.
     *
     * @param theMax The integer value representing the bound value or
     *               max value.
     * @return       The random integer value.
     */
    static int randomIntExc(final int theMax) {
        return RANDOM.nextInt(theMax);
    }

    /**
     * Generates random integer value inclusive with min and max value.
     *
     * @param theMin The integer value representing the min value.
     * @param theMax The integer value representing the max value.
     * @return       The random integer value.
     */
    static int randomIntInc(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMin, theMax + 1);
    }

    /**
     * Calculates and gets the integer value using clamp math algorithm
     * method.
     *
     * @param theValue    The integer value representing the value
     *                    to be calculated.
     * @return            The integer value representing the result.
     */
    static int clampPositiveInt(final int theValue) {
        return clampPositiveInt(theValue, MAX_INT);
    }

    /**
     * Calculates and gets the integer value using clamp math algorithm
     * method with min and max value
     *
     * @param theValue    The integer value representing the min value
     * @param theMax      The integer value representing the max value.
     * @return            The integer value representing the result.
     */
    static int clampPositiveInt(final int theValue, final int theMax) {
        return Math.max(
                1,
                Math.min(theValue, theMax)
        );
    }

    /**
     * Calculates and gets the integer value using clamp math algorithm
     * method with bound from zero to max value
     *
     * @param theValue    The integer value representing the min value
     * @param theMax      The integer value representing the max value.
     * @return            The integer value representing the result.
     */
    static int clampIntZeroToMaxExc(final int theMax,
                                    final int theValue) {
        return Math.max(
                0,
                Math.min(theValue, theMax - 1)
        );
    }

    /**
     * Calculates and gets the integer value using addition and clamp
     * math algorithm method.
     *
     * @param theMin      The integer value representing the min value
     * @param theMax      The integer value representing the max value.
     * @param theAugend   The integer value representing the aug end value.
     * @param theAddend   The integer value representing the add end value.
     * @return            The integer value representing the result.
     */
    static int addAndClampInt(final int theMin,
                              final int theMax,
                              final int theAddend,
                              final int theAugend) {
        return (int) Math.max(
                theMin,
                Math.min(((long) theAddend) + theAugend, theMax)
        );
    }

    /**
     * Calculates and gets the percent value.
     *
     * @param theFraction   The double value representing the fraction.
     * @return              The string representing the result.
     */
    static String asPercent(final double theFraction) {
        return Math.round(theFraction * 100.0) + "%";
    }

    /**
     * Accesses and creates name from class name.
     *
     * @param theObject The object to access the class
     * @return          The string representing the name.
     */
    static String createNameFromClassName(final Object theObject) {
        return theObject.getClass().getSimpleName()
               .replaceAll("(.)([A-Z])", "$1 $2");
    }

    /**
     * Accesses and creates name from enum.
     *
     * @param theEnum   The enum to access its name.
     * @return          The string representing the enum name.
     */
    static String createNameFromEnumName(@SuppressWarnings("rawtypes") final Enum theEnum) {
        final StringBuilder builder = new StringBuilder();
        for (String word : theEnum.name().split("_")) {
            builder.append(word.charAt(0))
                   .append(word.substring(1).toLowerCase())
                   .append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
