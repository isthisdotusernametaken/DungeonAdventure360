package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AdventurerTest {

    private static final Adventurer ADVENTURER = new Adventurer(
            "Dark LORD",
            "Warrior",
            200,
            25,
            40,
            0.7,
            0.2,
            2,
            DamageType.BLUNT,
            4,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2}),
            new CrushingBlow());

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
    void testToString() {
        String expected = """
                Dark LORD
                 Warrior, 200/200 HP
                 Base Damage: 25-40, Blunt, 70% Accuracy
                 20% Chance to Debuff for 2 Turns
                 Speed: 4, 30% Block Chance
                 Resistances - Normal: 10%, Sharp: 10%, Blunt: 0%, Fire: 20%, Poison: 20%
                 No buffs
                 Special Skill: Crushing Blow
                """;

        assertEquals(expected, ADVENTURER.toString());
    }

    @Test
    void testGetSpecialSkill() {
        assertEquals(CrushingBlow.class, ADVENTURER.getSpecialSkill().getClass());
    }

    @Test
    void testViewSpecialSkill() {
        assertEquals("Crushing Blow", ADVENTURER.viewSpecialSkill());
    }

    @Test
    void testUseSpecialSkill() {
        AttackResult actual = ADVENTURER.useSpecialSkill(MONSTER).getResult();

        assertTrue(
                actual.equals(AttackResult.KILL) ||
                actual.equals(AttackResult.MISS));
    }
}
