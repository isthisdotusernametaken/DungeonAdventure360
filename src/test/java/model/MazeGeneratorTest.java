package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static model.ArrayDungeon.MazeGenerator.*;
import static model.Difficulty.*;

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


}
