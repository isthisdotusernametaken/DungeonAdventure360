package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.TurnAllocator.*;

public class TurnAllocatorTest {

    @Test
    void testConstructor() {
        final TurnAllocator allocator = new TurnAllocator(11, 5);

        assertEquals(0, allocator.myCurrentTurn);
        assertEquals(11 / 5, allocator.myExtraTurnIndex);
        assertEquals(11.0 / 5.0 - 11 / 5, allocator.myExtraTurnChance);
        // Number of turns for faster, extra turn for faster, turn for slower
        assertEquals(Math.ceil(11.0 / 5.0) + 1, allocator.myTurns.length);
        assertFalse(allocator.myIsCompleted);
    }

    @Test
    void testCalculateSpeedRatioAdventurerFaster() {
        assertEquals(11.0 / 5.0, calculateSpeedRatio(true, 11, 5));
    }

    @Test
    void testCalculateSpeedRatioMonsterFaster() {
        assertEquals(11.0 / 5.0, calculateSpeedRatio(false, 5, 11));
    }

    @Test
    void testIsCompletedTrue() {
        final TurnAllocator allocator = new TurnAllocator(10, 2);
        allocator.myIsCompleted = true;

        assertTrue(allocator.isCompleted());
    }

    @Test
    void testIsCompletedFalse() {
        assertFalse(new TurnAllocator(10, 2).isCompleted());
    }

    @Test
    void testPeekNextTurn() {
        final TurnAllocator allocator = new TurnAllocator(10, 2);

        assertEquals(allocator.myTurns[0], allocator.peekNextTurn());
        assertEquals(0, allocator.myCurrentTurn);
    }

    @Test
    void testNextTurn() {
        final TurnAllocator allocator = new TurnAllocator(10, 2);

        assertEquals(0, allocator.myCurrentTurn);
        allocator.nextTurn();
        assertEquals(1, allocator.myCurrentTurn);
    }

    @Test
    void testIncrementTurn() {
        final TurnAllocator allocator = new TurnAllocator(10, 2);

        assertEquals(0, allocator.myCurrentTurn);
        allocator.incrementTurn();
        assertEquals(1, allocator.myCurrentTurn);
    }

    @Test
    void testIncrementTurnIfSkippingExtraNotExtraTurn() {
        final TurnAllocator allocator = new TurnAllocator(10, 2);

        assertEquals(0, allocator.myCurrentTurn);
        allocator.incrementTurnIfSkippingExtra();
        assertEquals(0, allocator.myCurrentTurn);
    }

    @Test
    void testIncrementTurnIfSkippingExtraExtraTurn() {
        final TurnAllocator allocator = new TurnAllocator(3, 2);
        allocator.myCurrentTurn = 1;

        allocator.incrementTurnIfSkippingExtra();
        assertTrue(1 == allocator.myCurrentTurn || 2 == allocator.myCurrentTurn);
    }

    @Test
    void testAssignTurnsAdventurerFaster() {
        final TurnAllocator allocator = new TurnAllocator(3, 2);

        // 1 turn for faster, 1 extra turn for faster, 1 turn for slower
        // (3 / 2 = 1 + 1/2 faster turns per slower turn)
        assertArrayEquals(
                new boolean[]{true, true, false}, allocator.assignTurns(true)
        );
    }

    @Test
    void testAssignTurnsMonsterFaster() {
        final TurnAllocator allocator = new TurnAllocator(2, 3);

        // 1 turn for faster, 1 extra turn for faster, 1 turn for slower
        // (3 / 2 = 1 + 1/2 faster turns per slower turn)
        assertArrayEquals(
                new boolean[]{false, false, true}, allocator.assignTurns(false)
        );
    }
}
