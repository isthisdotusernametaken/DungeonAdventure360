package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DamageDealerTest {

    private static final DamageDealer myDamageDealer = new Adventurer(
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

    @Test
    void testGetClassName() {
        String expected = "Warrior";

        assertEquals(expected, myDamageDealer.getClassName());
    }

    @Test
    void testGetMinDamage() {
        int expected = 25;

        assertEquals(expected, myDamageDealer.getMinDamage());
    }

    @Test
    void testGetMaxDamage() {
        int expected = 40;

        assertEquals(expected, myDamageDealer.getMaxDamage());
    }

    @Test
    void testGetHitChance() {
        double expected = 0.7;

        assertEquals(expected, myDamageDealer.getHitChance());
    }

    @Test
    void testGeDebuffChance() {
        double expected = 0.2;

        assertEquals(expected, myDamageDealer.getDebuffChance());
    }

    @Test
    void testGetDebuffDuration() {
        int expected = 2;

        assertEquals(expected, myDamageDealer.getDebuffDuration());
    }

    @Test
    void testGetDamageType() {
        DamageType expected = DamageType.BLUNT;

        assertEquals(expected, myDamageDealer.getDamageType());
    }

    @Test
    void testGetSpeed() {
        int expected = 4;

        assertEquals(expected, myDamageDealer.getSpeed());
    }

    @Test
    void testGetAdjustedMinDamage() {
        int expected = 25;

        assertEquals(expected, myDamageDealer.getMinDamage());
    }

    @Test
    void testGetAdjustedMaxDamage() {
        int expected = 40;

        assertEquals(expected, myDamageDealer.getMaxDamage());
    }

    @Test
    void testGetAdjustedHitChance() {
        double expected = 0.7;

        assertEquals(expected, myDamageDealer.getHitChance());
    }

    @Test
    void testGetAdjustedSpeed() {
        int expected = 4;

        assertEquals(expected, myDamageDealer.getSpeed());
    }

    @Test
    void testAttemptDamage() {
        AttackResult actual = myDamageDealer.attemptDamage(myCharacter, true).getResult();

        assertTrue(
                actual.equals(new AttackResultAndAmount(AttackResult.BLOCK, 2).getResult()) ||
                        actual.equals(new AttackResultAndAmount(AttackResult.HIT_NO_DEBUFF, 2).getResult()) ||
                        actual.equals(new AttackResultAndAmount(AttackResult.MISS, 2).getResult()));
    }
}
