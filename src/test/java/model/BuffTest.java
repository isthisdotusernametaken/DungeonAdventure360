package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffTest {

    private StrengthBuff myBuff;

    @BeforeEach
    void setUpBuff() {
        myBuff = new StrengthBuff(1);
    }

    @Test
    void testGetBuffType() {
        assertNotEquals(BuffType.BROKEN_BONE, myBuff.getType());
        assertEquals(BuffType.STRENGTH, myBuff.getType());
    }

    @Test
    void testGetStatMultiplier() {
        assertNotEquals(154, myBuff.getStatMultiplier());
        assertEquals(1.5, myBuff.getStatMultiplier());
    }

    @Test
    void testGetDamagePercent() {
        assertNotEquals(10.0, myBuff.getDamagePercent());
        assertEquals(0.0, myBuff.getDamagePercent());
    }

//    @Test
//    void testGetDuration() {
//        assertNotEquals(2, myBuff.getDuration());
//        assertEquals(1, myBuff.getDuration());
//    }
//
//    @Test
//    void testChangeDuration() {
//        myBuff.changeDuration(4);
//        assertNotEquals(4, myBuff.getDuration());
//        assertEquals(5, myBuff.getDuration());
//    }
//
//    @Test
//    void testAdvanceBuffDuration() {
//        myBuff.advance();
//        assertEquals(0, myBuff.getDuration());
//    }

    @Test
    void testIsCompleted() {
        assertFalse(myBuff.isCompleted());
        myBuff.advance();
        assertTrue(myBuff.isCompleted());
    }
}