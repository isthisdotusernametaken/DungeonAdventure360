package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class ArrayDungeon extends Dungeon {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 6134960628901176398L;

    /**
     * Double value representing the chance of a monster spawn per room.
     */
    private static final double MONSTER_CHANCE_PER_ROOM = 0.3;

    /**
     * Double value representing the chance of a trap spawn per room.
     */
    private static final double TRAP_CHANCE_PER_ROOM = 0.2;

    /**
     * Double value representing the chance of an item spawn per room.
     */
    private static final double ITEM_CHANCE_PER_ROOM = 0.4;

    /**
     * String representing the unknown rooms.
     */
    private static final String UNKNOWN_ROOM = createUnknownRoomString();
    // left/top wall + width/height of room contents + right/bottom wall

    /**
     * Integer value representing the total room size.
     */
    private static final int TOTAL_ROOM_SIZE = Room.ROOM_SIZE + 2;

    /**
     * Integer value representing half of the room size.
     */
    private static final int HALF_TOTAL_ROOM_SIZE = TOTAL_ROOM_SIZE / 2;

    /**
     * The 3D array representing the rooms in the dungeon.
     */
    private final Room[][][] myRooms;

    /**
     * The stairs in the dungeon where adventurer can go up/down.
     */
    private final RoomCoordinates[] myStairs;

    /**
     * The terminal points in the dungeon.
     */
    private final RoomCoordinates[] myTerminalPoints;

    /**
     * Room-coordinates representing the dimensions of the room.
     */
    private final RoomCoordinates myDimensions;

    /**
     * Creates a new Dungeon of Rooms with the provided number of floors, rows,
     * and columns, including a Map of the same size.
     * <p>
     * Each room can have a Monster, a Trap, or neither (but not both).
     * The probability of a Room having a Monster is monsterChancePerRoom.
     * The probability of a Room having a Trap is
     * (1 - monsterChancePerRoom) * trapChancePerRoom.
     * The probability of a Room having neither a Monster nor a Trap is
     * (1 - monsterChancePerRoom) * (1 - trapChancePerRoom)
     * <p>
     * The probability of a Room having an Item is independent of Monster and
     * Trap probabilities.
     *
     * @param theDifficulty Used to set the parameters of the dungeon
     */
    ArrayDungeon(final Difficulty theDifficulty) {
        super(MapFactory.create(theDifficulty.getDimensions()));

        final RoomCoordinates dimensions = theDifficulty.getDimensions();
        myStairs = MazeGenerator.generateStairs(dimensions);
        myTerminalPoints = new RoomCoordinates[2];
        myRooms = MazeGenerator.generateMaze(
                dimensions,
                theDifficulty.modifyNegative(MONSTER_CHANCE_PER_ROOM),
                theDifficulty.modifyNegative(TRAP_CHANCE_PER_ROOM),
                theDifficulty.modifyPositive(ITEM_CHANCE_PER_ROOM),
                theDifficulty,
                myTerminalPoints
        );

        myDimensions = new RoomCoordinates(
                myRooms.length, myRooms[0].length, myRooms[0][0].length
        );
    }

    /**
     * Constructs the unknown rooms in the dungeon.
     *
     * @return The string representing the unknown rooms.
     */
    private static String createUnknownRoomString() {
        final String unknownRoomRow = ("" + UNKNOWN)
                                      .repeat(TOTAL_ROOM_SIZE);

        return (unknownRoomRow + '\n')
               .repeat(TOTAL_ROOM_SIZE - 1) + // No LF after last row
               unknownRoomRow;
    }

    /**
     * ToString method to check if adventurer not shown and to compare
     * to the current room coordinates.
     *
     * @return The string representing the room coordinates
     */
    @Override
    public String toString() {
        // Coords to make sure Adventurer not shown. Okay because only ever
        // used to compare to current room coords
        return toString(new RoomCoordinates(-1, -1, -1), false);
    }

    /**
     * ToString method to format and display the dungeon's rooms.
     *
     * @param theAdventurerCoords The coordinates of the adventurer.
     * @param theHideUnknown      The boolean true or false if hide is unknown.
     * @return                    The string representing the dungeon's rooms.
     */
    @Override
    String toString(final RoomCoordinates theAdventurerCoords,
                    final boolean theHideUnknown) {
        final StringBuilder dungeon = new StringBuilder();

        for (int i = 0; i < myRooms.length; i++) {
            dungeon.append("Floor ").append(i + 1).append(":\n");

            appendStairs(dungeon, i, true, theHideUnknown);
            for (int j = 0; j < myRooms[0].length; j++) {
                appendRow(dungeon, i, j, theAdventurerCoords, theHideUnknown);
            }
            appendStairs(dungeon, i, false, theHideUnknown);

            dungeon.append('\n');
        }

        return dungeon.toString();
    }

    /**
     * Gets the dimension of the dungeon's rooms.
     *
     * @return The dimension of the dungeon's rooms.
     */
    @Override
    RoomCoordinates getDimensions() {
        return myDimensions;
    }

    /**
     * Gets the coordinates of the dungeon's room
     *
     * @param theCoords The room coordinates to access and obtain
     *                  its dimension.
     * @return          The room coordinates.
     */
    @Override
    Room getRoom(final RoomCoordinates theCoords) {
        return myRooms[theCoords.getFloor()]
                      [theCoords.getX()]
                      [theCoords.getY()];
    }

    /**
     * Checks if the room has a stair going up.
     *
     * @param theCoords The room coordinates to access and obtains
     *                  its dimension.
     * @return          The boolean true or false if there is
     *                  a stair going up in the current room.
     */
    @Override
    boolean hasStairsUp(final RoomCoordinates theCoords) {
        return theCoords.getFloor() != 0 &&
               theCoords.isOneBelow(myStairs[theCoords.getFloor() - 1]);
    }

    /**
     * Checks if the room has a stair going down.
     *
     * @param theCoords The room coordinates to access and obtains
     *                  its dimension.
     * @return          The boolean true or false if there is a
     *                  stair going down in the current room.
     */
    @Override
    boolean hasStairsDown(final RoomCoordinates theCoords) {
        return theCoords.getFloor() != myDimensions.getFloor() - 1 &&
               theCoords.isSameRoom(myStairs[theCoords.getFloor()]);
    }

    /**
     * Gets the terminal point of the dungeon.
     *
     * @return The coordinates of the entrance.
     */
    @Override
    RoomCoordinates getEntrance() {
        return myTerminalPoints[0];
    }

    /**
     * Appends or adds stairs to the dungeon's rooms.
     *
     * @param theBuilder     The string builder to construct and append
     *                       stairs in the dungeon's rooms.
     * @param theFloor       The integer to get the floor location.
     * @param theNorth       The boolean true or false if the stair is
     *                       at northside.
     * @param theHideUnknown The boolean true or false if hide is unknown.
     */
    private void appendStairs(final StringBuilder theBuilder,
                              final int theFloor,
                              final boolean theNorth,
                              final boolean theHideUnknown) {
        final int targetY = theNorth ? 0 : myDimensions.getY() - 1;
        final boolean hasUpStairs = hasKnownStairs(
                0, theFloor, targetY, theFloor - 1,
                theHideUnknown
        );
        final boolean hasDownStairs = hasKnownStairs(
                myDimensions.getFloor() - 1, theFloor, targetY, theFloor,
                theHideUnknown
        );

        if (hasUpStairs && hasDownStairs) {
            if (myStairs[theFloor - 1].getX() < myStairs[theFloor].getX()) {
                appendTwoStairs(
                        theBuilder,
                        theFloor - 1, theFloor, UP_STAIRS, DOWN_STAIRS
                );
            } else {
                appendTwoStairs(
                        theBuilder,
                        theFloor, theFloor - 1, DOWN_STAIRS, UP_STAIRS
                );
            }
        } else if (hasUpStairs) {
            appendStairs(theBuilder, theFloor - 1, UP_STAIRS, true);
        } else if (hasDownStairs) {
            appendStairs(theBuilder, theFloor, DOWN_STAIRS, true);
        }
    }

    /**
     *
     *
     * @param theInvalidFloor
     * @param theFloor
     * @param theTargetY
     * @param theStairsIndex
     * @param theHideUnknown
     * @return
     */
    private boolean hasKnownStairs(final int theInvalidFloor,
                                   final int theFloor,
                                   final int theTargetY,
                                   final int theStairsIndex,
                                   final boolean theHideUnknown) {
        return theFloor != theInvalidFloor &&
               myStairs[theStairsIndex].getY() == theTargetY &&
               (
                       !theHideUnknown ||
                       getMap().isExplored(myStairs[theStairsIndex])
               );
    }

    private void appendTwoStairs(final StringBuilder theBuilder,
                                 final int theFirstFloor,
                                 final int theSecondFloor,
                                 final char theFirstFloorChar,
                                 final char theSecondFloorChar) {
        appendStairs(
                theBuilder, theFirstFloor, theFirstFloorChar, false
        );

        appendSpaces(
                theBuilder,
                (
                        myStairs[theSecondFloor].getX() - myStairs[theFirstFloor].getX()
                ) * TOTAL_ROOM_SIZE - (TOTAL_ROOM_SIZE % 2)
        );
        theBuilder.append(theSecondFloorChar).append('\n');
    }

    private void appendStairs(final StringBuilder theBuilder,
                              final int theFloor,
                              final char theStairsChar,
                              final boolean theAppendNewLine) {
        appendSpaces(
                theBuilder,
                myStairs[theFloor].getX() * TOTAL_ROOM_SIZE +
                        HALF_TOTAL_ROOM_SIZE
        );

        theBuilder.append(theStairsChar);

        if (theAppendNewLine) {
            theBuilder.append('\n');
        }
    }

    private void appendSpaces(final StringBuilder theBuilder,
                              final int theSpaces) {
        theBuilder.append(" ".repeat(Math.max(0, theSpaces)));
    }

    private void appendRow(final StringBuilder theBuilder,
                           final int theFloor,
                           final int theRow,
                           final RoomCoordinates theAdventurerCoords,
                           final boolean theHideUnknown) {
        final String[][] rooms = Arrays.stream(viewRowAsArray(
                theFloor, theRow, theAdventurerCoords, theHideUnknown
        )).map(s -> s.split("\n")).toArray(String[][]::new);

        for (int i = 0; i < TOTAL_ROOM_SIZE; i++) {
            for (String[] room : rooms) {
                theBuilder.append(room[i]);
            }
            theBuilder.append('\n');
        }
    }

    private String[] viewRowAsArray(final int theFloor,
                                    final int theRow,
                                    final RoomCoordinates theAdventurerCoords,
                                    final boolean theHideUnknown) {
        final String[] rooms = new String[myRooms[0][0].length];

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = (
                    theHideUnknown &&
                    !getMap().isExplored(theFloor, i, theRow)
            ) ?
                    UNKNOWN_ROOM :
                    myRooms[theFloor][i][theRow].toString(
                            theAdventurerCoords != null &&
                            theAdventurerCoords.isSameRoom(theFloor, i, theRow)
                    );
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

            if (stairs.length > 0) {
                stairs[0] = randomCoordsOnWall(theDimensions, 0, true);
            }
            for (int i = 1; i < stairs.length; i++) {
                stairs[i] = randomDifferentCoordsOnWall(
                        theDimensions, i, true, stairs[i - 1]
                );
            }

            return stairs;
        }

        private static Room[][][] generateMaze(final RoomCoordinates theDimensions,
                                               final double theMonsterChance,
                                               final double theTrapChance,
                                               final double theItemChance,
                                               final Difficulty theDifficulty,
                                               final RoomCoordinates[] theTerminalPoints) {
            final Room[][][] maze = new Room[theDimensions.getFloor()]
                                            [theDimensions.getX()]
                                            [theDimensions.getY()];

            addTerminalPointsAndPillars(theDimensions, maze, theTerminalPoints);
            for (int i = 0; i < theDimensions.getFloor(); i++) {
                addFloor(
                        maze, theDimensions, i,
                        theMonsterChance, theTrapChance, theItemChance,
                        theDifficulty
                );
            }

            return maze;
        }

        private static void addFloor(final Room[][][] theMaze,
                                     final RoomCoordinates theDimensions,
                                     final int theFloor,
                                     final double theMonsterChance,
                                     final double theTrapChance,
                                     final double theItemChance,
                                     final Difficulty theDifficulty) {
            final boolean[][][] layout = generateFloorLayout(
                    theDimensions, theFloor
            );

            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[0].length; j++) {
                    addRoom(
                            theMaze,
                            theFloor, i, j,
                            layout[i][j],
                            theMonsterChance, theTrapChance, theItemChance,
                            theDifficulty
                    );
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

        private static void addRoom(final Room[][][] theMaze,
                                    final int theFloor,
                                    final int theX,
                                    final int theY,
                                    final boolean[] theDoors,
                                    final double theMonsterChance,
                                    final double theTrapChance,
                                    final double theItemChance,
                                    final Difficulty theDifficulty) {
            final Room room = theMaze[theFloor][theX][theY];

            theMaze[theFloor][theX][theY] = room == null ?
                    randomRoom(
                            theDoors,
                            theMonsterChance,
                            theTrapChance,
                            theItemChance,
                            theDifficulty
                    ) :
                    new Room(
                            theDoors,
                            null,
                            null,
                            room.isEntrance(),
                            room.isExit(),
                            room.getContainer().viewItems()
                    );
        }

        private static Room randomRoom(final boolean[] theDoors,
                                       final double theMonsterChance,
                                       final double theTrapChance,
                                       final double theItemChance,
                                       final Difficulty theDifficulty) {
            Monster monster = null;
            Trap trap = null;
            if (Util.probabilityTest(theMonsterChance)) {
                monster = MonsterFactory.getInstance().createRandom(
                        theDifficulty
                );
            } else if (Util.probabilityTest(theTrapChance)) {
                trap = TrapFactory.getInstance().createRandom(
                        theDifficulty
                );
            }

            return new Room(
                    theDoors,
                    trap,
                    monster,
                    false,
                    false,
                    Util.probabilityTest(theItemChance) ?
                            new Item[]{ItemFactory.createRandom()} :
                            new Item[0]
            );
        }

        private static void addTerminalPointsAndPillars(
                final RoomCoordinates theDimensions,
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
                           new boolean[4],
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
                    theDimensions, theDimensions.getFloor() - 1, false,
                    rooms[0]
            );
            for (int i = 2; i < rooms.length; i++) { // Pillars
                rooms[i] = randomDifferentCoords(theDimensions, rooms);
            }

            return rooms;
        }

        private static RoomCoordinates randomDifferentCoords(
                final RoomCoordinates theDimensions,
                final RoomCoordinates ... theOtherCoords) {
            RoomCoordinates newCoords;
            do {
                newCoords = randomCoords(theDimensions);
            } while (isSameAsAny(newCoords, theOtherCoords));

            return newCoords;
        }

        private static RoomCoordinates randomDifferentCoordsOnWall(
                final RoomCoordinates theDimensions,
                final int theFloor,
                final boolean theNorthAndSouthOnly,
                final RoomCoordinates theOtherCoords) {
            RoomCoordinates newCoords;
            do {
                newCoords = randomCoordsOnWall(
                        theDimensions, theFloor, theNorthAndSouthOnly
                );
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
            return randomCoordsOnWall(theDimensions, theFloor, false);
        }

        private static RoomCoordinates randomCoordsOnWall(final RoomCoordinates theDimensions,
                                                          final int theFloor,
                                                          final boolean theNorthAndSouthOnly) {
            final int directionCount = Direction.values().length;
            return switch (Util.randomIntExc(
                    theNorthAndSouthOnly ? directionCount / 2 : directionCount
                )) {
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
