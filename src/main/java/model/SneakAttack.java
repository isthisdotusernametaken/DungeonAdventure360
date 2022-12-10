package model;

public class SneakAttack extends SpecialSkill {

    private static final int COOLDOWN = 5;
    private static final double SUCCESS_CHANCE = 0.4;
    private static final double NORMAL_ATTACK_CHANCE = 0.4;

    private static final double NORMAL_ATTACK_CUTOFF =
            SUCCESS_CHANCE + NORMAL_ATTACK_CHANCE;

    SneakAttack() {
        super(COOLDOWN);
    }

    @Override
    AttackResultAndAmount apply(final DungeonCharacter theSelf,
                                final DungeonCharacter theEnemy) {
        final double test = Util.randomDouble();
        if (test <= NORMAL_ATTACK_CUTOFF) {
            final AttackResultAndAmount result =
                    theSelf.attemptDamage(theEnemy, true);

            return test <= SUCCESS_CHANCE ? (
                        result.getResult() == AttackResult.HIT_NO_DEBUFF ?
                            new AttackResultAndAmount(
                                    AttackResult.EXTRA_TURN_NO_DEBUFF,
                                    result.getAmount()
                            ) :
                        result.getResult() == AttackResult.HIT_DEBUFF ?
                            new AttackResultAndAmount(
                                    AttackResult.EXTRA_TURN_DEBUFF,
                                    result.getAmount()
                            ) :
                        result
                   ) :
                   result;
        }
        return AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
