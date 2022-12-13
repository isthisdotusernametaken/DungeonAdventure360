package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdjustedCharacterStatsTest {

    private final AdjustedCharacterStats stats = new AdjustedCharacterStats(
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
    void getMinDamageTest () {
        assertEquals(15, stats.getMinDamage());
    }

    @Test
    void getMaxDamageTest () {
        assertEquals(25, stats.getMaxDamage());
    }

    @Test
    void getHitChanceTest () {
        assertEquals(0.5, stats.getHitChance());
    }

    @Test
    void getSpeedTest () {
        assertEquals(4, stats.getSpeed());
    }

    @Test
    void getResistanceTest () {
        assertEquals(0.1, stats.getResistance(DamageType.NORMAL));
        assertEquals(0.1, stats.getResistance(DamageType.SHARP));
        assertEquals(0.0, stats.getResistance(DamageType.BLUNT));
        assertEquals(0.2, stats.getResistance(DamageType.FIRE));
        assertEquals(0.2, stats.getResistance(DamageType.POISON));
    }

    @Test
    void multiplyDamageTest () {
        stats.multiplyDamage(2);

        assertEquals(30, stats.getMinDamage());
        assertEquals(50, stats.getMaxDamage());
    }

    @Test
    void multiplyHitChanceTest () {
        stats.multiplyHitChance(200000);

        assertEquals(1.0, stats.getHitChance());
    }

    @Test
    void multiplySpeedTest () {
        stats.multiplySpeed(9999);

        assertEquals(1000, stats.getSpeed());
    }

    @Test
    void multiplyResistancesTest () {
        stats.multiplyResistances(99999999);

        assertEquals(1.0, stats.getResistance(DamageType.NORMAL));
        assertEquals(1.0, stats.getResistance(DamageType.SHARP));
        assertEquals(0.0, stats.getResistance(DamageType.BLUNT));
        assertEquals(1.0, stats.getResistance(DamageType.FIRE));
        assertEquals(1.0, stats.getResistance(DamageType.POISON));
    }

    @Test
    void resetStatsTest () {
        stats.multiplyDamage(2);
        stats.multiplyHitChance(200000);
        stats.multiplySpeed(9999);
        stats.resetStats();

        assertEquals(15, stats.getMinDamage());
        assertEquals(25, stats.getMaxDamage());
        assertEquals(0.5, stats.getHitChance());
        assertEquals(4, stats.getSpeed());
    }

    @Test
    void resetResistancesTest () {
        stats.multiplyResistances(2);
        stats.resetResistances();

        assertEquals(0.1, stats.getResistance(DamageType.NORMAL));
        assertEquals(0.1, stats.getResistance(DamageType.SHARP));
        assertEquals(0.0, stats.getResistance(DamageType.BLUNT));
        assertEquals(0.2, stats.getResistance(DamageType.FIRE));
        assertEquals(0.2, stats.getResistance(DamageType.POISON));
    }
}