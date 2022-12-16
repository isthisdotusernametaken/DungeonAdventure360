package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.Difficulty.*;

public class DifficultyTest {

    @Test
    void calculateMinFloorSizeLargeEnoughForPillarsAndTerminalPoints() {
        final int minSize = calculateMinFloorSize();

        // Guarantee enough space for entrance and exit (on outside, but then
        // only need at least 2 on outside because entrance and exit are
        // generated before pillars) and pillars
        assertTrue(minSize * minSize >= Pillar.createPillars().length + 2);
    }

    @Test
    void testGetDimensionsRelationshipBetweenDifficultyLevels() {
        assertValidDimensionRelationship(
                EASY.getDimensions(), NORMAL.getDimensions()
        );
        assertValidDimensionRelationship(
                NORMAL.getDimensions(), HARD.getDimensions()
        );
    }

    @Test
    void testModifyPositiveNonzero() {
        assertEquals(0.25, EASY.modifyPositive(0.2), 0.001);
    }

    @Test
    void testModifyPositiveZero() {
        assertEquals(0.0, EASY.modifyPositive(0.0));
    }

    @Test
    void testModifyNegativeDoubleNonzero() {
        assertEquals(0.15, EASY.modifyNegative(0.2), 0.001);
    }

    @Test
    void testModifyNegativeDoubleZero() {
        assertEquals(0.0, EASY.modifyNegative(0.0));
    }

    @Test
    void testModifyNegativeIntNonzero() {
        // Truncate
        assertEquals(32, HARD.modifyNegative(25));
    }

    @Test
    void testModifyNegativeIntZero() {
        assertEquals(0, EASY.modifyNegative(0));
    }

    private void assertValidDimensionRelationship(final RoomCoordinates theFirst,
                                                  final RoomCoordinates theSecond) {
        assertTrue(theFirst.getFloor() <= theSecond.getFloor());
        assertTrue(theFirst.getX() <= theSecond.getX());
        assertTrue(theFirst.getY() <= theSecond.getY());
    }
}
