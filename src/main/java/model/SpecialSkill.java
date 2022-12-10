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
               myName + " (can use in " + myRemainingCooldown + " turns)";
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
        if (myRemainingCooldown == 0 && theEnemy != null) {
            myRemainingCooldown = myCooldown;

            return apply(theSelf, theEnemy);
        }

        return AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }

    abstract AttackResultAndAmount apply(DungeonCharacter theSelf,
                                         DungeonCharacter theEnemy);
}
