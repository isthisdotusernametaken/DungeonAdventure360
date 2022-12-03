package model;

public class CrushingBlow extends SpecialSkill {

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
    public AttackResult apply(final DungeonCharacter theSelf,
                              final DungeonCharacter theEnemy) {
        return null;
    }
}
