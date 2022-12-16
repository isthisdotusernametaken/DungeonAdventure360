package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {

    /**
     * The width and length of the contents of a Room in its String
     * representation.
     * Should be >= 3
     */
    static final int ROOM_SIZE = 3;

    static final int HALF_ROOM_SIZE = ROOM_SIZE / 2;

    static final char ADVENTURER = '@';
    static final char MONSTER = 'M';
    static final char EMPTY = ' ';
    static final char MORE = '.';
    static final char ENTRANCE = 'i';
    static final char EXIT = 'o';
    static final char BROKEN_TRAP = 'X';
    static final char WALL = '*';
    static final char HORIZONTAL_DOOR = '-';
    static final char VERTICAL_DOOR = '|';

    @Serial
    private static final long serialVersionUID = 4590698579987805557L;

    final boolean[] myDoors;
    final Container myContainer;
    final Trap myTrap;
    Monster myMonster;
    final String myMonsterName;
    final boolean myIsEntrance;
    final boolean myIsExit;

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
        myMonsterName = myMonster == null ? "" : myMonster.getName();
        myIsEntrance = theIsEntrance;
        myIsExit = theIsExit;
    }

    static List<String> getContentTypesAndRepresentations() {
        return List.of(
                ADVENTURER + ": Adventurer",
                MONSTER + ": Monster",
                ENTRANCE + ": Entrance",
                EXIT + ": Exit",
                BROKEN_TRAP + ": Broken Trap",
                WALL + ": Wall",
                HORIZONTAL_DOOR + " or " + VERTICAL_DOOR + ": Door",
                MORE + ": More contents than could be displayed"
        );
    }

    @Override
    public String toString() {
        return toString(false);
    }

    String toString(final boolean theHasAdventurer) {
        final StringBuilder builder = new StringBuilder();

        appendHorizontalWall(builder, Direction.NORTH);
        appendVerticalWallsAndContents(builder, theHasAdventurer);
        appendHorizontalWall(builder, Direction.SOUTH);

        return builder.toString();
    }

    Container getContainer() {
        return myContainer;
    }

    Item[] collectItems(final Container theInventory) {
        final Item[] items = myContainer.viewItems();

        theInventory.addItems(items);
        myContainer.clearItems();

        return items;
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

    public String getMonsterName() {
        return myMonsterName;
    }

    AttackResultAndAmount attackMonster(final DungeonCharacter theAttacker) {
        return myMonster == null ?
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
               killMonsterOnKillResult(
                       theAttacker.attemptDamage(
                               myMonster,true
                       )
               );
    }

    AttackResultAndAmount killMonsterOnKillResult(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            myContainer.addItem(ItemFactory.createRandom());
            myMonster = null;
        }

        return theResult;
    }


    // **-**
    void appendHorizontalWall(final StringBuilder theBuilder,
                              final Direction theDirection) {
        theBuilder.append(WALL).append(WALL)
                  .append(hasDoor(theDirection) ? HORIZONTAL_DOOR : WALL)
                  .append(WALL).append(WALL)
                  .append('\n');
    }

    // * co*
    // |nte|
    // *nts*
    void appendVerticalWallsAndContents(final StringBuilder theBuilder,
                                        final boolean theHasAdventurer) {
        final char[][] contents = roomContents(theHasAdventurer);

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

    void appendWallAndContentsLine(final StringBuilder theBuilder,
                                   final char[][] theContents,
                                   final int theStart,
                                   final int theEnd) {
        for (int i = theStart; i < theEnd; i++) {
            theBuilder.append(WALL).append(theContents[i]).append(WALL)
                      .append('\n');
        }
    }

    char[][] roomContents(final boolean theHasAdventurer) {
        final char[][] contents = new char[ROOM_SIZE][ROOM_SIZE];
        final int[] position = new int[2]; // row, col
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

    void addToContents(final char theChar,
                       final char[][] theContents,
                       final int[] thePosition) {
        theContents[thePosition[0]][thePosition[1]] = theChar;
        stepBack(thePosition);
    }

    void stepBack(final int[] thePosition) {
        if (--thePosition[1] < 0) {
            thePosition[0]--;
            thePosition[1] = ROOM_SIZE - 1;
        }
    }

    boolean testContentsFull(final char[][] theContents,
                             final int[] thePosition) {
        if (thePosition[0] == -1) {
            theContents[0][0] = MORE;
            return true;
        }
        return false;
    }

    void setRemainingToEmpty(final char[][] theContents,
                             final int[] thePosition) {
        while (thePosition[0] != -1) {
            addToContents(EMPTY, theContents, thePosition);
        }
    }
}
