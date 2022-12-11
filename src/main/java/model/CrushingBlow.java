package model;

import java.io.Serial;

public class CrushingBlow extends SpecialSkill {

    @Serial
    private static final long serialVersionUID = 1960099350768448086L;

    private static final int COOLDOWN = 5;
    private static final int MIN_DAMAGE = 80;
    private static final int MAX_DAMAGE = 150;
    private static final double HIT_CHANCE = 0.3;
    private static final double DEBUFF_CHANCE = 1.0;
    private static final int DEBUFF_DURATION = 4;

    CrushingBlow() {
        super(COOLDOWN);
    }

    @Override
    AttackResultAndAmount apply(final DungeonCharacter theSelf,
                                final DungeonCharacter theEnemy) {
        return Util.probabilityTest(HIT_CHANCE) ?
               theEnemy.applyDamageAndBuff(
                       theSelf.getDamageType(),
                       Util.randomIntInc(MIN_DAMAGE, MAX_DAMAGE),
                       DEBUFF_CHANCE,
                       DEBUFF_DURATION,
                       false
               ) :
               AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
