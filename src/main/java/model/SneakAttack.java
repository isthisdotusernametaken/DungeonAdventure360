package model;

public class SneakAttack extends SpecialSkill {

    private static final int COOLDOWN = 5;
    private static final double SUCCESS_CHANCE = 0.4;
    private static final double NORMAL_ATTACK_CHANCE = 0.4;

    SneakAttack() {
        super(COOLDOWN);
    }

    @Override
    public AttackResult apply(final DungeonCharacter theSelf,
                              final DungeonCharacter theEnemy) {
        return null;
    }
}
