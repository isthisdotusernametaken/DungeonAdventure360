package model;

public class Trap extends DamageDealer implements CharRepresentable {

    private final char myCharRepresentation;
    private final boolean myIsSingleUse;
    private boolean myIsBroken;
    private final boolean myIsBoardable;

    Trap(final String theClass,
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

        super(
                theClass,
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

        return Util.probabilityTest(SpeedTest.evaluate(theTarget, this)) ?
               attemptDamage(theTarget, false) :
               AttackResult.DODGE;
    }
}
