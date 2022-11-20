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

    final AttackResult activate(final DungeonCharacter theTarget) {
        return attemptDamage(theTarget, false);
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
