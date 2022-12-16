package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HealTest {

    private static final DungeonCharacter ADVENTURER = new Adventurer(
            "Dark LORD",
            "Priestess",
            150,
            15,
            30,
            0.75,
            0.4,
            4,
            DamageType.POISON,
            7,
            0.15,
            new ResistanceData(new double[]{0.3, 0.15, 0.1, 0.6, 0.6}),
            new Heal());
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
    void testApplyResult() {
        assertEquals(
                AttackResult.HEAL, new Heal().apply(ADVENTURER, MONSTER).getResult()
        );
    }

    @Test
    void testApplyAmount() {
        ADVENTURER.myHP--;
        assertEquals(1, new Heal().apply(ADVENTURER, MONSTER).getAmount());
    }
}
