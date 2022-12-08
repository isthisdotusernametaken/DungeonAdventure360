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
        return test <= SUCCESS_CHANCE ?
               new AttackResultAndAmount(
                       AttackResult.EXTRA_TURN,
                       theSelf.attemptDamage(theEnemy, true).getAmount()
               ) : test <= NORMAL_ATTACK_CUTOFF ?
               theSelf.attemptDamage(theEnemy, true) :
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }
}
