package model;

import java.io.Serializable;

public class Room implements Serializable {

    /**
     * The width and length of the contents of a Room in its String
     * representation.
     * Should be >= 3
     */
    static final int ROOM_SIZE = 3;
    private static final int HALF_ROOM_SIZE = ROOM_SIZE / 2;

    private static final char ADVENTURER = '@';
    private static final char MONSTER = 'M';
    private static final char EMPTY = ' ';
    private static final char MORE = '…';
    private static final char ENTRANCE = 'i';
    private static final char EXIT = 'o';
    private static final char BROKEN_TRAP = 'X';
    private static final char WALL = '*';
    private static final char HORIZONTAL_DOOR = '-';
    private static final char VERTICAL_DOOR = '|';

    private final boolean[] myDoors;
    private final Container myContainer;
    private final Trap myTrap;
    private Monster myMonster;
    private final boolean myIsEntrance;
    private final boolean myIsExit;

    Room(final boolean[] theDoors,
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
        return toString(false);
    }

    String toString(final boolean theHasAdventurer) {
        StringBuilder theBuilder = new StringBuilder();

        appendHorizontalWall(theBuilder, Direction.NORTH);
        appendVerticalWallsAndContents(theBuilder, theHasAdventurer);
        appendHorizontalWall(theBuilder, Direction.SOUTH);

        return theBuilder.toString();
    }

    Container getContainer() {
        return myContainer;
    }

    boolean hasDoor(final Direction theDirection) {
        return myDoors[theDirection.ordinal()];
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

    AttackResultAndAmount activateTrap(final DungeonCharacter theTarget) {
        return myTrap == null ?
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
               myTrap.activate(theTarget);
    }

    boolean boardTrap() {
        return myTrap != null && myTrap.board();
    }

    String getTrapClass() {
        return myTrap == null ? Util.NONE : myTrap.getClassName();
    }

    String getTrapDebuffType() {
        return myTrap == null ?
               Util.NONE :
               myTrap.getDamageType().getDebuffType().toString();
    }

    Monster getMonster() {
        return myMonster;
    }

    AttackResultAndAmount attackMonster(final DungeonCharacter theAttacker) {
        return myMonster == null ?
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
               killMonsterOnKillResult(theAttacker.attemptDamage(
                   myMonster,true
               ));
    }

    AttackResultAndAmount killMonsterOnKillResult(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            // Send drops to Room's Container
            myMonster = null;
        }

        return theResult;
    }


    // **-**
    private void appendHorizontalWall(final StringBuilder theBuilder,
                                      final Direction theDirection) {
        theBuilder.append(WALL).append(WALL)
                  .append(hasDoor(theDirection) ? HORIZONTAL_DOOR : WALL)
                  .append(WALL).append(WALL)
                  .append('\n');
    }

    // * co*
    // |nte|
    // *nts*
    private void appendVerticalWallsAndContents(final StringBuilder theBuilder,
                                                final boolean theHasAdventurer) {
        char[][] contents = roomContents(theHasAdventurer);

        appendWallAndContentsLine(
                theBuilder,
                contents, 0, HALF_ROOM_SIZE
        );
        theBuilder.append(hasDoor(Direction.WEST) ? VERTICAL_DOOR : WALL)
                  .append(contents[HALF_ROOM_SIZE])
                  .append(hasDoor(Direction.EAST) ? VERTICAL_DOOR : WALL)
                  .append('\n');
        appendWallAndContentsLine(
                theBuilder,
                contents, HALF_ROOM_SIZE + 1, ROOM_SIZE
        );
    }

    private void appendWallAndContentsLine(final StringBuilder theBuilder,
                                           final char[][] theContents,
                                           final int theStart,
                                           final int theEnd) {
        for (int i = theStart; i < theEnd; i++) {
            theBuilder.append(WALL).append(theContents[i]).append(WALL)
                      .append('\n');
        }
    }

    private char[][] roomContents(final boolean theHasAdventurer) {
        char[][] contents = new char[ROOM_SIZE][ROOM_SIZE];
        int[] position = new int[2]; // row, col
        position[0] = position[1] = ROOM_SIZE - 1;

        if (theHasAdventurer) {
            addToContents(ADVENTURER, contents, position);
        }
        if (myMonster != null) {
            addToContents(MONSTER, contents, position);
        }
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
