package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.BuffType.*;
import static model.TestingUtil.*;

public class AccuracyBuffTest {

    @Test
    void testConstructor() {
        final Buff buff = new AccuracyBuff(1);

        assertEquals(ACCURACY, buff.getType());
        assertEquals(1, buff.myDuration);
        assertEquals(0.0, buff.getDamagePercent());
    }

    @Test
    void testAdjustStats() {
        final AdjustedCharacterStats stats = buildAdjustedStats();
        final Buff buff = new AccuracyBuff(1);

        assertEquals(stats.myCharacter.getHitChance(), stats.getHitChance());
        buff.adjustStats(stats);
        assertEquals(
                Util.clampFraction(
                        stats.myCharacter.getHitChance() * buff.getStatMultiplier()
                ),
                stats.getHitChance()
        );
    }
}
