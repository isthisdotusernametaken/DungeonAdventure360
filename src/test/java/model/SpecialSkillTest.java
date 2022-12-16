package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.TestingUtil.assertIsAttemptDamageResultType;

public class SpecialSkillTest {

    private static final DungeonCharacter ADVENTURER = new Adventurer(
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
        String expected = "Crushing Blow";

        assertEquals(expected, new CrushingBlow().toString());
    }

    @Test
    void testAdvance() {
        final SpecialSkill skill = new Heal();

        skill.myRemainingCooldown = 2;
        assertEquals(2, skill.myRemainingCooldown);
        skill.advance();
        assertEquals(1, skill.myRemainingCooldown);
    }

    @Test
    void testCanUseNoCooldown() {
        assertTrue(new SneakAttack().canUse());
    }

    @Test
    void testCanUseCooldown() {
        final SpecialSkill skill = new SneakAttack();
        skill.myRemainingCooldown = 1;

        assertFalse(skill.canUse());
    }

    @Test
    void testUse() {
        assertIsAttemptDamageResultType(
                new CrushingBlow().use(ADVENTURER, MONSTER).getResult()
        );
    }
}
