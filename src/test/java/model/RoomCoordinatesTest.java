package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.Direction.*;

public class RoomCoordinatesTest {

    private static final RoomCoordinates COORDS = new RoomCoordinates(1, 1, 2);
    private static final RoomCoordinates DIMENSIONS = new RoomCoordinates(3, 3, 3);

    @Test
    void testConstructor() {
        final RoomCoordinates coords = new RoomCoordinates(0, 1, 2);

        assertEquals(0, coords.myFloor);
        assertEquals(1, coords.myX);
        assertEquals(2, coords.myY);
    }

    @Test
    void testToString() {
        assertEquals("Floor: 1; X: 1; Y: 2", COORDS.toString());
    }

    @Test
    void testGetFloor() {
        assertEquals(COORDS.myFloor, COORDS.getFloor());
    }

    @Test
    void testGetX() {
        assertEquals(COORDS.myX, COORDS.getX());
    }

    @Test
    void testGetY() {
        assertEquals(COORDS.myY, COORDS.getY());
    }

    @Test
    void testAddNorthNotClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, 1)
                        .add(NORTH, DIMENSIONS)
                        .isSameRoom(1, 1, 0)
        );
    }

    @Test
    void testAddNorthClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, 0)
                        .add(NORTH, DIMENSIONS)
                        .isSameRoom(1, 1, 0)
        );
    }

    @Test
    void testAddSouthNotClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, 1)
                        .add(SOUTH, DIMENSIONS)
                        .isSameRoom(1, 1, 2)
        );
    }

    @Test
    void testAddSouthClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, DIMENSIONS.getY() - 1)
                        .add(SOUTH, DIMENSIONS)
                        .isSameRoom(1, 1, DIMENSIONS.getY() - 1)
        );
    }

    @Test
    void testAddWestNotClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, 1)
                        .add(WEST, DIMENSIONS)
                        .isSameRoom(1, 0, 1)
        );
    }

    @Test
    void testAddWestClamped() {
        assertTrue(
                new RoomCoordinates(1, 0, 1)
                        .add(WEST, DIMENSIONS)
                        .isSameRoom(1, 0, 1)
        );
    }

    @Test
    void testAddEastNotClamped() {
        assertTrue(
                new RoomCoordinates(1, 1, 1)
                        .add(EAST, DIMENSIONS)
                        .isSameRoom(1, 2, 1)
        );
    }

    @Test
    void testAddEastClamped() {
        assertTrue(
                new RoomCoordinates(1, DIMENSIONS.getX() - 1, 1)
                        .add(EAST, DIMENSIONS)
                        .isSameRoom(1, DIMENSIONS.getX() - 1, 1)
        );
    }

    @Test
    void testAddFloorUp() {
        assertTrue(
                COORDS.addFloor(true, DIMENSIONS.getFloor()).isSameRoom(
                        COORDS.getFloor() - 1, COORDS.getX(), COORDS.getY()
                )
        );
    }

    @Test
    void testAddFloorDown() {
        assertTrue(
                COORDS.addFloor(false, DIMENSIONS.getFloor()).isSameRoom(
                        COORDS.getFloor() + 1, COORDS.getX(), COORDS.getY()
                )
        );
    }

    @Test
    void testIsOneBelowTrue() {
        assertTrue(
                COORDS.addFloor(false, DIMENSIONS.getFloor())
                        .isOneBelow(COORDS)
        );
    }

    @Test
    void testIsOneBelowFalse() {
        assertFalse(COORDS.isOneBelow(COORDS));
    }

    @Test
    void testIsSameRoomTrue() {
        assertTrue(COORDS.isSameRoom(new RoomCoordinates(
                COORDS.getFloor(), COORDS.getX(), COORDS.getY())
        ));
    }

    @Test
    void testIsSameRoomFalse() {
        assertFalse(COORDS.isSameRoom(new RoomCoordinates(
                COORDS.getFloor() + 1, COORDS.getX(), COORDS.getY())
        ));
    }

    @Test
    void testAddXNotClamped() {
        assertTrue(
                COORDS.addX(6, 10)
                        .isSameRoom(COORDS.myFloor, COORDS.myX + 6, COORDS.myY)
        );
    }

    @Test
    void testAddXClamped() {
        assertTrue(
                COORDS.addX(7, 9)
                        .isSameRoom(COORDS.myFloor, 8, COORDS.myY)
        );
    }

    @Test
    void testAddYNotClamped() {
        assertTrue(
                COORDS.addY(6, 10)
                        .isSameRoom(COORDS.myFloor, COORDS.myX, COORDS.myY + 6)
        );
    }

    @Test
    void testAddYClamped() {
        assertTrue(
                COORDS.addY(7, 9)
                        .isSameRoom(COORDS.myFloor, COORDS.myX, 8)
        );
    }
}
