package model;

public class Heal extends SpecialSkill {

    private static final int COOLDOWN = 4;
    private static final double HEAL_PERCENT = 0.3;

    Heal() {
        super(COOLDOWN);
    }

    @Override
    public AttackResult apply(final DungeonCharacter theSelf,
                              final DungeonCharacter theEnemy) {
        return null;
    }
}
