package model;

public abstract class DamageDealer {

    private final int myMinDamage;
    private final int myMaxDamage;
    private final double myHitChance;
    private final double myDebuffChance;
    private final DamageType myDamageType;
    private final int mySpeed;

    DamageDealer(final int theMinDamage,
                 final int theMaxDamage,
                 final double theHitChance,
                 final double theDebuffChance,
                 final DamageType theDamageType,
                 final int theSpeed) {
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myHitChance = theHitChance;
        myDebuffChance = theDebuffChance;
        myDamageType = theDamageType;
        mySpeed = theSpeed;
    }

    final int getMinDamage() {
        return myMinDamage;
    }

    final int getMaxDamage() {
        return myMaxDamage;
    }

    final double getHitChance() {
        return myHitChance;
    }

    double getDebuffChance() {
        return myDebuffChance;
    }

    final DamageType getDamageType() {
        return myDamageType;
    }

    final int getSpeed() {
        return mySpeed;
    }

    final AttackResult attemptDamage(final DungeonCharacter theCharacter,
                                     final boolean theIsBlockable) {
        return null;
    }
}
