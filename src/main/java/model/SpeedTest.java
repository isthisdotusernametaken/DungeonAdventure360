package model;

public class SpeedTest {

    static final double START_X = 0.1; // Clamped min speed ratio
    static final double MID_X = 1;
    static final double END_X = 3; // Clamped max speed ratio
    static final double START_Y = 0.2; // Min dodge chance
    static final double MID_Y = 0.5;
    static final double END_Y = 0.95; // Max dodge chance

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

    static double evaluate(final double theSpeedRatio) {
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

    static class LinearEquation {

        final double mySlope;
        final double myOffset;

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

        static double calculateSlope(final double theFirstX,
                                     final double theFirstY,
                                     final double theSecondX,
                                     final double theSecondY) {
            return (theSecondY - theFirstY) / (theSecondX - theFirstX);
        }

        static double calculateOffset(final double theFirstX,
                                      final double theFirstY,
                                      final double theSlope) {
            return theFirstY - theSlope * theFirstX;
        }

        double evaluate(final double theX) {
            return mySlope * theX + myOffset;
        }
    }
}
