package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.ArrayDungeon.*;
import static model.Difficulty.*;

public class ArrayDungeonTest {

    private ArrayDungeon myDungeon;

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @BeforeEach
    void createDungeon() {
        myDungeon = new ArrayDungeon(HARD);
    }

    @Test
    void testConstructorDimensions() {
        assertNotNull(myDungeon.myDimensions);
        assertTrue(myDungeon.getDimensions().isSameRoom(HARD.getDimensions()));
        assertEquals(
                HARD.getDimensions().getFloor() - 1, myDungeon.myStairs.length
        );
    }

    @Test
    void testConstructorTerminalPoints() {
        assertNotNull(myDungeon.myTerminalPoints);
        assertEquals(2, myDungeon.myTerminalPoints.length);
        assertNotNull(myDungeon.myTerminalPoints[0]);
        assertNotNull(myDungeon.myTerminalPoints[1]);
    }

    @Test
    void testConstructorRooms() {
        assertEquals(HARD.getDimensions().getFloor(), myDungeon.myRooms.length);
        assertEquals(HARD.getDimensions().getX(), myDungeon.myRooms[0].length);
        assertEquals(HARD.getDimensions().getY(), myDungeon.myRooms[0][0].length);

        for (Room[][] floor : myDungeon.myRooms) {
            for (Room[] row : floor) {
                for (Room room : row) {
                    assertNotNull(room);
                }
            }
        }
    }

    @Test
    void testCreateUnknownRoomString() {
        final String[] room = createUnknownRoomString().split("\n");
        for (String line : room) {
            assertNotNull(line);
            assertEquals(TOTAL_ROOM_SIZE, line.length());

            for (char character : line.toCharArray()) {
                assertEquals(UNKNOWN, character);
            }
        }
    }

    @Test
    void testToString() {
        assertDoesNotThrow(() -> myDungeon.toString());

        final String[] dungeon = myDungeon.toString().split("\n");
        assertEquals(
                TOTAL_ROOM_SIZE * myDungeon.getDimensions().getX(),
                dungeon[2].length() // Guarantee not on stairs line
        );
    }

    @Test
    void testToStringIncludesAdventurer() {
        for (String line : myDungeon.toString(
                new RoomCoordinates(0, 0, 0), false
             ).split("\n")) {
           if (line.contains("" + Room.ADVENTURER)) {
               return; // Success
           }
        }

        fail();
    }

    @Test
    void testGetDimensions() {
        assertEquals(myDungeon.myDimensions, myDungeon.getDimensions());
    }

    @Test
    void testGetRoom() {
        assertEquals(
                myDungeon.myRooms[0][0][0],
                myDungeon.getRoom(new RoomCoordinates(0, 0, 0))
        );
    }

    @Test
    void testHasStairsUpSuccess() {
        assertTrue(myDungeon.hasStairsUp(
                myDungeon.myStairs[0].addFloor(
                        false, myDungeon.getDimensions().getFloor()
                )
        ));
    }

    @Test
    void testHasStairsUpFail() {
        assertFalse(myDungeon.hasStairsUp(
                myDungeon.myStairs[0]
        ));
    }

    @Test
    void testHasStairsDownSuccess() {
        assertTrue(myDungeon.hasStairsDown(
                myDungeon.myStairs[0]
        ));
    }

    @Test
    void testHasStairsDownFail() {
        assertFalse(myDungeon.hasStairsDown(
                myDungeon.myStairs[myDungeon.getDimensions().getFloor() - 2]
                        .addFloor(
                                true, myDungeon.getDimensions().getFloor()
                        )
        ));
    }

    @Test
    void testGetEntrance() {
        assertNotNull(myDungeon.getEntrance());
        assertEquals(myDungeon.myTerminalPoints[0], myDungeon.getEntrance());
    }

    @Test
    void testAppendSpaces() {
        final StringBuilder builder = new StringBuilder("test");
        final int initialLength = builder.length();

        myDungeon.appendSpaces(builder, 10);

        assertEquals(initialLength + 10, builder.length());
        for (int i = initialLength; i < builder.length(); i++) {
            assertEquals(' ', builder.charAt(i));
        }
    }

    @Test
    void testViewRowAsArrayRoomCount() {
        assertEquals(
                myDungeon.getDimensions().getX(),
                myDungeon.viewRowAsArray(0, 0, null, true).length
        );
    }

    @Test
    void testViewRowAsArrayRoomSize() {
        final String[] roomLines = myDungeon.viewRowAsArray(
                0, 0, null, true
        )[0].split("\n");

        assertEquals(TOTAL_ROOM_SIZE, roomLines.length);
        for (String line : roomLines) {
            assertEquals(TOTAL_ROOM_SIZE, line.length());
        }
    }

    @Test
    void testViewRowAsArrayHasHidden() {
        final String row = myDungeon.viewRowAsArray(0, 0, null, true)[0];

        assertTrue(row.contains("" + UNKNOWN));
    }

    @Test
    void testViewRowAsArrayHasNoHidden() {
        final String row = myDungeon.viewRowAsArray(0, 0, null, false)[0];

        assertFalse(row.contains("" + UNKNOWN));
    }
}
