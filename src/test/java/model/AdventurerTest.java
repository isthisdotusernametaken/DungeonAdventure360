package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AdventurerTest {

    private static final Adventurer myCharacter = new Adventurer(
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

        assertEquals(expected, myCharacter.toString());
    }

    @Test
    void testGetSpecialSkill() {
        SpecialSkill expected = new CrushingBlow();

        assertEquals(expected.getClass(), myCharacter.getSpecialSkill().getClass());
    }

    @Test
    void testViewSpecialSkill() {
        String expected = "Crushing Blow";

        assertEquals(expected, myCharacter.viewSpecialSkill());
    }

    @Test
    void testUseSpecialSkill() {
        AttackResult actual = myCharacter.useSpecialSkill(myMonster).getResult();

        assertTrue(
                actual.equals(AttackResult.KILL) ||
                actual.equals(AttackResult.MISS));
    }
}
