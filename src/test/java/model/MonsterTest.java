package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonsterTest {

    private final Monster myMonster = new Monster(
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
    void testToString() {
        String expected = """
                Skeleton
                 Skeleton, 110/110 HP
                 Base Damage: 15-25, Sharp, 50% Accuracy
                 10% Chance to Debuff for 2 Turns
                 Speed: 4, 5% Block Chance
                 Resistances - Normal: 10%, Sharp: 10%, Blunt: 0%, Fire: 20%, Poison: 20%
                 No buffs
                 30% Heal Chance per Turn
                """;

        assertEquals(expected,myMonster.toString());
    }

    @Test
    void testGetHealChance() {
        double expected = 0.3;

        assertEquals(expected, myMonster.getHealChance());
    }

    @Test
    void testAttemptHeal() {
        AttackResultAndAmount expected = new AttackResultAndAmount(AttackResult.NO_ACTION, 1);

        assertEquals(expected.getResult(), myMonster.attemptHeal().getResult()); //No action. Monster is full hp
    }
}
