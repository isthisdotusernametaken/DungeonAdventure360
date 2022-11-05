package model;

public class Trap extends DamageDealer implements CharRepresentable {

    private final char myCharRepresentation;
    private final boolean myIsSingleUse;
    private boolean myIsBroken;
    private final boolean myIsBoardable;
    private final int myDebuffDuration;

    Trap(final boolean theIsSingleUse,
         final boolean theIsBoardable,
         final int theMinDamage,
         final int theMaxDamage,
         final double theHitChance,
         final double theDebuffChance,
         final DamageType theDamageType,
         final int theSpeed,
         final int theDebuffDuration,
         final char theCharRepresentation) {

        super(theMinDamage,
              theMaxDamage,
              theHitChance,
              theDebuffChance,
              theDamageType,
              theSpeed
        );

        myCharRepresentation = theCharRepresentation;
        myIsSingleUse = theIsSingleUse;
        myIsBroken = false;
        myIsBoardable = theIsBoardable;
        myDebuffDuration = theDebuffDuration;
    }

    public final char charRepresentation() {
        return myCharRepresentation;
    }

    final boolean isSingleUse() {
        return myIsSingleUse;
    }

    final boolean isBroken() {
        return myIsBroken;
    }

    final AttackResult activate(final DungeonCharacter theTarget) {
        return attemptDamage(theTarget, false, myDebuffDuration);
    }

    final boolean board() {
        if (myIsBoardable && !myIsBroken) {
            myIsBroken = true;
            return true;
        }
        return false;
    }

    private boolean speedTest() { // incomplete
        return false;
    }
}
