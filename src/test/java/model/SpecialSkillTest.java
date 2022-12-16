package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SpecialSkillTest {
    private final static SpecialSkill mySpeciallSkill = new CrushingBlow();

    private static final DungeonCharacter myCharacter = new Adventurer(
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
        String expected = "Crushing Blow";

        assertEquals(expected, mySpeciallSkill.toString());
    }

    @Test
    void testAdvance() {
        int expected = 0; //0 because it has not been used yet
        mySpeciallSkill.advance();

        assertEquals(expected, mySpeciallSkill.myRemainingCooldown);
    }

    @Test
    void testCanUse() {
        boolean expected = true;

        assertEquals(expected, mySpeciallSkill.canUse());
    }

    @Test
    void testUse() {
        AttackResult actual = mySpeciallSkill.use(myCharacter, myMonster).getResult();

        assertTrue(
                actual.equals(AttackResult.KILL) ||
                actual.equals(AttackResult.MISS) ||
                actual.equals(AttackResult.HIT_NO_DEBUFF));
    }
}
