package model;

import java.util.SplittableRandom;

public class Util {

    public static final String NONE = "";
    public static final String NEW = "n";

    private static final SplittableRandom RANDOM = new SplittableRandom();

    static boolean isValidIndex(final int theIndex, final int theSize) {
        return theIndex >= 0 && theIndex < theSize;
    }

    static boolean probabilityTest(final double theProbability) {
        return randomDouble() < theProbability;
    }

    static double randomDouble() {
        return RANDOM.nextDouble(1.0);
    }

    static double clampFraction(final double theFraction) {
        return Math.max(
                0.0,
                Math.min(theFraction, 1.0)
        );
    }

    static int randomIntExc(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMin, theMax);
    }

    static int randomIntExc(final int theMax) {
        return RANDOM.nextInt(theMax);
    }

    static int randomIntInc(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMin, theMax + 1);
    }

    static int clampPositiveInt(final int theValue) {
        return Math.max(1, theValue);
    }

    static int clampIntUpExc(final int theMin,
                             final int theMax,
                             final int theValue) {
        return Math.max(
                theMin,
                Math.min(theValue, theMax - 1)
        );
    }

    static int addAndClampInt(final int theMin,
                              final int theMax,
                              final int theAddend,
                              final int theAugend) {
        return (int) Math.max(
                theMin,
                Math.min(((long) theAddend) + theAugend, theMax - 1)
        );
    }

    static String createNameFromClassName(final Object theObject) {
        return theObject.getClass().getSimpleName()
               .replaceAll("(.)([A-Z])", "$1 $2");
    }

    static String createNameFromEnumName(final Enum theEnum) {
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
