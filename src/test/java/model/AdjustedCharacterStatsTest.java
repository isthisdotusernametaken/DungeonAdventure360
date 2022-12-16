package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AdjustedCharacterStatsTest {

    private static final AdjustedCharacterStats STATS = new AdjustedCharacterStats(
            new Monster(
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
                    new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2})));

    @Test
    void testGetMinDamage() {
        assertEquals(15, STATS.getMinDamage());
    }

    @Test
    void testMaxDamage() {
        assertEquals(25, STATS.getMaxDamage());
    }

    @Test
    void testGetHitChance() {
        assertEquals(0.5, STATS.getHitChance());
    }

    @Test
    void testGetSpeed() {
        assertEquals(4, STATS.getSpeed());
    }

    @Test
    void testGetResistance() {
        assertEquals(0.1, STATS.getResistance(DamageType.NORMAL));
        assertEquals(0.1, STATS.getResistance(DamageType.SHARP));
        assertEquals(0.0, STATS.getResistance(DamageType.BLUNT));
        assertEquals(0.2, STATS.getResistance(DamageType.FIRE));
        assertEquals(0.2, STATS.getResistance(DamageType.POISON));
    }

    @Test
    void testMultiplyDamage() {
        STATS.multiplyDamage(2);

        assertEquals(30, STATS.getMinDamage());
        assertEquals(50, STATS.getMaxDamage());

        STATS.resetStats();
    }

    @Test
    void testMultiplyHitChance() {
        STATS.multiplyHitChance(200000);

        assertEquals(1.0, STATS.getHitChance());

        STATS.resetStats();
    }

    @Test
    void testMultiplySpeed() {
        STATS.multiplySpeed(9999);

        assertEquals(1000, STATS.getSpeed());

        STATS.resetStats();
    }

    @Test
    void testMultiplyResistances() {
        STATS.multiplyResistances(99999999);

        assertEquals(1.0, STATS.getResistance(DamageType.NORMAL));
        assertEquals(1.0, STATS.getResistance(DamageType.SHARP));
        assertEquals(0.0, STATS.getResistance(DamageType.BLUNT));
        assertEquals(1.0, STATS.getResistance(DamageType.FIRE));
        assertEquals(1.0, STATS.getResistance(DamageType.POISON));

        STATS.resetStats();
    }

    @Test
    void testResetStat() {
        STATS.multiplyDamage(2);
        STATS.multiplyHitChance(200000);
        STATS.multiplySpeed(9999);
        STATS.resetStats();

        assertEquals(15, STATS.getMinDamage());
        assertEquals(25, STATS.getMaxDamage());
        assertEquals(0.5, STATS.getHitChance());
        assertEquals(4, STATS.getSpeed());
    }

    @Test
    void testResetResistances() {
        STATS.multiplyResistances(2);
        STATS.resetResistances();

        assertEquals(0.1, STATS.getResistance(DamageType.NORMAL));
        assertEquals(0.1, STATS.getResistance(DamageType.SHARP));
        assertEquals(0.0, STATS.getResistance(DamageType.BLUNT));
        assertEquals(0.2, STATS.getResistance(DamageType.FIRE));
        assertEquals(0.2, STATS.getResistance(DamageType.POISON));
    }
}