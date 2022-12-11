package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class DamageDealer implements Serializable {

    @Serial
    private static final long serialVersionUID = -9039191023674738936L;

    private final String myClass;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final double myHitChance;
    private final double myDebuffChance;
    private final int myDebuffDuration;
    private final DamageType myDamageType;
    private final int mySpeed;

    DamageDealer(final String theClass,
                 final int theMinDamage,
                 final int theMaxDamage,
                 final double theHitChance,
                 final double theDebuffChance,
                 final int theDebuffDuration,
                 final DamageType theDamageType,
                 final int theSpeed) {
        myClass = theClass;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myHitChance = theHitChance;
        myDebuffChance = theDebuffChance;
        myDebuffDuration = theDebuffDuration;
        myDamageType = theDamageType;
        mySpeed = theSpeed;
    }

    final String getClassName() {
        return myClass;
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

    final double getDebuffChance() {
        return myDebuffChance;
    }

    final int getDebuffDuration() {
        return myDebuffDuration;
    }

    final DamageType getDamageType() {
        return myDamageType;
    }

    final int getSpeed() {
        return mySpeed;
    }

    int getAdjustedMinDamage() {
        return getMinDamage();
    }

    int getAdjustedMaxDamage() {
        return getMaxDamage();
    }

    double getAdjustedHitChance() {
        return getHitChance();
    }

    int getAdjustedSpeed() {
        return getSpeed();
    }

    final AttackResultAndAmount attemptDamage(final DungeonCharacter theTarget,
                                              final boolean theIsBlockable) {
        if (Util.probabilityTest(getAdjustedHitChance())) {
            return theTarget.applyDamageAndBuff(
                    myDamageType,
                    Util.randomIntInc(getAdjustedMinDamage(), getAdjustedMaxDamage()),
                    myDebuffChance,
                    myDebuffDuration,
                    theIsBlockable
            );
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
