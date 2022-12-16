package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BuffTypeTest {

    @Test
    void getAllPositiveBuffTypes() {
        assertArrayEquals(new BuffType[] {
                BuffType.STRENGTH,
                BuffType.SPEED,
                BuffType.ACCURACY,
                BuffType.RESISTANCE}, BuffType.getAllPositiveBuffTypes());
    }

    @Test
    void toStringTest() {
        assertEquals("None", BuffType.NONE.toString());
        assertEquals("Strength", BuffType.STRENGTH.toString());
        assertEquals("Speed", BuffType.SPEED.toString());
        assertEquals("Accuracy", BuffType.ACCURACY.toString());
        assertEquals("Resistance", BuffType.RESISTANCE.toString());
        assertEquals("Broken Bone", BuffType.BROKEN_BONE.toString());
        assertEquals("Burning", BuffType.BURNING.toString());
        assertEquals("Bleeding", BuffType.BLEEDING.toString());
        assertEquals("Poisoned", BuffType.POISONED.toString());
    }

    @Test
    void getCharRepresentation() {
        assertEquals(' ', BuffType.NONE.charRepresentation());
        assertEquals('S', BuffType.STRENGTH.charRepresentation());
        assertEquals('>', BuffType.SPEED.charRepresentation());
        assertEquals('^', BuffType.ACCURACY.charRepresentation());
        assertEquals('R', BuffType.RESISTANCE.charRepresentation());
        assertEquals('{', BuffType.BROKEN_BONE.charRepresentation());
        assertEquals('F', BuffType.BURNING.charRepresentation());
        assertEquals('B', BuffType.BLEEDING.charRepresentation());
        assertEquals('C', BuffType.POISONED.charRepresentation());
    }

    @Test
    void testIsDebuff() {
        assertFalse(BuffType.NONE.isDebuff());
        assertFalse(BuffType.STRENGTH.isDebuff());
        assertFalse(BuffType.SPEED.isDebuff());
        assertFalse(BuffType.ACCURACY.isDebuff());
        assertFalse(BuffType.RESISTANCE.isDebuff());
        assertTrue(BuffType.BROKEN_BONE.isDebuff());
        assertTrue(BuffType.BURNING.isDebuff());
        assertTrue(BuffType.BLEEDING.isDebuff());
        assertTrue(BuffType.POISONED.isDebuff());
    }
}