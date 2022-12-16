package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import static model.BuffType.*;
import static model.TestingUtil.*;

public class BurningDebuffTest {

    @Test
    void testConstructor() {
        final Buff buff = new BurningDebuff(1);

        assertEquals(BURNING, buff.getType());
        assertEquals(1, buff.myDuration);
        assertNotEquals(0.0, buff.getDamagePercent());
    }

    @Test
    void testAdjustStats() {
        adjustStatsResistanceTestHelper(BURNING);
    }
}
