/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * This class creates, modifies, and handles the dungeon of rooms for the
 * dungeon adventure game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
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
     * Integer value representing the total room size in characters.
     */
    private static final int TOTAL_ROOM_SIZE = Room.ROOM_SIZE + 2;

    /**
     * Integer value representing half of the room size in characters.
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
     * The entrance and exit coords.
     */
    private final RoomCoordinates[] myTerminalPoints;

    /**
     * Room-coordinates representing the dimensions of the dungeon.
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
     * Constructs the representation of unknown rooms in the dungeon.
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
     * Constructs a String representing the entire dungeon in its current
     * state, including the stairs and each room and its contents.
     *
     * @return The string representing the dungeon
     */
    @Override
    public String toString() {
        // Coords to make sure Adventurer not shown. Okay because only ever
        // used to compare to current room coords
        return toString(new RoomCoordinates(-1, -1, -1), false);
    }

    /**
     * Constructs a String representing the entire dungeon in its current
     * state, including the stairs and each room and its contents.
     *
     * @param theAdventurerCoords The room the adventurer is in.
     * @param theHideUnknown Whether unknown rooms should have their contents
     *                       hidden.
     * @return The string representing the dungeon
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
     * Gets the size of the dungeon.
     *
     * @return The dungeon's size.
     */
    @Override
    RoomCoordinates getDimensions() {
        return myDimensions;
    }

    /**
     * Gets the room at the specified coords
     *
     * @param theCoords The coords of the room to retrieve.
     * @return The specified room.
     */
    @Override
    Room getRoom(final RoomCoordinates theCoords) {
        return myRooms[theCoords.getFloor()]
                      [theCoords.getX()]
                      [theCoords.getY()];
    }

    /**
     * Checks if the specified room has stairs going up.
     *
     * @param theCoords The coords of the room to check for stairs in.
     * @return Whether there are stairs going up in the current room.
     */
    @Override
    boolean hasStairsUp(final RoomCoordinates theCoords) {
        return theCoords.getFloor() != 0 &&
               theCoords.isOneBelow(myStairs[theCoords.getFloor() - 1]);
    }

    /**
     * Checks if the specified room has stairs going down.
     *
     * @param theCoords The coords of the room to check for stairs in.
     * @return Whether there are stairs going down in the current room.
     */
    @Override
    boolean hasStairsDown(final RoomCoordinates theCoords) {
        return theCoords.getFloor() != myDimensions.getFloor() - 1 &&
               theCoords.isSameRoom(myStairs[theCoords.getFloor()]);
    }

    /**
     * Gets the entry point of the dungeon.
     *
     * @return The coordinates of the entrance.
     */
    @Override
    RoomCoordinates getEntrance() {
        return myTerminalPoints[0];
    }

    /**
     * Appends any existing stairs to the dungeon's string representation.
     *
     * @param theBuilder     The string builder to construct and append
     *                       stairs in the dungeon's rooms.
     * @param theFloor       The floor to check for stairs in.
     * @param theNorth       Whether the stairs to possibly be appended are on
     *                       the north side of the floor.
     * @param theHideUnknown Whether unknown rooms should have their contents
     *                       hidden.
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
     * Checks if stairs should be appended based on whether the stairs exist
     * and whether the room with the stairs is known.
     *
     * @param theInvalidFloor The integer value representing the invalid floor
     *                        location.
     * @param theFloor        The integer value representing the floor
     *                        location.
     * @param theTargetY      The integer value representing the target
     *                        y-value for checking purpose.
     * @param theStairsIndex  The integer value representing the index of the
     *                        stair.
     * @param theHideUnknown Whether unknown rooms should have their contents
     *                       hidden.
     * @return                The boolean true or false if the stair is known.
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

    /**
     * Appends or adds two stairs to the dungeon's rooms.
     *
     * @param theBuilder            The string builder to construct and append
     *                              stairs in the dungeon's rooms.
     * @param theFirstFloor         The integer to get the first floor
     *                              location.
     * @param theSecondFloor        The integer to get the second floor
     *                              location.
     * @param theFirstFloorChar     The character representing the first
     *                              floor.
     * @param theSecondFloorChar    The character representing the second
     *                              floor.
     */
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

    /**
     * Appends or adds stairs to the dungeon's rooms.
     *
     * @param theBuilder        The string builder to construct and append
     *                          stairs in the dungeon's rooms.
     * @param theFloor          The integer to get the floor location.
     * @param theStairsChar     The character representing the stair.
     * @param theAppendNewLine  The boolean true or false if append new line
     *                          is needed.
     */
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

    /**
     * Appends spaces to the rooms.
     *
     * @param theBuilder    The string builder to construct and append
     *                      stairs in the dungeon's rooms.
     * @param theSpaces     The integer value representing the max
     *                      number of spaces allowed.
     */
    private void appendSpaces(final StringBuilder theBuilder,
                              final int theSpaces) {
        theBuilder.append(" ".repeat(Math.max(0, theSpaces)));
    }

    /**
     * Appends rows to the rooms.
     *
     * @param theBuilder            The string builder to construct and append
     *                              stairs in the dungeon's rooms.
     * @param theFloor              The integer to get the floor location.
     * @param theRow                The integer to get the number of rows.
     * @param theAdventurerCoords   The current coordination of the current
     *                              room.
     * @param theHideUnknown        The boolean true or false if hide is unknown.
     */
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

    /**
     * Accesses and views row as in array string.
     *
     * @param theFloor              The integer to get the floor location.
     * @param theRow                The integer to get the number of rows.
     * @param theAdventurerCoords   The current coordination of the current
     *                              room.
     * @param theHideUnknown        The boolean true or false if hide is unknown.
     * @return                      The string array representing the rows.
     */
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

    /**
     * This maze generator helps to generate and create the dungeon rooms.
     */
    private static class MazeGenerator {

        /**
         * Indicates that the current cell has no unvisited neighbors for DFS
         */
        private static final int[] NO_UNVISITED_NEIGHBORS = new int[0];

        /**
         * Generates the stairs for the dungeon rooms.
         *
         * @param theDimensions The dimensions of the dungeon rooms.
         * @return              The array of room coordinates containing
         *                      the stairs.
         */
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

        /**
         * Builds and populates an ArrayDungeon with Monsters, Traps, and Items
         *
         * @param theDimensions The size of the dungeon
         * @param theMonsterChance The chance of a room having a Monster
         * @param theTrapChance The chance of a room without a Monster having a
         *                      Trap
         * @param theItemChance The chance of a room to have an Item
         * @param theDifficulty The difficulty to use for making Monsters and
         *                      Traps
         * @param theTerminalPoints The array to place the entrance and exit
         *                          coords in
         * @return The contents of a new dungeon
         */
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

        /**
         * Generates and populates one floor of the dungeon and adds it to the
         * array.
         *
         * @param theMaze The array to place the floor in
         * @param theDimensions The size of the floor
         * @param theFloor The place in the array to place the floor in
         * @param theMonsterChance The chance of a room to have a Monster
         * @param theTrapChance The chance of a room without a Monster having a
         *                      Trap
         * @param theItemChance The chance of a room to have an Item
         * @param theDifficulty The difficulty to use for making Monsters and
         *                      Traps
         */
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

        /**
         * Uses a random DFS to generate the doors between the rooms in a
         * single-floor traversable maze.
         *
         * @param theDimensions The size of the floor
         * @param theFloor The floor to generate the layout for
         * @return A 2D array of boolean arrays representing the doors each
         *         room in a 2D grid has
         */
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

        /**
         * Marks the starting cell as visited and pushes it onto the stack to
         * start the DFS.
         *
         * @param theVisited The array to mark the cell as visited in
         * @param theDfsCells The stack to push the cell onto
         * @param theDimensions The size of the dungeon
         * @param theFloor The floor being generated
         */
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

        /**
         * Randomly selects a neighboring cell that has not been visited, or
         * indicates no such cell exists by returning NO_UNVISITED_NEIGHBORS.
         *
         * @param theVisited Which cells have been visited
         * @param theCell The current cell the search is in
         * @return An unvisited neighbor, or NO_UNVISITED_NEIGHBORS
         */
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

        /**
         * Indicates whether the specified cell is in bounds and has not been
         * visited.
         *
         * @param theVisited Which cells have been visited
         * @param theX The current cell's x-position
         * @param theY The current cell's y-position
         * @return Whether the specified cell can be visited by the search
         */
        private static boolean isValidAndUnvisited(final boolean[][] theVisited,
                                                   final int theX, final int theY) {
            return theX >= 0 && theX < theVisited.length &&
                   theY >= 0 && theY < theVisited[0].length &&
                   !theVisited[theX][theY];
        }

        /**
         * Sets the booleans marking which doors exist to true for the doors
         * between the two specified cells.
         *
         * @param theDoors All the doors in the floor
         * @param theFirstCell The first cell to add a door to
         * @param theSecondCell The second cell to add a door to
         */
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

        /**
         * Sets the booleans marking which doors exist to true for the doors
         * between the two specified cells if the cells are horizontally
         * adjacent.
         *
         * @param theFirstDoors The doors of the first cell
         * @param theSecondDoors The doors of the second cell
         * @param theFirstCell The first cell to add a door to
         * @param theSecondCell The second cell to add a door to
         */
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

        /**
         * Sets the booleans marking which doors exist to true for the doors
         * between the two specified cells if the cells are vertically
         * adjacent.
         *
         * @param theFirstDoors The doors of the first cell
         * @param theSecondDoors The doors of the second cell
         * @param theFirstCell The first cell to add a door to
         * @param theSecondCell The second cell to add a door to
         */
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

        /**
         * Creates and adds a new Room to the floor with the provided chances
         * of spawns and the provided doors, or with only the provided doors
         * and maybe pillar if the room is marked for an entrance, exit, or
         * pillar.
         *
         * @param theMaze The dungeon to add a room to
         * @param theFloor The floor to add a room to
         * @param theX The x-position to place the new room at
         * @param theY The y-position to place the new room at
         * @param theDoors The doors the new room should have
         * @param theMonsterChance The chance of a room to have a Monster
         * @param theTrapChance The chance of a room without a Monster having a
         *                      Trap
         * @param theItemChance The chance of a room to have an Item
         * @param theDifficulty The difficulty to use for making Monsters and
         *                      Traps
         */
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

        /**
         * Creates a random room that may have a Monster, Trap, or neither and
         * may have an Item.
         *
         * @param theDoors The doors the new room should have
         * @param theMonsterChance The chance of a room to have a Monster
         * @param theTrapChance The chance of a room without a Monster having a
         *                      Trap
         * @param theItemChance The chance of a room to have an Item
         * @param theDifficulty The difficulty to use for making Monsters and
         *                      Traps
         * @return The created room
         */
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

        /**
         * Adds empty rooms for the entrance, exit, and pillars
         *
         * @param theDimensions The size of the dungeon
         * @param theMaze The array of rooms in the dungeon
         * @param theTerminalPoints The array to place the entrance and exit in
         */
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

        /**
         * Creates an empty room for the entrance, exit, or a pillar
         *
         * @param theMaze The array of rooms in the dungeon
         * @param theCoords The coordinates to place the room at
         * @param theIsEntrance Whether the room is an entrance
         * @param theIsExit Whether the room is an exit
         * @param theItems The Pillar or no item
         */
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

        /**
         * Randomly chooses points on the walls for the entrance and exit and
         * then randomly chooses points for pillars.
         *
         * @param theDimensions The size of the dungeon
         * @param thePillarCount The number of pillars to generate spots for
         * @return The coords of the entrance, exit, and pillars
         */
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

        /**
         * Randomly selects a position, excluding the provided positions
         *
         * @param theDimensions The size of the dungeon
         * @param theOtherCoords The coords to exclude
         * @return A random position that is not one of the excluded spots
         */
        private static RoomCoordinates randomDifferentCoords(
                final RoomCoordinates theDimensions,
                final RoomCoordinates ... theOtherCoords) {
            RoomCoordinates newCoords;
            do {
                newCoords = randomCoords(theDimensions);
            } while (isSameAsAny(newCoords, theOtherCoords));

            return newCoords;
        }

        /**
         * Randomly selects a position on a wall, excluding the provided
         * position
         *
         * @param theDimensions The size of the dungeon
         * @param theFloor The floor to pick a location in
         * @param theNorthAndSouthOnly Whether only the north and south walls
         *                             should be considered
         * @param theOtherCoords The coords to exclude
         * @return A random position on a wall that is not one of the excluded
         *         spots
         */
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

        /**
         * Indicates whether the first provided coords match any of the other
         * provided coords
         *
         * @param theCoords The coords to check against the others
         * @param theOtherCoords The coords to check the first coords against
         * @return Whether the first coords are the same as any of the
         *         subsequent coords
         */
        private static boolean isSameAsAny(final RoomCoordinates theCoords,
                                           final RoomCoordinates ... theOtherCoords) {
            for (RoomCoordinates other : theOtherCoords) {
                if (other != null && theCoords.isSameRoom(other)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Randomly selects a position
         *
         * @param theDimensions The size of the dungeon
         * @return A random position in the dungeon
         */
        private static RoomCoordinates randomCoords(final RoomCoordinates theDimensions) {
            return new RoomCoordinates(
                    Util.randomIntExc(theDimensions.getFloor()),
                    Util.randomIntExc(theDimensions.getX()),
                    Util.randomIntExc(theDimensions.getY())
            );
        }

        /**
         * Randomly selects a position on a wall
         *
         * @param theDimensions The size of the dungeon
         * @param theFloor The floor to pick a location in
         * @return A random position on a wall
         */
        private static RoomCoordinates randomCoordsOnWall(final RoomCoordinates theDimensions,
                                                          final int theFloor) {
            return randomCoordsOnWall(theDimensions, theFloor, false);
        }

        /**
         * Randomly selects a position on a wall
         *
         * @param theDimensions The size of the dungeon
         * @param theFloor The floor to pick a location in
         * @param theNorthAndSouthOnly Whether only the north and south walls
         *                             should be considered
         * @return A random position on a valid wall
         */
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
