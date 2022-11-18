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

    static int randomInt(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMin, theMax + 1);
    }

    static double clampFraction(final double theFraction) {
        return Math.max(
                0.0,
                Math.min(theFraction, 1.0)
        );
    }

    static int clampPositiveInt(final int theValue) {
        return Math.max(1, theValue);
    }
}
