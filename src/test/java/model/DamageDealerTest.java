package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DamageDealerTest {

    private static final DamageDealer DAMAGE_DEALER_1 = new Adventurer(
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

    private static final DungeonCharacter DAMAGE_DEALER_2 = new Adventurer(
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

    @Test
    void testGetClassName() {
        assertEquals("Warrior", DAMAGE_DEALER_1.getClassName());
    }

    @Test
    void testGetMinDamage() {
        assertEquals(25, DAMAGE_DEALER_1.getMinDamage());
    }

    @Test
    void testGetMaxDamage() {
        assertEquals(40, DAMAGE_DEALER_1.getMaxDamage());
    }

    @Test
    void testGetHitChance() {
        assertEquals(0.7, DAMAGE_DEALER_1.getHitChance());
    }

    @Test
    void testGeDebuffChance() {
        assertEquals(0.2, DAMAGE_DEALER_1.getDebuffChance());
    }

    @Test
    void testGetDebuffDuration() {
        assertEquals(2, DAMAGE_DEALER_1.getDebuffDuration());
    }

    @Test
    void testGetDamageType() {
        assertEquals(DamageType.BLUNT, DAMAGE_DEALER_1.getDamageType());
    }

    @Test
    void testGetSpeed() {
        assertEquals(4, DAMAGE_DEALER_1.getSpeed());
    }

    @Test
    void testGetAdjustedMinDamage() {
        assertEquals(25, DAMAGE_DEALER_1.getMinDamage());
    }

    @Test
    void testGetAdjustedMaxDamage() {
        assertEquals(40, DAMAGE_DEALER_1.getMaxDamage());
    }

    @Test
    void testGetAdjustedHitChance() {
        assertEquals(0.7, DAMAGE_DEALER_1.getHitChance());
    }

    @Test
    void testGetAdjustedSpeed() {
        assertEquals(4, DAMAGE_DEALER_1.getSpeed());
    }

    @Test
    void testAttemptDamage() {
        final AttackResult actual = DAMAGE_DEALER_1.attemptDamage(
                DAMAGE_DEALER_2, true
        ).getResult();

        assertTrue(
                actual.equals(new AttackResultAndAmount(AttackResult.BLOCK, 2).getResult()) ||
                        actual.equals(new AttackResultAndAmount(AttackResult.HIT_NO_DEBUFF, 2).getResult()) ||
                        actual.equals(new AttackResultAndAmount(AttackResult.MISS, 2).getResult()));
    }
}
