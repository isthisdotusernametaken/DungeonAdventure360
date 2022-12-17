package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.Util.*;

public class UtilTest {

    @Test
    void testIsValidIndexMin() {
        assertTrue(isValidIndex(0, 4));
    }

    @Test
    void testIsValidIndexMax() {
        assertTrue(isValidIndex(3, 4));
    }

    @Test
    void testIsValidIndexNegative() {
        assertFalse(isValidIndex(-1, 4));
    }

    @Test
    void testIsValidIndexTooLarge() {
        assertFalse(isValidIndex(4, 4));
    }

    @Test
    void testProbabilityTestZeroPercent() {
        assertFalse(probabilityTest(0.0));
    }

    @Test
    void testProbabilityTestOneHundredPercent() {
        assertTrue(probabilityTest(1.0));
    }

    @Test
    void testRandomDouble() {
        final double random = randomDouble();
        assertTrue(random >= 0.0 && random <= 1.0);
    }

    @Test
    void testClampFractionValid() {
        assertEquals(0.5, clampFraction(0.5));
    }

    @Test
    void testClampFractionTooSmall() {
        assertEquals(0.0, clampFraction(-0.1));
    }

    @Test
    void testClampFractionTooLarge() {
        assertEquals(1.0, clampFraction(1.1));
    }

    @Test
    void testRandomIntExc() {
        final int random = randomIntExc(2);
        assertTrue(random == 0 || random == 1);
    }

    @Test
    void testRandomIntInc() {
        final int random = randomIntInc(0, 1);
        assertTrue(random == 0 || random == 1);
    }

    @Test
    void testClampPositiveIntImpliedMaxValid() {
        assertEquals(MAX_INT / 2, clampPositiveInt(MAX_INT / 2));
    }

    @Test
    void testClampPositiveIntImpliedMaxTooSmall() {
        assertEquals(1, clampPositiveInt(0));
    }

    @Test
    void testClampPositiveIntImpliedMaxTooLarge() {
        assertEquals(MAX_INT, clampPositiveInt(MAX_INT + 1));
    }

    @Test
    void testClampPositiveIntValid() {
        assertEquals(5, clampPositiveInt(5, 5));
    }

    @Test
    void testClampPositiveIntTooSmall() {
        assertEquals(1, clampPositiveInt(0, 5));
    }

    @Test
    void testClampPositiveIntTooLarge() {
        assertEquals(5, clampPositiveInt(6, 5));
    }

    @Test
    void testClampIntZeroToMaxExcMin() {
        assertEquals(0, clampIntZeroToMaxExc(10, 0));
    }

    @Test
    void testClampIntZeroToMaxExcMax() {
        assertEquals(9, clampIntZeroToMaxExc(10, 9));
    }

    @Test
    void testClampIntZeroToMaxTooSmall() {
        assertEquals(0, clampIntZeroToMaxExc(10, -1));
    }

    @Test
    void testClampIntZeroToMaxTooLarge() {
        assertEquals(9, clampIntZeroToMaxExc(10, 10));
    }

    @Test
    void testAddAndClampIntMin() {
        assertEquals(10, addAndClampInt(10, 20, 4, 6));
    }

    @Test
    void testAddAndClampIntMax() {
        assertEquals(20, addAndClampInt(10, 20, 10, 10));
    }

    @Test
    void testAddAndClampIntTooSmall() {
        assertEquals(10, addAndClampInt(10, 20, 5, 1));
    }

    @Test
    void testAddAndClampIntTooLarge() {
        assertEquals(20, addAndClampInt(10, 20, 15, 37));
    }

    @Test
    void testAsPercentZero() {
        assertEquals("0%", asPercent(0.0));
    }

    @Test
    void testAsPercentOne() {
        assertEquals("100%", asPercent(1.0));
    }

    @Test
    void testAsPercentInRange() {
        assertEquals("50%", asPercent(0.503));
    }

    @Test
    void testCreateNameFromClassName() {
        assertEquals(
                "String Builder",
                createNameFromClassName(new StringBuilder())
        );
    }

    @Test
    void testCreateNameFromEnumName() {
        assertEquals(
                "North",
                createNameFromEnumName(Direction.NORTH)
        );
    }
}
