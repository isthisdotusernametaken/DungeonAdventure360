package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SneakAttackTest {

    private static final SneakAttack mySkill = new SneakAttack();

    private static final DungeonCharacter myCharacter = new Adventurer(
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

    private final DungeonCharacter myMonster = new Monster(
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
        AttackResult actual = mySkill.apply(myCharacter, myMonster).getResult();

        assertTrue(
                actual.equals(AttackResult.KILL) ||
                        actual.equals(AttackResult.MISS) ||
                        actual.equals(AttackResult.HIT_NO_DEBUFF) ||
                        actual.equals(AttackResult.HIT_DEBUFF) ||
                        actual.equals(AttackResult.EXTRA_TURN_DEBUFF) ||
                        actual.equals(AttackResult.EXTRA_TURN_NO_DEBUFF));
    }
}
