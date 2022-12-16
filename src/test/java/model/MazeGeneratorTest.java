package model;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.ArrayDungeon.MazeGenerator.*;
import static model.Difficulty.*;
import static model.Direction.*;

public class MazeGeneratorTest {

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testGenerateStairsCorrectSizeAndNotNull() {
        final RoomCoordinates[] stairs = generateStairs(NORMAL.getDimensions());

        assertEquals(NORMAL.getDimensions().getFloor() - 1, stairs.length);
        for (RoomCoordinates flight : stairs) {
            assertNotNull(flight);
        }
    }

    @Test
    void testGenerateMazeCorrectSizeAndNotNull() {
        final Room[][][] maze = generateMaze(
                NORMAL.getDimensions(),
                0.5, 0.5, 0.5,
                NORMAL,
                new RoomCoordinates[2]
        );

        assertEquals(NORMAL.getDimensions().getFloor(), maze.length);
        assertEquals(NORMAL.getDimensions().getX(), maze[0].length);
        assertEquals(NORMAL.getDimensions().getY(), maze[0][0].length);

        for (Room[][] floor : maze) {
            for (Room[] row : floor) {
                for (Room room : row) {
                    assertNotNull(room);
                }
            }
        }
    }

    @Test
    void testAddFloorAllRoomsNotNull() {
        final RoomCoordinates dimensions = NORMAL.getDimensions();
        final Room[][][] maze = new Room[dimensions.getFloor()]
                                        [dimensions.getX()]
                                        [dimensions.getY()];
        addFloor(
                maze,
                dimensions,
                0,
                0.5, 0.5, 0.5,
                NORMAL
        );

        for (Room[] row : maze[0]) {
            for (Room room : row) {
                assertNotNull(room);
            }
        }
    }

    @Test
    void testGenerateFloorLayoutAllRoomsAtLeastOneDoor() {
        for (boolean[][] row : generateFloorLayout(NORMAL.getDimensions(), 0)) {
            for (boolean[] roomDoors : row) {
                assertTrue(
                        roomDoors[0] || roomDoors[1] ||
                        roomDoors[2] || roomDoors[3]
                );
            }
        }
    }

    @Test
    void testVisitStartingCell() {
        final RoomCoordinates dimensions = EASY.getDimensions();
        final boolean[][] visited = new boolean[dimensions.getX()]
                                               [dimensions.getY()];
        final Stack<int[]> cells = new Stack<>();

        visitStartingCell(visited, cells, dimensions, 0);

        assertFalse(cells.isEmpty());
        final int[] chosenCell = cells.pop();
        assertTrue(visited[chosenCell[0]][chosenCell[1]]);
    }

    @Test
    void testRandomUnvisitedNeighborFound() {
        final RoomCoordinates dimensions = EASY.getDimensions();
        final boolean[][] visited = new boolean[dimensions.getX()]
                [dimensions.getY()];

        final int[] neighbor =
                randomUnvisitedNeighbor(visited, new int[]{0, 0});

        assertNotEquals(NO_UNVISITED_NEIGHBORS, neighbor);
        assertTrue((
                neighbor[0] == 0 && neighbor[1] == 1) ||
                (neighbor[0] == 1 && neighbor[1] == 0)
        );
    }

    @Test
    void testRandomUnvisitedNeighborNone() {
        final RoomCoordinates dimensions = EASY.getDimensions();
        final boolean[][] visited = new boolean[dimensions.getX()]
                                               [dimensions.getY()];
        // Visit all neighboring cells
        visited[0][1] = true;
        visited[1][0] = true;

        assertEquals(
                NO_UNVISITED_NEIGHBORS,
                randomUnvisitedNeighbor(visited, new int[]{0, 0})
        );
    }

    @Test
    void testIsValidAndUnvisitedInvalid() {
        assertFalse(isValidAndUnvisited(new boolean[2][2], 1, 2));
    }

    @Test
    void testIsValidAndUnvisitedVisited() {
        final boolean[][] visited = new boolean[2][2];
        visited[0][1] = true;

        assertFalse(isValidAndUnvisited(visited, 0, 1));
    }

    @Test
    void testIsValidAndUnvisitedUnvisited() {
        assertTrue(isValidAndUnvisited(new boolean[2][2], 0, 1));
    }

    @Test
    void testAddDoorsBetweenHorizontal() {
        final boolean[][][] doors = new boolean[2][2][4];
        addDoorsBetween(doors, new int[]{0, 0}, new int[]{1, 0});

        assertTrue(doors[0][0][EAST.ordinal()]);
        assertTrue(doors[1][0][WEST.ordinal()]);
    }

    @Test
    void testAddDoorsBetweenVertical() {
        final boolean[][][] doors = new boolean[2][2][4];
        addDoorsBetween(doors, new int[]{0, 0}, new int[]{0, 1});

        assertTrue(doors[0][0][SOUTH.ordinal()]);
        assertTrue(doors[0][1][NORTH.ordinal()]);
    }

    @Test
    void testAddHorizontalDoors() {
        final boolean[][][] doors = new boolean[2][2][4];
        addHorizontalDoors(
                doors[0][0], doors[1][0], new int[]{0, 0}, new int[]{1, 0}
        );

        assertTrue(doors[0][0][EAST.ordinal()]);
        assertTrue(doors[1][0][WEST.ordinal()]);
    }

    @Test
    void testAddVerticalDoors() {
        final boolean[][][] doors = new boolean[2][2][4];
        addVerticalDoors(
                doors[0][0], doors[0][1], new int[]{0, 0}, new int[]{0, 1}
        );

        assertTrue(doors[0][0][SOUTH.ordinal()]);
        assertTrue(doors[0][1][NORTH.ordinal()]);
    }

    @Test
    void testAddRoomRandomRoom() {
        final boolean[] doors = new boolean[]{true, true, true, true};
        final Room[][][] maze = new Room[2][2][2];
        addRoom(maze, 0, 0, 0, doors, 0.5, 0.5, 0.5, EASY);

        assertNotNull(maze[0][0][0]);
        assertArrayEquals(doors, maze[0][0][0].myDoors);
        assertFalse(maze[0][0][0].isEntrance() || maze[0][0][0].isExit());
    }

    @Test
    void testAddRoomPillarOrTerminalPoint() {
        final boolean[] overwrittenDoors = new boolean[]{true, false, true, false};
        final boolean[] doors = new boolean[]{true, true, true, true};
        final Room[][][] maze = new Room[2][2][2];
        maze[0][0][0] = new Room(overwrittenDoors, null, null, true, false);

        addRoom(maze, 0, 0, 0, doors, 0.5, 0.5, 0.5, EASY);

        assertNotNull(maze[0][0][0]);
        assertArrayEquals(doors, maze[0][0][0].myDoors);
        assertTrue(maze[0][0][0].isEntrance());
    }

    @Test
    void testRandomRoom() {
        final boolean[] doors = new boolean[]{true, true, true, true};
        final Room room = randomRoom(doors, 0.0, 1.0, 1.0, EASY);

        assertNotNull(room);
        assertArrayEquals(doors, room.myDoors);
        assertNull(room.myMonster);
        assertNotNull(room.myTrap);
        assertTrue(room.myContainer.hasItems());
    }

    @Test
    void testAddTerminalPointsAndPillars() {
        final Room[][][] maze = new Room[1][3][3];
        final RoomCoordinates[] terminalPoints = new RoomCoordinates[2];
        addTerminalPointsAndPillars(
                new RoomCoordinates(1, 3, 3), maze, terminalPoints
        );

        boolean entranceFound = false, exitFound = false;
        int pillarCount = 0;
        for (Room[][] floor : maze) {
            for (Room[] row : floor) {
                for (Room room : row) {
                    if (room != null) {
                        if (room.isEntrance()) {
                            entranceFound = true;
                        } else if (room.isExit()) {
                            exitFound = true;
                        } else if (room.myContainer.hasItems()) {
                            pillarCount++;
                        }
                    }
                }
            }
        }
        assertTrue(entranceFound);
        assertTrue(exitFound);
        assertEquals(Pillar.createPillars().length, pillarCount);
    }

    @Test
    void testAddEmptyRoom() {
        final Room[][][] maze = new Room[1][2][2];
        addEmptyRoom(maze, new RoomCoordinates(0, 0, 0), true, false);

        assertNotNull(maze[0][0][0]);
        assertNull(maze[0][0][0].myMonster);
        assertNull(maze[0][0][0].myTrap);
        assertTrue(maze[0][0][0].isEntrance());
        assertFalse(maze[0][0][0].isExit());
    }

    @Test
    void testRandomTerminalPointsAndPillars() {
        final RoomCoordinates[] terminalPointsAndPillars =
                randomTerminalPointsAndPillars(
                        EASY.getDimensions(), Pillar.createPillars().length
                );

        assertEquals(
                2 + Pillar.createPillars().length,
                terminalPointsAndPillars.length
        );
        for (RoomCoordinates targetRoom : terminalPointsAndPillars) {
            assertNotNull(targetRoom);
        }
        assertIsOnWall(EASY.getDimensions(), terminalPointsAndPillars[0]);
        assertIsOnWall(EASY.getDimensions(), terminalPointsAndPillars[1]);
    }

    @Test
    void testRandomDifferentCoords() {
        assertTrue(
                randomDifferentCoords(
                        new RoomCoordinates(1, 2, 2),
                        new RoomCoordinates(0, 0, 0),
                        new RoomCoordinates(0, 0, 1),
                        new RoomCoordinates(0, 1, 0)
                ).isSameRoom(0, 1, 1)
        );
    }

    @Test
    void testRandomDifferentCoordsOnWallAllWalls() {
        assertTrue(
                randomDifferentCoordsOnWall(
                        new RoomCoordinates(1, 2, 1),
                        0,
                        false,
                        new RoomCoordinates(0, 0, 0)
                ).isSameRoom(0, 1, 0)
        );
    }

    @Test
    void testRandomDifferentCoordsOnWallNorthAndSouthOnly() {
        assertTrue(
                randomDifferentCoordsOnWall(
                        new RoomCoordinates(1, 1, 3),
                        0,
                        true,
                        new RoomCoordinates(0, 0, 0)
                ).isSameRoom(0, 0, 2)
        );
    }

    @Test
    void testIsSameAsAnyTrue() {
        assertTrue(isSameAsAny(
                new RoomCoordinates(0, 0, 0),
                new RoomCoordinates(0, 1, 0), new RoomCoordinates(0, 0, 0)
        ));
    }

    @Test
    void testIsSameAsAnyFalse() {
        assertFalse(isSameAsAny(
                new RoomCoordinates(0, 0, 0),
                new RoomCoordinates(0, 1, 0), new RoomCoordinates(0, 0, 1)
        ));
    }

    @Test
    void testRandomCoords() {
        final RoomCoordinates coords =
                randomCoords(new RoomCoordinates(1, 1, 2));
        assertTrue(coords.isSameRoom(0, 0, 0) || coords.isSameRoom(0, 0, 1));
    }

    @Test
    void testRandomCoordsOnWallAllWalls() {
        assertFalse(
                randomCoordsOnWall(
                        new RoomCoordinates(1, 3, 3), 0
                ).isSameRoom(0, 1, 1)
        );
    }

    @Test
    void testRandomCoordsOnWallNorthAndSouthOnly() {
        assertFalse(
                randomCoordsOnWall(
                        new RoomCoordinates(1, 1, 3),
                        0,
                        true
                ).isSameRoom(0, 0, 1)
        );
    }

    private void assertIsOnWall(final RoomCoordinates theDimensions,
                                final RoomCoordinates theCoords) {
        assertTrue(
                theCoords.getX() == 0 ||
                theCoords.getX() == theDimensions.getX() - 1 ||
                theCoords.getY() == 0 ||
                theCoords.getY() == theDimensions.getY() - 1
        );
    }
}
