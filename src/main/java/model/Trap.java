package model;

public class Trap extends DamageDealer {

    private final boolean myIsSingleUse;
    private  boolean myIsBroken;
    private  boolean myIsBoardable;
    private final char myCharRepresentation;

    Trap(final boolean theIsSingleUse,
         final boolean theIsBoardable,
         final int theMinDamage,
         final int theMaxDamage,
         final double theHitChance,
         final double theDebuffChance,
         final DamageType theDamageType,
         final int theSpeed,
         final char theCharRepresentation) {

        super(theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDamageType,
                theSpeed);
        
        myIsSingleUse = theIsSingleUse;
        myIsBoardable = theIsBoardable;
        myCharRepresentation = theCharRepresentation;
    }

    public final char charRepresentation() {
        return myCharRepresentation;
    }

    final AttackResult attemptDamage(final DungeonCharacter theCharacter) {
        return null;
    }

    final boolean isSingleUse() {
        return false;
    }

    final boolean isBroken() {
        return false;
    }

    final AttackResult activate() {
        return null;
    }

    final boolean board() {
        return false;
    }

    private final boolean speedTest() {
        return false;
    }
}
