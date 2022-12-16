package model;

import org.junit.jupiter.api.Test;

import static model.TestingUtil.assertIsAttemptDamageResultType;

public class SneakAttackTest {

    private static final SneakAttack SKILL = new SneakAttack();

    private static final DungeonCharacter ADVENTURER = new Adventurer(
            "Dark LORD",
            "Thief",
            125,
            15,
            35,
            0.8,
            0.3,
            1,
            DamageType.SHARP,
            10,
            0.1,
            new ResistanceData(new double[]{0.3, 0.3, 0.15, 0.3, 0.3}),
            new SneakAttack());
    private static final DungeonCharacter MONSTER = new Monster(
            "Skeleton",
            "Skeleton",
            110,
            15,
            25,
            0.5,
            0.1,
            2,
            DamageType.SHARP,
            4,
            0.05,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2})
    );

    @Test
    void testApply() {
        assertIsAttemptDamageResultType(
                SKILL.apply(ADVENTURER, MONSTER).getResult(),
                AttackResult.EXTRA_TURN_DEBUFF,
                AttackResult.EXTRA_TURN_NO_DEBUFF
        );
    }
}
