package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.BuffFactory.*;
import static model.BuffType.*;
import static model.TestingUtil.assertThrowsWithMessage;

public class BuffFactoryTest {

    @Test
    void testCreateStrength() {
        createTestHelper(StrengthBuff.class, 1, STRENGTH, 1);
    }

    @Test
    void testCreateSpeed() {
        createTestHelper(SpeedBuff.class, 7, SPEED, 7);
    }

    @Test
    void testCreateAccuracy() {
        createTestHelper(AccuracyBuff.class, 19, ACCURACY, 19);
    }

    @Test
    void testCreateResistance() {
        createTestHelper(ResistanceBuff.class, 500, RESISTANCE, 500);
    }

    @Test
    void testCreateBrokenBone() {
        createTestHelper(BrokenBoneDebuff.class, 980, BROKEN_BONE, 980);
    }

    @Test
    void testCreateBurningAndClamp() {
        createTestHelper(BurningDebuff.class, 1, BURNING, 0);
    }

    @Test
    void testCreateBleeding() {
        createTestHelper(
                BleedingDebuff.class, Util.MAX_INT, BLEEDING, Util.MAX_INT
        );
    }

    @Test
    void testCreatePoisonedAndClamp() {
        createTestHelper(
                PoisonedDebuff.class, Util.MAX_INT, POISONED, Util.MAX_INT + 1
        );
    }

    @Test
    void testCreateException() {
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> create(NONE, 3),
                "Invalid buff type: None"
        );
    }

    private void createTestHelper(final Class<? extends Buff> theClassType,
                                  final int theExpectedDuration,
                                  final BuffType theType,
                                  final int theProvidedDuration) {
        final Buff buff = create(theType, theProvidedDuration);

        assertEquals(theClassType, buff.getClass());
        assertEquals(theType, buff.getType());
        assertEquals(theExpectedDuration, buff.myDuration);
    }
}
