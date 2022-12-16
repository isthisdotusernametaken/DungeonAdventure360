package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.BuffType.*;
import static model.TestingUtil.*;

public class ResistanceBuffTest {

    @Test
    void testConstructor() {
        final Buff buff = new ResistanceBuff(1);

        assertEquals(RESISTANCE, buff.getType());
        assertEquals(1, buff.myDuration);
        assertEquals(0.0, buff.getDamagePercent());
    }

    @Test
    void testAdjustStats() {
        adjustStatsResistanceTestHelper(RESISTANCE);
    }
}
