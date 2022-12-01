package model;

public class SpeedTest {

    private static final double START_X = 0.1; // Clamped min speed ratio
    private static final double MID_X = 1;
    private static final double END_X = 3; // Clamped max speed ratio
    private static final double START_Y = 0.2; // Min dodge chance
    private static final double MID_Y = 0.5;
    private static final double END_Y = 0.95; // Max dodge chance

    private static final LinearEquation START_MID_EQUATION =
            new LinearEquation(START_X, START_Y, MID_X, MID_Y);
    private static final LinearEquation MID_END_EQUATION =
            new LinearEquation(MID_X, MID_Y, END_X, END_Y);

    static double evaluate(final DamageDealer theAdvantaged,
                           final DamageDealer theOther) {
        return evaluate(
                ((double) theAdvantaged.getAdjustedSpeed()) /
                        theOther.getAdjustedSpeed()
        );
    }

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

    private static class LinearEquation {

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
