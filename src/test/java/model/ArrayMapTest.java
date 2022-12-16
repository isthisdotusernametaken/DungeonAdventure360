package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayMapTest {

    private ArrayMap myMap;

    @BeforeEach
    void createMap() {
        myMap = new ArrayMap(new RoomCoordinates(2, 2, 2));
    }

    @Test
    void testConstructorAllFalse() {
        for (boolean[][] floor : myMap.myExplored) {
            for (boolean[] row : floor) {
                for (boolean room : row) {
                    assertFalse(room);
                }
            }
        }
    }

    @Test
    void testIsExploredIntsFalse() {
        assertFalse(myMap.isExplored(1, 0, 1));
    }

    @Test
    void testIsExploredIntsOutOfBounds() {
        assertFalse(myMap.isExplored(-1, 0, 0));
    }

    @Test
    void testIsExploredIntsTrue() {
        myMap.myExplored[1][0][1] = true;
        assertTrue(myMap.isExplored(1, 0, 1));
    }

    @Test
    void testIsExploredCoordsFalse() {
        assertFalse(myMap.isExplored(new RoomCoordinates(1, 0, 1)));
    }

    @Test
    void testIsExploredCoordsOutOfBounds() {
        assertFalse(myMap.isExplored(new RoomCoordinates(0, -2, 0)));
    }

    @Test
    void testIsExploredCoordsTrue() {
        myMap.myExplored[1][0][1] = true;
        assertTrue(myMap.isExplored(new RoomCoordinates(1, 0, 1)));
    }

    @Test
    void testExploreIntsInBounds() {
        myMap.explore(0, 1, 1);
        assertTrue(myMap.isExplored(0, 1, 1));
    }

    @Test
    void testExploreIntsOutOfBounds() {
        assertDoesNotThrow(() -> myMap.explore(0, 0, -10));
        assertFalse(myMap.isExplored(0, 0, -10));
    }

    @Test
    void testExploreCoordsInBounds() {
        myMap.explore(new RoomCoordinates(0, 1, 1));
        assertTrue(myMap.isExplored(new RoomCoordinates(0, 1, 1)));
    }

    @Test
    void testExploreCoordsOutOfBounds() {
        assertDoesNotThrow(() -> myMap.explore(new RoomCoordinates(0, 0, 10)));
        assertFalse(myMap.isExplored(new RoomCoordinates(0, 0, 10)));
    }

    @Test
    void testIsInBoundsInBounds() {
        assertTrue(myMap.isInBounds(1, 1, 1));
    }

    @Test
    void testIsInBoundsOutOfBoundsNegative() {
        assertFalse(myMap.isInBounds(-1, 1, 1));
    }

    @Test
    void testIsInBoundsOutOfBoundsPositive() {
        assertFalse(myMap.isInBounds(1, 2, 1));
    }
}
