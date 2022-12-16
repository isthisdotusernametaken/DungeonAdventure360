package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.BuffType.*;
import static model.TestingUtil.*;

public class StrengthBuffTest {

    @Test
    void testConstructor() {
        final Buff buff = new StrengthBuff(1);

        assertEquals(STRENGTH, buff.getType());
        assertEquals(1, buff.myDuration);
        assertEquals(0.0, buff.getDamagePercent());
    }

    @Test
    void testAdjustStats() {
        adjustStatsDamageTestHelper(STRENGTH);
    }
}
