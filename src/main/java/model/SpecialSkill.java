package model;

import java.io.Serializable;

public abstract class SpecialSkill implements Serializable {

    private final String myName;
    private final int myCooldown;
    private int myRemainingCooldown;

    SpecialSkill(final int theCooldown) {
        myName = Util.createNameFromClassName(this);
        myCooldown = theCooldown;
    }

    @Override
    public String toString() {
        return myRemainingCooldown == 0 ?
               myName :
               new StringBuilder(myName).append(" (can use in ")
                                        .append(myRemainingCooldown)
                                        .append(" turns)")
                                        .toString();
    }

    void advance() {
        if (myRemainingCooldown > 0) {
            myRemainingCooldown--;
        }
    }

    boolean canUse() {
        return myRemainingCooldown == 0;
    }

    AttackResultAndAmount use(final DungeonCharacter theSelf,
                     final DungeonCharacter theEnemy) {
        if (myRemainingCooldown == 0) {
            myRemainingCooldown = myCooldown;

            return theEnemy == null ?
                   AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
                   apply(theSelf, theEnemy);
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }

    abstract AttackResultAndAmount apply(DungeonCharacter theSelf,
                                         DungeonCharacter theEnemy);
}
