package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class handles and modifies the contents of the dungeon rooms.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Room implements Serializable {

    /**
     * The width and length of the contents of a Room in its String
     * representation.
     * Should be >= 3
     */
    static final int ROOM_SIZE = 3;

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 4590698579987805557L;

    /**
     * The integer value represent the value of half of the
     * room size.
     */
    private static final int HALF_ROOM_SIZE = ROOM_SIZE / 2;

    /**
     * The character representing the character symbol of the
     * adventurer in the dungeon room.
     */
    private static final char ADVENTURER = '@';

    /**
     * The character representing the character symbol of the
     * monster in the dungeon room.
     */
    private static final char MONSTER = 'M';

    /**
     * The character representing the character symbol of the
     * empty in the dungeon room.
     */
    private static final char EMPTY = ' ';

    /**
     * The character representing the character symbol of the
     * "more" in the dungeon room.
     */
    private static final char MORE = '.';

    /**
     * The character representing the character symbol of the
     * entrance in the dungeon room.
     */
    private static final char ENTRANCE = 'i';

    /**
     * The character representing the character symbol of the
     * exit in the dungeon room.
     */
    private static final char EXIT = 'o';

    /**
     * The character representing the character symbol of the
     * broken trap in the dungeon room.
     */
    private static final char BROKEN_TRAP = 'X';

    /**
     * The character representing the character symbol of the
     * wall in the dungeon room.
     */
    private static final char WALL = '*';

    /**
     * The character representing the character symbol of the
     * horizontal door in the dungeon room.
     */
    private static final char HORIZONTAL_DOOR = '-';

    /**
     * The character representing the character symbol of the
     * vertical door in the dungeon room.
     */
    private static final char VERTICAL_DOOR = '|';

    /**
     * The boolean array representing boolean value to check if
     * that index location has door.
     */
    private final boolean[] myDoors;

    /**
     * The container to utilizes, handles, and accesses
     * the collectable items in the dungeon.
     */
    private final Container myContainer;

    /**
     * The trap to set up traps in the dungeon room.
     */
    private final Trap myTrap;

    /**
     * The monster to set up monsters in the dungeon
     * room.
     */
    private Monster myMonster;

    /**
     * The string representing the monster name.
     */
    private final String myMonsterName;

    /**
     * The boolean true or false if it is the entrance.
     */
    private final boolean myIsEntrance;

    /**
     *The boolean true or false if it is the exit.
     */
    private final boolean myIsExit;

    /**
     * Constructor to construct the rooms for the dungeon.
     *
     * @param theDoors      The boolean array to check if represents
     *                      which index location has door.
     * @param theTrap       The trap to use method to set up trap
     *                      in the dungeon room.
     * @param theMonster    The monster to use method to set up monster
     *                      in the dungeon room.
     * @param theIsEntrance The boolean to check if there is an entrance.
     * @param theIsExit     The boolean to check if there is an exit.
     * @param theItems      The item to use method to set up items in the
     *                      dungeon room.
     */
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

    /**
     * Creates and formats the content types and its representations in the
     * dungeon rooms as in the list of string.
     *
     * @return  The string list representing the content types and its
     *          representations in the dungeon rooms.
     */
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

    /**
     * ToString method to formats and creates the dungeon rooms.
     *
     * @return The string representing the dungeon rooms.
     */
    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * ToString method to formats and creates the dungeon rooms.
     *
     * @param theHasAdventurer The boolean representing if the room
     *                         has the adventurer in it.
     * @return                 The string representing the dungeon rooms.
     */
    String toString(final boolean theHasAdventurer) {
        final StringBuilder builder = new StringBuilder();

        appendHorizontalWall(builder, Direction.NORTH);
        appendVerticalWallsAndContents(builder, theHasAdventurer);
        appendHorizontalWall(builder, Direction.SOUTH);

        return builder.toString();
    }

    /**
     * Get the container.
     *
     * @return The container.
     */
    Container getContainer() {
        return myContainer;
    }

    /**
     * Collects the item in the current room.
     *
     * @param theInventory The inventory of the adventurer.
     * @return             The items in the inventory.
     */
    Item[] collectItems(final Container theInventory) {
        final Item[] items = myContainer.viewItems();

        theInventory.addItems(items);
        myContainer.clearItems();

        return items;
    }

    /**
     * Checks if the current room has doors.
     *
     * @param theDirection The direction of room (N,S,W,E) that has
     *                     door.
     * @return             The boolean true or false if that direction of
     *                     the room has door.
     */
    boolean hasDoor(final Direction theDirection) {
        return myDoors[theDirection.ordinal()];
    }

    /**
     * Checks if it is the entrance.
     *
     * @return The boolean true or false if it is the entrance.
     */
    boolean isEntrance() {
        return myIsEntrance;
    }

    /**
     * Checks if it is the exit.
     *
     * @return The boolean true or false if it is the exit.
     */
    boolean isExit() {
        return myIsExit;
    }

    /**
     * Attempts to activate the trap and applies the trap's damage onto
     * the dungeon character.
     *
     * @param theTarget The dungeon character that the trap's damage will be
     *                  dealt on.
     *
     * @return          The type of attack result and amount after
     *                  the skill is executed.
     */
    AttackResultAndAmount activateTrap(final DungeonCharacter theTarget) {
        return myTrap == null ?
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
               myTrap.activate(theTarget);
    }

    /**
     * Attempts to board the trap.
     *
     * @return  The boolean true or false if the trap is boarded successfully.
     */
    boolean boardTrap() {
        return myTrap != null && myTrap.board();
    }

    /**
     * Gets the name of the trap class.
     *
     * @return The string representing the trap class.
     */
    String getTrapClass() {
        return myTrap == null ? Util.NONE : myTrap.getClassName();
    }

    /**
     * Gets the trap debuff type.
     *
     * @return The string representing the trap debuff type.
     */
    String getTrapDebuffType() {
        return myTrap == null ?
               Util.NONE :
               myTrap.getDamageType().getDebuffType().toString();
    }

    /**
     * Gets the monster.
     *
     * @return The monster.
     */
    Monster getMonster() {
        return myMonster;
    }

    /**
     * Gets the monster name.
     *
     * @return The string representing the monster name.
     */
    String getMonsterName() {
        return myMonsterName;
    }

    /**
     * Executes and applies the damage to the monster.
     *
     * @param theAttacker The dungeon character or the adventurer's character
     *                    to get and apply its unique special skill's effect onto
     *                    the monster.
     * @return            The string result representing the damage result after
     *                    attempted to attack monster.
     */
    AttackResultAndAmount attackMonster(final DungeonCharacter theAttacker) {
        return myMonster == null ?
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION) :
               killMonsterOnKillResult(
                       theAttacker.attemptDamage(
                               myMonster,true
                       )
               );
    }

    /**
     * If the monster get K'Oed by one hit, the adventurer will get one
     * random item from the dungeon as a reward.
     *
     * @param theResult   The kill result for checking purpose.
     * @return            The string result representing the kill result after
     *                    attempted to attack monster.
     */
    AttackResultAndAmount killMonsterOnKillResult(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            myContainer.addItem(ItemFactory.createRandom());
            myMonster = null;
        }

        return theResult;
    }


    /**
     * Appends horizontal wall for the dungeon rooms.
     *
     * @param theBuilder    The string builder to append the wall.
     * @param theDirection  The direction of the room.
     */
    // **-**
    private void appendHorizontalWall(final StringBuilder theBuilder,
                                      final Direction theDirection) {
        theBuilder.append(WALL).append(WALL)
                  .append(hasDoor(theDirection) ? HORIZONTAL_DOOR : WALL)
                  .append(WALL).append(WALL)
                  .append('\n');
    }

    /**
     * Appends vertical walls and the contents within for the dungeon rooms.
     *
     * @param theBuilder        The string builder to append the wall.
     * @param theHasAdventurer  The boolean to check if the current room has
     *                          the adventurer.
     */
    // * co*
    // |nte|
    // *nts*
    private void appendVerticalWallsAndContents(final StringBuilder theBuilder,
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

    /**
     * Appends wall and line contents for the dungeon rooms.
     *
     * @param theBuilder    The string builder to append the wall.
     * @param theContents   The 2D character array representing the
     *                      line contents.
     * @param theStart      The integer value representing the
     *                      start location of the room.
     * @param theEnd        The integer value representing the
     *                      end location of the room.
     */
    private void appendWallAndContentsLine(final StringBuilder theBuilder,
                                           final char[][] theContents,
                                           final int theStart,
                                           final int theEnd) {
        for (int i = theStart; i < theEnd; i++) {
            theBuilder.append(WALL).append(theContents[i]).append(WALL)
                      .append('\n');
        }
    }

    /**
     * Constructs and sets up the room contents for the dungeon rooms
     * , including the monster, the trap, the entrance, the items,
     * the exit, and all other walls contents.
     *
     * @param theHasAdventurer The boolean to check if the current room has
     *                         the adventurer.
     * @return                 The 2D character array containing all the
     *                         contents for the dungeon rooms.
     */
    private char[][] roomContents(final boolean theHasAdventurer) {
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

    /**
     * Adds the contents to the dungeon rooms.
     *
     * @param theChar       The character representing the content.
     * @param theContents   The 2D character array containing all
     *                      the contents for the room.
     * @param thePosition   The integer array position to get
     *                      the location of the content.
     */
    private void addToContents(final char theChar,
                               final char[][] theContents,
                               final int[] thePosition) {
        theContents[thePosition[0]][thePosition[1]] = theChar;
        stepBack(thePosition);
    }

    /**
     * Steps back to the next position in the room's array contents
     * allowing the representation of the next content
     * in the room can be added.
     *
     * @param thePosition   The integer array position to get
     *                      the location of the content.
     */
    private void stepBack(final int[] thePosition) {
        if (--thePosition[1] < 0) {
            thePosition[0]--;
            thePosition[1] = ROOM_SIZE - 1;
        }
    }

    /**
     * Checks if the 2D character array of the contents is full.
     *
     * @param theContents   The 2D character array containing all
     *                      the contents for the room.
     * @param thePosition   The integer array position to get
     *                      the location of the content.
     * @return              The boolean true or false if the 2D
     *                      character array of the contents is full.
     */
    private boolean testContentsFull(final char[][] theContents,
                                     final int[] thePosition) {
        if (thePosition[0] == -1) {
            theContents[0][0] = MORE;
            return true;
        }
        return false;
    }

    /**
     * Sets the remaining spots in the 2D character array of the contents
     * to be empty.
     *
     * @param theContents   The 2D character array containing all
     *                      the contents for the room.
     * @param thePosition   The integer array position to get
     *                      the location of the content.
     */
    private void setRemainingToEmpty(final char[][] theContents,
                                     final int[] thePosition) {
        while (thePosition[0] != -1) {
            addToContents(EMPTY, theContents, thePosition);
        }
    }
}
