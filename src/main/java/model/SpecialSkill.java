package model;

import java.io.Serializable;

public abstract class SpecialSkill implements Serializable {

    private final String myName;
    private final int myCooldown;
    private int myRemainingCooldown;

    SpecialSkill(final int theCooldown) {
        myName = createNameFromClassName();
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

    AttackResult use(final DungeonCharacter theSelf,
                     final DungeonCharacter theEnemy) {
        if (myRemainingCooldown == 0) {
            myRemainingCooldown = myCooldown;

            return apply(theSelf, theEnemy);
        }

        return AttackResult.NO_ACTION;
    }

    abstract AttackResult apply(DungeonCharacter theSelf,
                                DungeonCharacter theEnemy);

    private String createNameFromClassName() {
        return getClass().getSimpleName()
               .replaceAll("(.)([A-Z])", "\\1 \\2");
    }
}
