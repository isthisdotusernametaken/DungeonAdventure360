package model;

import java.io.Serializable;

public class Room implements Serializable {

    /**
     * The width and length of the contents of a Room in its String
     * representation.
     * Should be >= 2
     */
    static final int ROOM_SIZE = 3;

    private static final char EMPTY = ' ';
    private static final char MORE = '…';
    private static final char ENTRANCE = 'i';
    private static final char EXIT = '0';
    private static final char BROKEN_TRAP = 'X';
    private static final char WALL = '*';
    private static final char HORIZONTAL_DOOR = '-';
    private static final char VERTICAL_DOOR = '|';

    private final Direction[] myDoors;
    private final Container myContainer;
    private final Trap myTrap;
    private Monster myMonster;
    private final boolean myIsEntrance;
    private final boolean myIsExit;

    Room(final Direction[] theDoors,
         final Trap theTrap,
         final Monster theMonster,
         final boolean theIsEntrance,
         final boolean theIsExit,
         final Item ... theItems) {

        myDoors = theDoors.clone();
        myContainer = new Container(theItems);
        myTrap = theTrap;
        myMonster = theMonster;
        myIsEntrance = theIsEntrance;
        myIsExit = theIsExit;
    }

    @Override
    public String toString() {
        StringBuilder theBuilder = new StringBuilder();

        appendHorizontalWall(theBuilder, Direction.NORTH);
        appendVerticalWallsAndContents(theBuilder);
        appendHorizontalWall(theBuilder, Direction.SOUTH);

        return theBuilder.toString();
    }

    Container getContents() {
        return myContainer;
    }

    Direction[] getDoors() {
        return myDoors.clone();
    }

    boolean hasDoor(final Direction theDirection) {
        for (Direction door : myDoors) {
            if (theDirection == door) {
                return true;
            }
        }

        return false;
    }

    boolean isEntrance() {
        return myIsEntrance;
    }

    boolean isExit() {
        return myIsExit;
    }

    boolean hasTrap() {
        return myTrap != null;
    }

    AttackResult activateTrap(final DungeonCharacter theTarget) {
        return myTrap == null ?
               AttackResult.NO_ACTION :
               myTrap.activate(theTarget);
    }

    boolean boardTrap() {
        return myTrap != null && myTrap.board();
    }

    Monster getMonster() {
        return myMonster;
    }

    AttackResult attackMonster(final DungeonCharacter theAttacker) {
        if (myMonster == null) {
            return AttackResult.NO_ACTION;
        } else {
            final AttackResult result = theAttacker.attemptDamage(
                    myMonster,true
            );

            if (result == AttackResult.KILL) {
                // Send drops to Room's Container
                myMonster = null;
            }

            return result;
        }
    }


    // **-**
    private void appendHorizontalWall(final StringBuilder theBuilder,
                                      final Direction theDirection) {
        theBuilder.append(WALL).append(WALL)
                .append(hasDoor(theDirection) ? HORIZONTAL_DOOR : WALL)
                .append(WALL).append(WALL);
    }

    // * co*
    // |nte|
    // *nts*
    private void appendVerticalWallsAndContents(final StringBuilder theBuilder
    ) {
        char[][] contents = roomContents();

        theBuilder.append(WALL).append(contents[0]).append(WALL)
                .append(hasDoor(Direction.WEST) ? VERTICAL_DOOR : WALL)
                .append(contents[1])
                .append(hasDoor(Direction.EAST) ? VERTICAL_DOOR : WALL)
                .append(WALL).append(contents[2]).append(WALL);
    }

    private char[][] roomContents() {
        char[][] contents = new char[ROOM_SIZE][ROOM_SIZE];
        int[] position = new int[2]; // row, col
        position[0] = position[1] = ROOM_SIZE - 1;

        if (myIsEntrance) {
            addToContents(ENTRANCE, contents, position);
        }
        if (myIsExit) {
            addToContents(EXIT, contents, position);
        }
        if (myTrap != null) {
            addToContents(
                    myTrap.isBroken() ?
                            BROKEN_TRAP :
                            myTrap.charRepresentation(),
                    contents,
                    position
            );
        }
        for (Item item : myContainer.viewItems()) {
            if (testContentsFull(contents, position)) {
                break;
            }

            addToContents(item.charRepresentation(), contents, position);
        }
        setRemainingToEmpty(contents, position);

        return contents;
    }

    private void addToContents(final char theChar,
                               final char[][] theContents,
                               final int[] thePosition) {
        theContents[thePosition[0]][thePosition[1]] = theChar;
        stepBack(thePosition);
    }

    private void stepBack(final int[] position) {
        if (--position[1] < 0) {
            position[0]--;
            position[1] = ROOM_SIZE - 1;
        }
    }

    private boolean testContentsFull(final char[][] theContents,
                                     final int[] thePosition) {
        if (thePosition[0] == -1) {
            theContents[0][0] = MORE;
            return true;
        }
        return false;
    }

    private void setRemainingToEmpty(final char[][] theContents,
                                     final int[] thePosition) {
        while (thePosition[0] != -1) {
            addToContents(EMPTY, theContents, thePosition);
        }
    }
}
