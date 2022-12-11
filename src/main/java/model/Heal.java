package model;

import java.io.Serial;

public class Heal extends SpecialSkill {

    @Serial
    private static final long serialVersionUID = -4124759288806986206L;

    private static final int COOLDOWN = 4;
    private static final double HEAL_PERCENT = 0.3;

    Heal() {
        super(COOLDOWN);
    }

    @Override
    AttackResultAndAmount apply(final DungeonCharacter theSelf,
                              final DungeonCharacter theEnemy) {
        return new AttackResultAndAmount(
                AttackResult.HEAL,
                theSelf.heal(theSelf.percentOfMaxHP(HEAL_PERCENT))
        );
    }
}
