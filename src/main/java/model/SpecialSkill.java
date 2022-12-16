package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class SpecialSkill implements Serializable {

    @Serial
    private static final long serialVersionUID = -164477215720427212L;

    private final String myName;
    private final int myCooldown;
    public int myRemainingCooldown;

    SpecialSkill(final int theCooldown) {
        myName = Util.createNameFromClassName(this);
        myCooldown = theCooldown;
    }

    @Override
    public final String toString() {
        return myRemainingCooldown == 0 ?
               myName :
               myName + " (can use in " + myRemainingCooldown + " turns)";
    }

    final void advance() {
        if (myRemainingCooldown > 0) {
            myRemainingCooldown--;
        }
    }

    final boolean canUse() {
        return myRemainingCooldown == 0;
    }

    final AttackResultAndAmount use(final DungeonCharacter theSelf,
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
