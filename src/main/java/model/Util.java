package model;

import java.util.SplittableRandom;

public class Util {

    private static final SplittableRandom RANDOM = new SplittableRandom();

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

    static class LinearEquation {

        private final double mySlope;
        private final double myOffset;

        LinearEquation(final double theFirstX,
                       final double theFirstY,
                       final double theSecondX,
                       final double theSecondY) {
            mySlope = calculateSlope(
                    theFirstX, theFirstY,
                    theSecondX, theSecondY
            );
            myOffset = calculateOffset(
                    theFirstX, theFirstY,
                    mySlope
            );
        }

        double evaluate(final double theX) {
            return mySlope * theX + myOffset;
        }

        private static double calculateSlope(final double theFirstX,
                                             final double theFirstY,
                                             final double theSecondX,
                                             final double theSecondY) {
            return (theSecondY - theFirstY) / (theSecondX - theFirstX);
        }

        private static double calculateOffset(final double theFirstX,
                                              final double theFirstY,
                                              final double theSlope) {
            return theFirstY - theSlope * theFirstX;
        }
    }
}
