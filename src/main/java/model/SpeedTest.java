/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This class handles and tests the speed of the dungeon character.
 */
public class SpeedTest {

    /**
     * The integer representing the clamped minimum speed ratio.
     */
    private static final double START_X = 0.1;

    /**
     * The integer representing the clamped median speed ratio.
     */
    private static final double MID_X = 1;

    /**
     * The integer representing the clamped maximum speed ratio.
     */
    private static final double END_X = 3;

    /**
     * The integer representing the clamped minimum dodge chance.
     */
    private static final double START_Y = 0.2;

    /**
     * The integer representing the clamped median dodge chance.
     */
    private static final double MID_Y = 0.5;

    /**
     * The integer representing the clamped maximum dodge chance.
     */
    private static final double END_Y = 0.95;

    /**
     * The linear equation using minimum value and median value.
     */
    private static final LinearEquation START_MID_EQUATION =
            new LinearEquation(START_X, START_Y, MID_X, MID_Y);

    /**
     * The linear equation using median value and maximum value.
     */
    private static final LinearEquation MID_END_EQUATION =
            new LinearEquation(MID_X, MID_Y, END_X, END_Y);

    /**
     * Evaluates the speed of the dungeon characters.
     *
     * @param theAdvantaged The supposed dungeon character that has
     *                      greater speed stat.
     * @param theOther      The other dungeon character.
     * @return              The greater speed stat.
     */
    static double evaluate(final DamageDealer theAdvantaged,
                           final DamageDealer theOther) {
        return evaluate(
                ((double) theAdvantaged.getAdjustedSpeed()) /
                        theOther.getAdjustedSpeed()
        );
    }

    /**
     * Checks and evaluates the speed of the dungeon characters.
     *
     * @param theSpeedRatio The double value representing the speed ratio.
     * @return              The greater speed stat.
     */
    private static double evaluate(final double theSpeedRatio) {
        if (theSpeedRatio <= START_X) {
            return START_Y;
        }
        if (theSpeedRatio <= MID_X) {
            return START_MID_EQUATION.evaluate(theSpeedRatio);
        }
        if (theSpeedRatio < END_X) {
            return MID_END_EQUATION.evaluate(theSpeedRatio);
        }

        return END_Y; // theSpeedRatio > END_X
    }

    /**
     * This class helps to access, evaluate and handle the speed stat.
     */
    private static class LinearEquation {

        /**
         * The double value representing the slope value.
         */
        private final double mySlope;

        /**
         * The double value representing the off-set value.
         */
        private final double myOffset;

        /**
         * Constructor to construct the linear equation.
         *
         * @param theFirstX     The first double value of x.
         * @param theFirstY     The first double value of y.
         * @param theSecondX    The second double value of x.
         * @param theSecondY    The second double value of y.
         */
        private LinearEquation(final double theFirstX,
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

        /**
         * Calculates and evaluates for the slop value.
         *
         * @param theFirstX     The first double value of x.
         * @param theFirstY     The first double value of y.
         * @param theSecondX    The second double value of x.
         * @param theSecondY    The second double value of y.
         * @return              The double value of the calculated slope.
         */
        private static double calculateSlope(final double theFirstX,
                                             final double theFirstY,
                                             final double theSecondX,
                                             final double theSecondY) {
            return (theSecondY - theFirstY) / (theSecondX - theFirstX);
        }

        /**
         * Calculates and evaluates for the off-set value.
         *
         * @param theFirstX     The first double value of x.
         * @param theFirstY     The first double value of y.
         * @param theSlope      The double value of the slope.
         * @return              The double value of the calculated off-set.
         */
        private static double calculateOffset(final double theFirstX,
                                              final double theFirstY,
                                              final double theSlope) {
            return theFirstY - theSlope * theFirstX;
        }

        /**
         * Final evaluation of the speed.
         *
         * @param theX The double value representing the speed.
         * @return     The double value representing the result.
         */
        private double evaluate(final double theX) {
            return mySlope * theX + myOffset;
        }
    }
}
