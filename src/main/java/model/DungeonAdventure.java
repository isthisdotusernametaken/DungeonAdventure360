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
 *
 * If a menu has cheats (such as revealing all rooms on the map) they can be
 * accessed with the hidden menu option "oop" (without quotes; defined in
 * view.InputReader).
 */
public class DungeonAdventure implements Serializable {

    @Serial
    private static final long serialVersionUID = 7334732339432863725L;

    private static final String DB_ERROR =
            "An error occurred while accessing the database, and the " +
            "application could not recover.\n";

    private final Dungeon myDungeon;
    private final Adventurer myAdventurer;
    private final Container myInventory;
    private RoomCoordinates myAdventurerCoordinates;
    private boolean myIsAlive;
    private boolean myIsInCombat;
    private TurnAllocator myTurnAllocator;

    private boolean myIsUnexploredHidden;

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

    public static String[] getAdventurerClasses() {
        return AdventurerFactory.getInstance().getClasses();
    }

    public static List<List<String>> getCharRepresentations() {
        return List.of(
                Room.getContentTypesAndRepresentations(),
                Dungeon.getMapRepresentations(),
                ItemFactory.getItemsAndRepresentation(),
                TrapFactory.getInstance().getClassesAndRepresentations()
        );
    }

    public static boolean isValidAdventurerClass(final int theIndex) {
        return AdventurerFactory.getInstance().isValidIndex(theIndex);
    }

    public static boolean isValidDifficulty(final int theIndex) {
        return Util.isValidIndex(theIndex, Difficulty.values().length);
    }

    private static void logDBException(final Exception theException) {
        ProgramFileManager.getInstance().logException(
                theException, DB_ERROR, false
        );
    }


    public String getAdventurer() {
        return myAdventurer.toString();
    }

    public String getAdventurerName() {
        return myAdventurer.getName();
    }

    public String getAdventurerDebuffType() {
        return myAdventurer.getDamageType().getDebuffType().toString();
    }

    public String getMap() {
        return myDungeon.toString(
                myAdventurerCoordinates, myIsUnexploredHidden
        );
    }

    public boolean isUnexploredHidden() {
        return myIsUnexploredHidden;
    }

    public void toggleIsUnexploredHidden() {
        myIsUnexploredHidden = !myIsUnexploredHidden;
    }

    public String getRoom() {
        return getCurrentRoom().toString();
    }

    public String[] getInventoryItems() {
        return myInventory.viewItemsAsStrings();
    }

    public void addMaxItems() throws IllegalStateException {
        requireAlive();

        myInventory.addItems(ItemFactory.createAllItemsMaxed());
    }

    public boolean canUseInventoryItem(final int theIndex) {
        return myInventory.canUse(theIndex, myIsInCombat);
    }

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

    public boolean roomHasItems() {
        return getCurrentRoom().getContainer().hasItems();
    }

    public String[] collectItems() {
        return Arrays.stream(getCurrentRoom().collectItems(myInventory))
                     .map(Item::toString)
                     .toArray(String[]::new);
    }

    public boolean canExit() {
        return getCurrentRoom().isExit() && myInventory.hasAllPillars();
    }

    public boolean isInCombat() {
        return myIsInCombat;
    }

    public boolean isMonsterTurn() {
        return testCombat() && !myTurnAllocator.peekNextTurn();
    }

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

    public String getMonster() {
        return getCurrentRoom().getMonster().toString();
    }

    public String getMonsterName() {
        return getCurrentRoom().getMonsterName();
    }

    public String getMonsterDebuffType() {
        return getCurrentRoom().getMonster() != null ?
               getCurrentRoom().getMonster().getDamageType().getDebuffType()
                       .toString() :
               Util.NONE;
    }

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

    public boolean isAlive() {
        return myIsAlive;
    }

    public AttackResultAndAmount[] attack() throws IllegalStateException {
        return runAdventurerTurn(true);
    }

    public String getSpecialSkill() {
        return myAdventurer.viewSpecialSkill();
    }

    public boolean canUseSpecialSkill() {
        return myAdventurer.getSpecialSkill().canUse();
    }

    public AttackResultAndAmount[] useSpecialSkill()
            throws IllegalStateException {
        return canUseSpecialSkill() ?
               runAdventurerTurn(false) :
               null;
    }

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

    public boolean isValidDirection(final Direction theDirection) {
        return getCurrentRoom().hasDoor(theDirection);
    }

    public String getTrap() {
        return getCurrentRoom().getTrapClass();
    }

    public String getTrapDebuffType() {
        return getCurrentRoom().getTrapDebuffType();
    }

    public AttackResultAndAmount[] moveAdventurer(final Direction theDirection)
            throws IllegalStateException {
        requireAlive();

        return !myIsInCombat && isValidDirection(theDirection) ?
               moveAdventurerUnchecked(theDirection) :
               null;
    }

    public boolean hasStairs(final boolean theIsUp) {
        return theIsUp ?
               myDungeon.hasStairsUp(myAdventurerCoordinates) :
               myDungeon.hasStairsDown(myAdventurerCoordinates);
    }

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

    private Room getCurrentRoom() {
        return myDungeon.getRoom(myAdventurerCoordinates);
    }

    private RoomCoordinates getDimensions() {
        return myDungeon.getDimensions();
    }

    private AttackResultAndAmount[] moveAdventurerUnchecked(final Direction theDirection) {
        return moveToCoordsUnchecked(myAdventurerCoordinates.add(
                theDirection, myDungeon.getDimensions()
        ));
    }

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

    private void testDead(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            myIsAlive = false;
        }
    }

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

    private AttackResultAndAmount advanceOutOfCombat() {
        testEnterCombat();
        myAdventurer.getSpecialSkill().advance();

        final AttackResultAndAmount result = myAdventurer.advanceDebuffs();
        testDead(result);

        return result;
    }

    private AttackResultAndAmount advanceInCombat() {
        myAdventurer.getSpecialSkill().advance();

        final AttackResultAndAmount result =
                myAdventurer.advanceBuffsAndDebuffs();
        testDead(result);

        return result;
    }

    private void testEnterCombat() {
        if (testCombat()) {
            recalculateTurnAllocation();
        }
    }

    private boolean testCombat() {
        return myIsInCombat = getCurrentRoom().getMonster() != null;
    }

    private void recalculateTurnAllocation() {
        myTurnAllocator = new TurnAllocator(
                myAdventurer.getAdjustedSpeed(),
                getCurrentRoom().getMonster().getAdjustedSpeed()
        );
    }

    private void nextTurn() {
        if (testCombat() && myTurnAllocator.isCompleted()) {
            recalculateTurnAllocation();
        }

        myTurnAllocator.nextTurn();
    }

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
