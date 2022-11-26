package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Dungeon implements Serializable {

    private static final String UNKNOWN_ROOM = createUnknownRoomString('~');
    // left/top wall + width/height of room contents + right/bottom wall
    private static final int TOTAL_ROOM_SIZE = Room.ROOM_SIZE + 2;

    private final Room[][][] myRooms;
    private final Map myMap;

    private final RoomCoordinates[] myStairs;
    private final RoomCoordinates[] myTerminalPoints;

    /**
     * Creates a new Dungeon of Rooms with the provided number of floors, rows,
     * and columns, including a Map of the same size.
     *
     * Each room can have a Monster, a Trap, or neither (but not both).
     * The probability of a Room having a Monster is theMonsterChancePerRoom.
     * The probability of a Room having a Trap is
     * (1 - theMonsterChancePerRoom) * theTrapChancePerRoom.
     * The probability of a Room having neither a Monster nor a Trap is
     * (1 - theMonsterChancePerRoom) * (1 - theTrapChancePerRoom)
     *
     * The probability of a Room having an Item is theItemChancePerRoom.
     *
     * @param theDimensions The floor, row, and column count for the Dungeon.
     * @param theMonsterChancePerRoom The probability of a Room having a Monster
     * @param theTrapChancePerRoom The probability of a Room having a Trap,
     *                             given that the Room does not have a Monster
     * @param theItemChancePerRoom The probability of a Room having an Item
     */
    Dungeon(final RoomCoordinates theDimensions,
            final double theMonsterChancePerRoom,
            final double theTrapChancePerRoom,
            final double theItemChancePerRoom) {
        myStairs = MazeGenerator.generateStairs(theDimensions);
        myTerminalPoints = new RoomCoordinates[2];
        myRooms = MazeGenerator.generateMaze(
                theDimensions,
                theMonsterChancePerRoom,
                theTrapChancePerRoom,
                theItemChancePerRoom,
                myTerminalPoints
        );

        myMap = new Map(theDimensions);
    }

    private static String createUnknownRoomString(final char theUnknown) {
        final String unknownRoomRow = ("" + theUnknown)
                                      .repeat(TOTAL_ROOM_SIZE);

        return (unknownRoomRow + '\n')
               .repeat(TOTAL_ROOM_SIZE - 1) + // No LF after last row
               unknownRoomRow;
    }

    public String toString() {
        return view(false);
    }

    String view(final boolean theHideUnknown) {
        final StringBuilder dungeon = new StringBuilder();

        for (int i = 0; i < myRooms.length; i++) {
            dungeon.append("Floor ").append(i).append(":\n");
            for (int j = 0; j < myRooms[0].length; j++) {
                appendRow(dungeon, i, j, theHideUnknown);
            }
            dungeon.append('\n');
        }

        return dungeon.toString();
    }

    Room getRoom(final RoomCoordinates theCoords) {
        return myRooms[theCoords.getFloor()]
                      [theCoords.getX()]
                      [theCoords.getY()];
    }

    Map getMap() {
        return myMap;
    }

    RoomCoordinates[] getStairs() {
        return myStairs.clone();
    }

    RoomCoordinates getEntrance() {
        return myTerminalPoints[0];
    }

    RoomCoordinates getExit() {
        return myTerminalPoints[1];
    }

    private void appendRow(final StringBuilder theStringBuilder,
                           final int theFloor,
                           final int theRow,
                           final boolean theHideUnknown) {
        final String[][] rooms = Arrays.stream(viewRowAsArray(
                theFloor, theRow, theHideUnknown
        )).map((s) -> s.split("\n")).toArray(String[][]::new);

        for (int i = 0; i < TOTAL_ROOM_SIZE; i++) {
            for (String[] room : rooms) {
                theStringBuilder.append(room[i]);
            }
            theStringBuilder.append('\n');
        }
    }

    private String[] viewRowAsArray(final int theFloor,
                                    final int theRow,
                                    final boolean theHideUnknown) {
        final String[] rooms = new String[myRooms[0][0].length];

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = (
                    theHideUnknown &&
                    !myMap.isExplored(theFloor, theRow, i)
            ) ?
                    UNKNOWN_ROOM :
                    myRooms[theFloor][theRow][i].toString();
        }

        return rooms;
    }

    private static class MazeGenerator {

        private static final int[] NO_UNVISITED_NEIGHBORS = new int[0];

        private static RoomCoordinates[] generateStairs(final RoomCoordinates theDimensions) {
            // 1 flight between each pair of consecutive floors
            // (FloorStairsFloor...FloorStairsFloor)
            final RoomCoordinates[] stairs = new RoomCoordinates[
                    theDimensions.getFloor() - 1
            ];
            for (int i = 0; i < theDimensions.getFloor(); i++) {
                stairs[i] = randomCoordsOnWall(theDimensions, i);
            }

            return stairs;
        }

        private static Room[][][] generateMaze(final RoomCoordinates theDimensions,
                                               final double theMonsterChance,
                                               final double theTrapChance,
                                               final double theItemChance,
                                               final RoomCoordinates[] theTerminalPoints) {
            final Room[][][] maze = new Room[theDimensions.getFloor()]
                                            [theDimensions.getX()]
                                            [theDimensions.getY()];

            addTerminalPointsAndPillars(theDimensions, maze, theTerminalPoints);
            for (int i = 0; i < theDimensions.getFloor(); i++) {
                addFloor(
                        maze, theDimensions, i,
                        theMonsterChance, theTrapChance, theItemChance
                );
            }

            return maze;
        }

        private static void addFloor(final Room[][][] theMaze,
                                     final RoomCoordinates theDimensions,
                                     final int theFloor,
                                     final double theMonsterChance,
                                     final double theTrapChance,
                                     final double theItemChance) {
            final boolean[][][] layout = generateFloorLayout(
                    theDimensions, theFloor
            );

            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[0].length; j++) {
                    if (theMaze[theFloor][i][j] == null) {
                        theMaze[theFloor][i][j] = randomRoom(
                                layout[i][j],
                                theMonsterChance,
                                theTrapChance,
                                theItemChance
                        );
                    }
                }
            }
        }

        private static boolean[][][] generateFloorLayout(final RoomCoordinates theDimensions,
                                                         final int theFloor) {
            // Direction.values().length should always be 4 (used in place of a
            // new named constant in this class)
            final boolean[][][] doors = new boolean[theDimensions.getX()]
                                                   [theDimensions.getY()]
                                                   [Direction.values().length];

            final boolean[][] visited = new boolean[theDimensions.getX()]
                                                   [theDimensions.getY()];
            final Stack<int[]> dfsCells = new Stack<>();
            int[] currentCell, nextCell;

            visitStartingCell(visited, dfsCells, theDimensions, theFloor);
            while (!dfsCells.empty()) {
                currentCell = dfsCells.pop();

                nextCell = randomUnvisitedNeighbor(visited, currentCell);
                if (nextCell != NO_UNVISITED_NEIGHBORS) {
                    dfsCells.push(currentCell);

                    addDoorsBetween(doors, currentCell, nextCell);
                    visited[nextCell[0]][nextCell[1]] = true;
                    dfsCells.push(nextCell);
                }
            }

            return doors;
        }

        private static void visitStartingCell(final boolean[][] theVisited,
                                              final Stack<int[]> theDfsCells,
                                              final RoomCoordinates theDimensions,
                                              final int theFloor) {
            final RoomCoordinates start = randomCoordsOnWall(
                    theDimensions, theFloor
            );
            theVisited[start.getX()][start.getY()] = true;
            theDfsCells.push(new int[]{start.getX(), start.getY()});
        }

        private static int[] randomUnvisitedNeighbor(final boolean[][] theVisited,
                                                     final int[] theCell) {
            final ArrayList<int[]> unvisited = new ArrayList<>(
                    Direction.values().length - 1
            ); // Only 3 possible unvisited for all cells (first cell on wall)

            if (isValidAndUnvisited(theVisited, theCell[0], theCell[1] - 1)) {
                unvisited.add(new int[]{theCell[0], theCell[1] - 1});
            }
            if (isValidAndUnvisited(theVisited, theCell[0], theCell[1] + 1)) {
                unvisited.add(new int[]{theCell[0], theCell[1] + 1});
            }
            if (isValidAndUnvisited(theVisited, theCell[0] - 1, theCell[1])) {
                unvisited.add(new int[]{theCell[0] - 1, theCell[1]});
            }
            if (isValidAndUnvisited(theVisited, theCell[0] + 1, theCell[1])) {
                unvisited.add(new int[]{theCell[0] + 1, theCell[1]});
            }

            return unvisited.size() == 0 ?
                   NO_UNVISITED_NEIGHBORS :
                   unvisited.get(Util.randomIntExc(unvisited.size()));
        }

        private static boolean isValidAndUnvisited(final boolean[][] theVisited,
                                                   final int theX, final int theY) {
            return theX >= 0 && theX < theVisited.length &&
                   theY >= 0 && theY < theVisited[0].length &&
                   !theVisited[theX][theY];
        }

        private static void addDoorsBetween(final boolean[][][] theDoors,
                                            final int[] theFirstCell,
                                            final int[] theSecondCell) {
            final boolean[] firstDoors = theDoors[theFirstCell[0]]
                                                 [theFirstCell[1]];
            final boolean[] secondDoors = theDoors[theSecondCell[0]]
                                                  [theSecondCell[1]];

            addHorizontalDoors(
                    firstDoors, secondDoors,
                    theFirstCell, theSecondCell
            );
            addVerticalDoors(
                    firstDoors, secondDoors,
                    theFirstCell, theSecondCell
            );
        }

        private static void addHorizontalDoors(final boolean[] theFirstDoors,
                                               final boolean[] theSecondDoors,
                                               final int[] theFirstCell,
                                               final int[] theSecondCell) {
            final int xDiff = theFirstCell[0] - theSecondCell[0];
            if (xDiff == 1) { // Second is west of first
                theFirstDoors[Direction.WEST.ordinal()] = true;
                theSecondDoors[Direction.EAST.ordinal()] = true;
            } else if (xDiff == -1) { // Second is east of first
                theFirstDoors[Direction.EAST.ordinal()] = true;
                theSecondDoors[Direction.WEST.ordinal()] = true;
            }
        }

        private static void addVerticalDoors(final boolean[] theFirstDoors,
                                             final boolean[] theSecondDoors,
                                             final int[] theFirstCell,
                                             final int[] theSecondCell) {
            final int yDiff = theFirstCell[1] - theSecondCell[1];
            if (yDiff == 1) { // Second is north of first
                theFirstDoors[Direction.NORTH.ordinal()] = true;
                theSecondDoors[Direction.SOUTH.ordinal()] = true;
            } else if (yDiff == -1) { // Second is south of first
                theFirstDoors[Direction.SOUTH.ordinal()] = true;
                theSecondDoors[Direction.NORTH.ordinal()] = true;
            }
        }

        private static Room randomRoom(final boolean[] theDoors,
                                       final double theMonsterChance,
                                       final double theTrapChance,
                                       final double theItemChance) {
            Monster monster = null;
            Trap trap = null;
            if (Util.probabilityTest(theMonsterChance)) {
//                monster = MonsterFactory.createRandomMonster();
            } else if (Util.probabilityTest(theTrapChance)) {
                trap = TrapFactory.createRandomTrap();
            }

            return new Room(
                    toDirections(theDoors),
                    trap,
                    monster,
                    false,
                    false,
                    Util.probabilityTest(theItemChance) ?
                            new Item[]{randomItem()} :
                            new Item[0]
            );
        }

        private static Direction[] toDirections(final boolean[] theDoors) {
            final ArrayList<Direction> doorDirections = new ArrayList<>(
                    theDoors.length
            );

            if (theDoors[Direction.NORTH.ordinal()]) {
                doorDirections.add(Direction.NORTH);
            }
            if (theDoors[Direction.SOUTH.ordinal()]) {
                doorDirections.add(Direction.SOUTH);
            }
            if (theDoors[Direction.WEST.ordinal()]) {
                doorDirections.add(Direction.WEST);
            }
            if (theDoors[Direction.EAST.ordinal()]) {
                doorDirections.add(Direction.EAST);
            }

            return doorDirections.toArray(new Direction[0]);
        }

        private static Item randomItem() {
            // Health potion, vision potion, planks, and buff potions
            final int selected = Util.randomIntExc(
                    3 + BuffType.positiveTypeCount()
            );

            return switch (selected) {
                case 0 -> new HealthPotion(1);
                case 1 -> new VisionPotion(1);
                case 2 -> new Planks(1);
                default -> new BuffPotion(
                        1, BuffType.randomPositiveBuffType()
                );
            };
        }

        private static void addTerminalPointsAndPillars(final RoomCoordinates theDimensions,
                                                        final Room[][][] theMaze,
                                                        final RoomCoordinates[] theTerminalPoints) {
            final Pillar[] pillars = Pillar.createPillars();
            final RoomCoordinates[] terminalPointsAndPillars = randomTerminalPointsAndPillars(
                    theDimensions, pillars.length
            );
            theTerminalPoints[0] = terminalPointsAndPillars[0];
            theTerminalPoints[1] = terminalPointsAndPillars[1];

            addEmptyRoom(theMaze, terminalPointsAndPillars[0], true, false);
            addEmptyRoom(theMaze, terminalPointsAndPillars[1], false, true);
            for (int i = 2; i < terminalPointsAndPillars.length; i++) {
                addEmptyRoom(theMaze,
                        terminalPointsAndPillars[i],
                        false, false,
                        pillars[i - 2]
                );
            }
        }

        private static void addEmptyRoom(final Room[][][] theMaze,
                                         final RoomCoordinates theCoords,
                                         final boolean theIsEntrance,
                                         final boolean theIsExit,
                                         final Item ... theItems) {
            theMaze[theCoords.getFloor()]
                   [theCoords.getX()]
                   [theCoords.getY()] = new Room(
                           Direction.values(),
                           null, null,
                           theIsEntrance, theIsExit,
                           theItems
            );
        }

        private static RoomCoordinates[] randomTerminalPointsAndPillars(
                final RoomCoordinates theDimensions,
                final int thePillarCount) {
            // Entrance, exit, then pillars
            final RoomCoordinates[] rooms = new RoomCoordinates[2 + thePillarCount];

            rooms[0] = randomCoordsOnWall( // Entrance
                    theDimensions, 0
            );
            rooms[1] = randomDifferentCoordsOnWall( // Exit
                    theDimensions, theDimensions.getFloor() - 1, rooms[0]
            );
            for (int i = 2; i < rooms.length; i++) { // Pillars
                rooms[i] = randomDifferentCoords(theDimensions, rooms);
            }

            return rooms;
        }

        private static RoomCoordinates randomDifferentCoords(final RoomCoordinates theDimensions,
                                                             final RoomCoordinates ... theOtherCoords) {
            RoomCoordinates newCoords;
            do {
                newCoords = randomCoords(theDimensions);
            } while (isSameAsAny(newCoords, theOtherCoords));

            return newCoords;
        }

        private static RoomCoordinates randomDifferentCoordsOnWall(final RoomCoordinates theDimensions,
                                                                   final int theFloor,
                                                                   final RoomCoordinates ... theOtherCoords) {
            RoomCoordinates newCoords;
            do {
                newCoords = randomCoordsOnWall(theDimensions, theFloor);
            } while (isSameAsAny(newCoords, theOtherCoords));

            return newCoords;
        }

        private static boolean isSameAsAny(final RoomCoordinates theCoords,
                                           final RoomCoordinates ... theOtherCoords) {
            for (RoomCoordinates other : theOtherCoords) {
                if (other != null && theCoords.isSameRoom(other)) {
                    return true;
                }
            }
            return false;
        }

        private static RoomCoordinates randomCoords(final RoomCoordinates theDimensions) {
            return new RoomCoordinates(
                    Util.randomIntExc(theDimensions.getFloor()),
                    Util.randomIntExc(theDimensions.getX()),
                    Util.randomIntExc(theDimensions.getY())
            );
        }

        private static RoomCoordinates randomCoordsOnWall(final RoomCoordinates theDimensions,
                                                          final int theFloor) {
            return switch (Util.randomIntExc(4)) {
                // North wall
                case 0 -> new RoomCoordinates(
                        theFloor,
                        Util.randomIntExc(theDimensions.getX()),
                        0
                );
                // South wall
                case 1 -> new RoomCoordinates(
                        theFloor,
                        Util.randomIntExc(theDimensions.getX()),
                        theDimensions.getY() - 1
                );
                // West wall
                case 2 -> new RoomCoordinates(
                        theFloor,
                        0,
                        Util.randomIntExc(theDimensions.getY())
                );
                // East wall
                default -> new RoomCoordinates(
                        theFloor,
                        theDimensions.getX() - 1,
                        Util.randomIntExc(theDimensions.getY())
                );
            };
        }
    }
}
