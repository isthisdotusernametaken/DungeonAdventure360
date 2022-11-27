package model;

public class Trap extends DamageDealer implements CharRepresentable {

    private final char myCharRepresentation;
    private final boolean myIsSingleUse;
    private boolean myIsBroken;
    private final boolean myIsBoardable;

    Trap(final String theName,
         final boolean theIsSingleUse,
         final boolean theIsBoardable,
         final int theMinDamage,
         final int theMaxDamage,
         final double theHitChance,
         final double theDebuffChance,
         final int theDebuffDuration,
         final DamageType theDamageType,
         final int theSpeed,
         final char theCharRepresentation) {

        super(theName,
              theMinDamage,
              theMaxDamage,
              theHitChance,
              theDebuffChance,
              theDebuffDuration,
              theDamageType,
              theSpeed
        );

        myCharRepresentation = theCharRepresentation;
        myIsSingleUse = theIsSingleUse;
        myIsBroken = false;
        myIsBoardable = theIsBoardable;
    }

    public final char charRepresentation() {
        return myCharRepresentation;
    }

    final boolean isSingleUse() {
        return myIsSingleUse;
    }

    final boolean isBoardable() {
        return myIsBoardable;
    }

    final boolean isBroken() {
        return myIsBroken;
    }

    final boolean board() {
        if (myIsBoardable && !myIsBroken) {
            myIsBroken = true;
            return true;
        }
        return false;
    }

    final AttackResult activate(final DungeonCharacter theTarget) {
        if (myIsBroken) {
            return AttackResult.NO_ACTION;
        }
        if (myIsSingleUse) {
            myIsBroken = true;
        }

        return Util.probabilityTest(calculateDodgeChance(theTarget)) ?
                attemptDamage(theTarget, false) :
                AttackResult.DODGE;
    }

    private double calculateDodgeChance(final DungeonCharacter theTarget) {
        return SpeedTest.evaluate(
                ((double) theTarget.getAdjustedSpeed()) / getAdjustedSpeed()
        );
    }

    private static class SpeedTest {

        private static final double START_X = 0.1; // Clamped min speed ratio
        private static final double MID_X = 1;
        private static final double END_X = 3; // Clamped max speed ratio
        private static final double START_Y = 0.2; // Min dodge chance
        private static final double MID_Y = 0.5;
        private static final double END_Y = 0.95; // Max dodge chance

        private static final Util.LinearEquation START_MID_EQUATION =
                new Util.LinearEquation(START_X, START_Y, MID_X, MID_Y);
        private static final Util.LinearEquation MID_END_EQUATION =
                new Util.LinearEquation(MID_X, MID_Y, END_X, END_Y);

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
    }
}
