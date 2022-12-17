package model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import controller.ProgramFileManager;

/**
 * This class manages the interactions between the other high-level classes in
 * the model to produce the overall behavior for exploring, using items, and
 * combat.
 * <p>
 * If a menu has cheats (such as revealing all rooms on the map) they can be
 * accessed with the hidden menu option "oop" (without quotes; defined in
 * view.InputReader).
 */
public class DungeonAdventure implements Serializable {

    /**
     * Serialization class ID
     */
    @Serial
    private static final long serialVersionUID = 7334732339432863725L;

    /**
     * Error message for failure to read needed data from DB
     */
    private static final String DB_ERROR =
            "An error occurred while accessing the database, and the " +
            "application could not recover.\n";

    /**
     * The Dungeon the Adventurer will explore and fight in
     */
    private final Dungeon myDungeon;
    /**
     * The player's character
     */
    private final Adventurer myAdventurer;
    /**
     * The Container holding the player's items
     */
    private final Container myInventory;
    /**
     * The Adventurer's current location in the dungeon
     */
    private RoomCoordinates myAdventurerCoordinates;
    /**
     * Whether the Adventurer is still alive
     */
    private boolean myIsAlive;
    /**
     * Whether the Adventurer is in combat (is in a room with a Monster)
     */
    private boolean myIsInCombat;
    /**
     * Allocates the turns between the Adventurer and Monster based on speed
     */
    private TurnAllocator myTurnAllocator;

    /**
     * Whether to hide unexplored rooms' contents on the map
     */
    private boolean myIsUnexploredHidden;

    /**
     * Creates a new game instance wiht the specified Adventurer details
     *
     * @param theAdventurerName The name of the new Adventurer
     * @param theAdventurerClass The class of the new Adventurer
     * @param theDifficulty The difficulty of the new game
     * @throws IllegalArgumentException Indicates an invalid Adventurer class
     *                                  or Difficulty was provided
     */
    public DungeonAdventure(final String theAdventurerName,
                            final int theAdventurerClass,
                            final Difficulty theDifficulty)
            throws IllegalArgumentException {
        if (!AdventurerFactory.getInstance().isValidIndex(theAdventurerClass)) {
            throw new IllegalArgumentException(
                    "Adventurer class index out of bounds: " +
                    theAdventurerClass + ".\nCheck " +
                    "AdventurerFactory.isValidIndex to validate index"
            );
        }
        if (theDifficulty == null) {
            throw new IllegalArgumentException(
                    "Difficulty passed to DungeonAdventure constructor must" +
                    "not be null"
            );
        }

        myDungeon = DungeonFactory.create(theDifficulty);
        myAdventurerCoordinates = myDungeon.getEntrance();
        myDungeon.getMap().explore(myAdventurerCoordinates);

        myAdventurer = AdventurerFactory.getInstance()
                .create(theAdventurerClass, theDifficulty);
        if (theAdventurerName != null && !Util.NONE.equals(theAdventurerName)) {
            myAdventurer.setName(theAdventurerName);
        }

        myInventory = InventoryFactory.createWithInitialItems(theDifficulty);

        myIsAlive = true;
        // myInCombat false (myTurnAllocator irrelevant)

        myIsUnexploredHidden = true;
    }

    /**
     * Populates the DamageDealer factories from the DB
     *
     * @return Whether the factories could be created
     */
    public static boolean buildFactories() {
        DBManager dbManager = null;
        try {
            dbManager = new SQLiteDBManager();

            AdventurerFactory.buildInstance(dbManager);
            MonsterFactory.buildInstance(dbManager);
            TrapFactory.buildInstance(dbManager);

            dbManager.close();
        } catch (SQLException | IllegalArgumentException e1) {
            logDBException(e1);

            if (dbManager != null) {
                try {
                    dbManager.close();
                } catch (SQLException e2) {
                    logDBException(e2);
                    // Still need to exit, so nothing else here
                }
            }

            return false;
        }

        return true;
    }

    /**
     * Provides an array of all possible Adventurer classes
     *
     * @return an array of the classes that may be chosen for an Adventurer
     */
    public static String[] getAdventurerClasses() {
        return AdventurerFactory.getInstance().getClasses();
    }

    /**
     * Provides the meanings of the symbols from the different parts of the
     * model
     *
     * @return A List of the representations of each visually represented
     *         component of the model, grouped into Lists by what part of the
     *         model they come from
     */
    public static List<List<String>> getCharRepresentations() {
        return List.of(
                Room.getContentTypesAndRepresentations(),
                Dungeon.getMapRepresentations(),
                ItemFactory.getItemsAndRepresentation(),
                TrapFactory.getInstance().getClassesAndRepresentations()
        );
    }

    /**
     * Indicates whether the specified index refers to a valid Adventurer class
     *
     * @param theIndex The index of the class of Adventurer
     * @return Whether the index is a valid class
     */
    public static boolean isValidAdventurerClass(final int theIndex) {
        return AdventurerFactory.getInstance().isValidIndex(theIndex);
    }

    /**
     * Indicates whether the specified index refers to a valid Difficulty level
     *
     * @param theIndex The index of the difficulty
     * @return Whether the index is a valid difficulty
     */
    public static boolean isValidDifficulty(final int theIndex) {
        return Util.isValidIndex(theIndex, Difficulty.values().length);
    }

    /**
     * Logs an error to the log file
     *
     * @param theException The issue to log
     */
    private static void logDBException(final Exception theException) {
        ProgramFileManager.getInstance().logException(
                theException, DB_ERROR, false
        );
    }


    /**
     * Gets a String representing the Adventurer and their stats
     *
     * @return a String representation of the Adventurer
     */
    public String getAdventurer() {
        return myAdventurer.toString();
    }

    /**
     * Gets the name of the Adventurer
     *
     * @return the Adventurer's name
     */
    public String getAdventurerName() {
        return myAdventurer.getName();
    }

    /**
     * Gets the debuff type of the Adventurer
     *
     * @return the Adventurer's debuff type
     */
    public String getAdventurerDebuffType() {
        return myAdventurer.getDamageType().getDebuffType().toString();
    }

    /**
     * Gets a String representing the rooms in the Dungeon
     *
     * @return a String representation of the dungeon
     */
    public String getMap() {
        return myDungeon.toString(
                myAdventurerCoordinates, myIsUnexploredHidden
        );
    }

    /**
     * Indicates whether unexplored rooms have their contents hidden on the map
     *
     * @return Whether unexplored rooms have their contents hidden on the map
     */
    public boolean isUnexploredHidden() {
        return myIsUnexploredHidden;
    }

    /**
     * Toggles whether unexplored rooms have their contents hidden on the map
     */
    public void toggleIsUnexploredHidden() {
        myIsUnexploredHidden = !myIsUnexploredHidden;
    }

    /**
     * Gets a String representing the current room
     *
     * @return a String representation of the room the Adventurer is in
     */
    public String getRoom() {
        return getCurrentRoom().toString();
    }

    /**
     * Gets an array of Strings representing the items in the inventory
     *
     * @return an array of Strings representing the items in the inventory
     */
    public String[] getInventoryItems() {
        return myInventory.viewItemsAsStrings();
    }

    /**
     * Adds max stacks of all usable items to the player's inventory
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public void addMaxItems() throws IllegalStateException {
        requireAlive();

        myInventory.addItems(ItemFactory.createAllItemsMaxed());
    }

    /**
     * Indicates whether the player can currently see/use the specified item
     *
     * @param theIndex The index in the inventory to check
     * @return Whether the specified item can be seen and/or used
     */
    public boolean canUseInventoryItem(final int theIndex) {
        return myInventory.canUse(theIndex, myIsInCombat);
    }

    /**
     * Uses the specified inventory item on the map, Adventurer, or current
     * room
     *
     * @param theIndex The index in the inventory of the item to use
     * @return The results of using or trying to use the item
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public String useInventoryItem(final int theIndex)
            throws IllegalStateException {
        requireAlive();

        return (canUseInventoryItem(theIndex)) ?
                myInventory.useItem(
                        theIndex,
                        myAdventurer,
                        myDungeon.getMap(),
                        getCurrentRoom(),
                        myAdventurerCoordinates,
                        myIsInCombat
                ) :
                Util.NONE;
    }

    /**
     * Indicates whether the current room has any items in it for the player to
     * collect.
     *
     * @return Whether there are items in the current room
     */
    public boolean roomHasItems() {
        return getCurrentRoom().getContainer().hasItems();
    }

    /**
     * Adds any items in the current room to the player's inventory
     *
     * @return An array of the items that were collected
     */
    public String[] collectItems() {
        return Arrays.stream(getCurrentRoom().collectItems(myInventory))
                     .map(Item::toString)
                     .toArray(String[]::new);
    }

    /**
     * Indicates whether the player meets all the conditions to win the game,
     * including being at the exit and having all 4 Pillars of OO
     *
     * @return Whether the player can exit the dungeon (win)
     */
    public boolean canExit() {
        return getCurrentRoom().isExit() && myInventory.hasAllPillars();
    }

    /**
     * Indicates whether the player is in battle with Monster, which is the
     * case if there is a living Monster in the current room.
     *
     * @return Whether the player is in battle with monster.
     */
    public boolean isInCombat() {
        return myIsInCombat;
    }

    /**
     * Indicates whether it is the Monster's turn next in combat.
     *
     * @return Whether the Monster gets to play the next move
     */
    public boolean isMonsterTurn() {
        return testCombat() && !myTurnAllocator.peekNextTurn();
    }

    /**
     * Plays the Monster's next turn in attacking the Adventurer. This includes
     * the Monster taking damage from any debuffs, possibly healing, and
     * attempting to attack the Adventurer
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public AttackResultAndAmount[] tryMonsterTurn()
            throws IllegalStateException {
        requireAlive();

        if (myIsInCombat && !myTurnAllocator.peekNextTurn()) {
            if (myTurnAllocator.peekNextTurn()) {
                return null; // Adventurer's turn. To execute code between
                             // finding out whose turn it is and executing the
                             // Monster's turn, client should first call
                             // isMonsterTurn() to decide which turn to run
            }

            return runMonsterTurn();
        }

        return null; // Also waiting for input
    }

    /**
     * Retrieves a formatted String with information about the current room's
     * Monster
     *
     * @return The name and other details of the Monster in the current room
     */
    public String getMonster() {
        return getCurrentRoom().getMonster().toString();
    }

    /**
     * Gets the name of the Monster
     *
     * @return the Monster's name
     */
    public String getMonsterName() {
        return getCurrentRoom().getMonsterName();
    }

    /**
     * Gets the debuff type of the Monster
     *
     * @return the Monster's debuff type
     */
    public String getMonsterDebuffType() {
        return getCurrentRoom().getMonster() != null ?
               getCurrentRoom().getMonster().getDamageType().getDebuffType()
                       .toString() :
               Util.NONE;
    }

    /**
     * Immediately kills the Monster and exits combat. Drops still occur
     *
     * @return A message about the Monster's death
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public AttackResultAndAmount killMonster() throws IllegalStateException {
        requireAlive();

        final AttackResultAndAmount result =
                getCurrentRoom().killMonsterOnKillResult(
                        new AttackResultAndAmount(
                                AttackResult.KILL,
                                getCurrentRoom().getMonster().getHP()
                        )
                );
        testCombat();

        return result;
    }

    /**
     * Indicates whether the Adventurer is still alive. This is required for
     * any actions that change the state of the game to occur.
     *
     * @return Whether the Adventurer is alive
     */
    public boolean isAlive() {
        return myIsAlive;
    }

    /**
     * Executes the Adventurer's turn by attacking the Monster
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     */
    public AttackResultAndAmount[] attack() throws IllegalStateException {
        return runAdventurerTurn(true);
    }

    /**
     * Retrieves a String with details about the Adventurer's combat skill.
     *
     * @return A String representing the Adventurer's skill
     */
    public String getSpecialSkill() {
        return myAdventurer.viewSpecialSkill();
    }

    /**
     * Indicates whether the cooldown of the Adventurer's skill allows it to be
     * used now
     *
     * @return Whether the skill can be used now
     */
    public boolean canUseSpecialSkill() {
        return myAdventurer.getSpecialSkill().canUse();
    }

    /**
     * Applies the Adventurer's skill to the Adventurer or the Monster,
     * depending on the skill.
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     */
    public AttackResultAndAmount[] useSpecialSkill()
            throws IllegalStateException {
        return canUseSpecialSkill() ?
               runAdventurerTurn(false) :
               null;
    }

    /**
     * Attempts to exit combat by fleeing to another room. May or may not
     * succeed, depending on chance and the Adventurer's and Monster's speeds.
     * Uses the Adventurer's turn.
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public AttackResultAndAmount[] flee(final Direction theDirection)
            throws IllegalStateException {
        requireAlive();

        if (myIsInCombat && myTurnAllocator.peekNextTurn() &&
            isValidDirection(theDirection)) {

            if (Util.probabilityTest(SpeedTest.evaluate(
                    myAdventurer, getCurrentRoom().getMonster()))) {
                myIsInCombat = false;

                return Stream.concat(
                        Stream.of(AttackResultAndAmount.getNoAmount(
                                AttackResult.FLED_SUCCESSFULLY
                        )),
                        Arrays.stream(moveAdventurerUnchecked(theDirection))
                ).toArray(AttackResultAndAmount[]::new);
            }

            nextTurn();

            return new AttackResultAndAmount[]{
                    AttackResultAndAmount.getNoAmount(
                            AttackResult.COULD_NOT_FLEE
                    ),
                    advanceInCombat()
            };
        }
        return null;
    }

    /**
     * Indicates whether the Adventurer can move in the provided direction
     *
     * @param theDirection The direction to move in
     * @return Whether the Adventurer can more in that direction
     */
    public boolean isValidDirection(final Direction theDirection) {
        return getCurrentRoom().hasDoor(theDirection);
    }

    /**
     * Retrieves a formatted String with information about the current room's
     * Trap
     *
     * @return The details of the Trap in the current room
     */
    public String getTrap() {
        return getCurrentRoom().getTrapClass();
    }

    /**
     * Gets the debuff type of the Trap
     *
     * @return the Trap's debuff type
     */
    public String getTrapDebuffType() {
        return getCurrentRoom().getTrapDebuffType();
    }

    /**
     * Moves the Adventurer in the specified direction to an adjacent room.
     *
     * @param theDirection The direction to move in
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public AttackResultAndAmount[] moveAdventurer(final Direction theDirection)
            throws IllegalStateException {
        requireAlive();

        return !myIsInCombat && isValidDirection(theDirection) ?
               moveAdventurerUnchecked(theDirection) :
               null;
    }

    /**
     * Indicates whether the current room has stairs leading up or down to
     * another floor, depending on the provided boolean.
     *
     * @param theIsUp Whether to check for stairs leading up (false for down)
     * @return Whether valid stairs leading in the specified direction exist
     */
    public boolean hasStairs(final boolean theIsUp) {
        return theIsUp ?
               myDungeon.hasStairsUp(myAdventurerCoordinates) :
               myDungeon.hasStairsDown(myAdventurerCoordinates);
    }

    /**
     * Moves the Adventurer to the room connected to the current room by the
     * specified stairs.
     *
     * @param theIsUp Whether to attempt to use stairs leading up (false for
     *                down)
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    public AttackResultAndAmount[] useStairs(final boolean theIsUp)
            throws IllegalStateException {
        requireAlive();

        if (!myIsInCombat && hasStairs(theIsUp)) {
            return moveToCoordsUnchecked(myAdventurerCoordinates.addFloor(
                    theIsUp, getDimensions().getFloor()
            ));
        }

        return null;
    }

    /**
     * Gets the room the Adventurer is in
     *
     * @return The Adventurer's current room in the dungeon
     */
    private Room getCurrentRoom() {
        return myDungeon.getRoom(myAdventurerCoordinates);
    }

    /**
     * Gets the size of the dungeon
     *
     * @return The dungeon's dimensions
     */
    private RoomCoordinates getDimensions() {
        return myDungeon.getDimensions();
    }

    /**
     * Moves the Adventurer in the specified direction to an adjacent room.
     *
     * @param theDirection The direction to move in
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
     */
    private AttackResultAndAmount[] moveAdventurerUnchecked(final Direction theDirection) {
        return moveToCoordsUnchecked(myAdventurerCoordinates.add(
                theDirection, myDungeon.getDimensions()
        ));
    }

    /**
     * Relocates the Adventurer to the specified room.
     *
     * @param theCoords The location to move to
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
     */
    private AttackResultAndAmount[] moveToCoordsUnchecked(final RoomCoordinates theCoords) {
        if (!theCoords.isSameRoom(myAdventurerCoordinates)) {
            myAdventurerCoordinates = theCoords;
            myDungeon.getMap().explore(myAdventurerCoordinates);

            final AttackResultAndAmount buffDamage = advanceOutOfCombat();
            final AttackResultAndAmount trapDamage = myIsAlive ?
                    getCurrentRoom().activateTrap(myAdventurer) :
                    AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
            testDead(trapDamage);

            return new AttackResultAndAmount[]{
                    buffDamage,
                    trapDamage
            };
        }

        return null;
    }

    /**
     * Updates whether the Adventurer is dead based on the result of damaging
     * them
     *
     * @param theResult The result of damaging the Adventurer
     */
    private void testDead(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            myIsAlive = false;
        }
    }

    /**
     * Plays the Monster's next turn in attacking the Adventurer. This includes
     * the Monster taking damage from any debuffs, possibly healing, and
     * attempting to attack the Adventurer
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     */
    private AttackResultAndAmount[] runMonsterTurn() {
        final AttackResultAndAmount monsterBuffResult =
                getCurrentRoom().killMonsterOnKillResult(
                        getCurrentRoom().getMonster().advanceBuffsAndDebuffs()
                );

        if (testCombat()) {
            final AttackResultAndAmount healResult =
                    getCurrentRoom().getMonster().attemptHeal();
            final AttackResultAndAmount attackResult =
                    getCurrentRoom().getMonster().attemptDamage(
                            myAdventurer, true
                    );
            testDead(attackResult);

            nextTurn();

            return new AttackResultAndAmount[]{
                    monsterBuffResult, healResult, attackResult
            };
        }
        return new AttackResultAndAmount[]{monsterBuffResult};
    }

    /**
     * Executes the Adventurer's turn by attacking the Monster with a basic
     * attack or skill
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    private AttackResultAndAmount[] runAdventurerTurn(final boolean theIsBasicAttack)
            throws IllegalStateException {
        requireAlive();

        if (myIsInCombat && myTurnAllocator.peekNextTurn()) {
            final AttackResultAndAmount attackResult;
            if (theIsBasicAttack) {
                attackResult = getCurrentRoom().attackMonster(myAdventurer);
            } else {
                attackResult = getCurrentRoom().killMonsterOnKillResult(
                        myAdventurer.useSpecialSkill(
                                getCurrentRoom().getMonster()
                        )
                );
            }

            if (attackResult.getResult() != AttackResult.EXTRA_TURN_NO_DEBUFF &&
                attackResult.getResult() != AttackResult.EXTRA_TURN_DEBUFF) {
                nextTurn();
            }

            return new AttackResultAndAmount[]{advanceInCombat(), attackResult};
        }

        return null;
    }

    /**
     * Advances the game by one turn for skills and debuffs and tests whether
     * the new room has a Monster and whether the Adventurer has died
     *
     * @return The result of any buff damage on the Adventurer
     */
    private AttackResultAndAmount advanceOutOfCombat() {
        testEnterCombat();
        myAdventurer.getSpecialSkill().advance();

        final AttackResultAndAmount result = myAdventurer.advanceDebuffs();
        testDead(result);

        return result;
    }

    /**
     * Advances the game by one turn for skills and all buffs and tests whether
     * the Adventurer has died
     *
     * @return The result of any buff damage on the Adventurer
     */
    private AttackResultAndAmount advanceInCombat() {
        myAdventurer.getSpecialSkill().advance();

        final AttackResultAndAmount result =
                myAdventurer.advanceBuffsAndDebuffs();
        testDead(result);

        return result;
    }

    /**
     * Tests whether the room has a Monster and allocates turns if so
     */
    private void testEnterCombat() {
        if (testCombat()) {
            recalculateTurnAllocation();
        }
    }

    /**
     * Tests and returns whether the room has a Monster
     *
     * @return Whether the room has a Monster
     */
    private boolean testCombat() {
        return myIsInCombat = getCurrentRoom().getMonster() != null;
    }

    /**
     * Allocates combat turns
     */
    private void recalculateTurnAllocation() {
        myTurnAllocator = new TurnAllocator(
                myAdventurer.getAdjustedSpeed(),
                getCurrentRoom().getMonster().getAdjustedSpeed()
        );
    }

    /**
     * Advances the TurnAllocator to the next combat turn and checks whether
     * combat should continue
     */
    private void nextTurn() {
        if (testCombat() && myTurnAllocator.isCompleted()) {
            recalculateTurnAllocation();
        }

        myTurnAllocator.nextTurn();
    }

    /**
     * Throws an exception if a game-changing operation is performed when the
     * Adventurer is dead
     *
     * @throws IllegalStateException Indicates that an operation was attempted
     *                               while the Adventurer was dead
     */
    private void requireAlive() throws IllegalStateException {
        if (!myIsAlive) {
            final IllegalStateException e = new IllegalStateException(
                    "The adventurer is dead, and no actions other than " +
                    "viewing the final game state are allowed.\n"
            );

            ProgramFileManager.getInstance().logException(e, true);
            throw e;
        }
    }
}
