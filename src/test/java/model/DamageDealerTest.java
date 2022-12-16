package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

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
    void testGetDebuffChance() {
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
        final DungeonCharacter monster =
                MonsterFactory.getInstance().createRandom(Difficulty.EASY);
        monster.applyBuff(BuffType.STRENGTH, 1);

        assertEquals(
                monster.myAdjustedStats.getMinDamage(),
                monster.getAdjustedMinDamage()
        );
    }

    @Test
    void testGetAdjustedMaxDamage() {
        final DungeonCharacter monster =
                MonsterFactory.getInstance().createRandom(Difficulty.EASY);
        monster.applyBuff(BuffType.STRENGTH, 1);

        assertEquals(
                monster.myAdjustedStats.getMaxDamage(),
                monster.getAdjustedMaxDamage()
        );
    }

    @Test
    void testGetAdjustedHitChance() {
        final DungeonCharacter monster =
                MonsterFactory.getInstance().createRandom(Difficulty.EASY);
        monster.applyBuff(BuffType.ACCURACY, 1);

        assertEquals(
                monster.myAdjustedStats.getHitChance(),
                monster.getAdjustedHitChance()
        );
    }

    @Test
    void testGetAdjustedSpeed() {
        final DungeonCharacter monster =
            MonsterFactory.getInstance().createRandom(Difficulty.EASY);
        monster.applyBuff(BuffType.SPEED, 1);

        assertEquals(
                monster.myAdjustedStats.getSpeed(),
                monster.getAdjustedSpeed()
        );
    }

    @Test
    void testAttemptDamage() {
        TestingUtil.assertIsAttemptDamageResultType(
                DAMAGE_DEALER_1.attemptDamage(
                        DAMAGE_DEALER_2, true
                ).getResult()
        );
    }
}
