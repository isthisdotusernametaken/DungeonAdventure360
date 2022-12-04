package model;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class DungeonAdventure implements Serializable {

    private static final String DB_ERROR =
            "An error occurred while accessing the database, and the " +
            "application could not recover.\n";

    private final Dungeon myDungeon;
    private final Adventurer myAdventurer;
    private final Container myInventory;
    private RoomCoordinates myAdventurerCoordinates;
    private boolean myIsDead;
    private boolean myIsInCombat;
    private TurnAllocator myTurnAllocator;

    public DungeonAdventure(final String theAdventurerName,
                            final int theAdventurerClass,
                            final Difficulty theDifficulty) {
        myDungeon = DungeonFactory.create(theDifficulty);
        myAdventurerCoordinates = myDungeon.getEntrance();
        myDungeon.getMap().explore(myAdventurerCoordinates);

        myAdventurer = AdventurerFactory.getInstance()
                .create(theAdventurerClass, theDifficulty);
        myAdventurer.setName(theAdventurerName);

        myInventory = InventoryFactory.createWithInitialItems(theDifficulty);

        myIsDead = false;
        // myInCombat false (myTurnAllocator irrelevant)
    }

    public static boolean buildFactories(final String theLogFile) {
        DBManager dbManager = null;
        try {
            dbManager = new SQLiteDBManager();

            AdventurerFactory.buildInstance(dbManager);
            MonsterFactory.buildInstance(dbManager);
            TrapFactory.buildInstance(dbManager);

            dbManager.close();
        } catch (SQLException | IllegalArgumentException e1) {
            logDBException(e1, theLogFile);

            if (dbManager != null) {
                try {
                    dbManager.close();
                } catch (SQLException e2) {
                    logDBException(e2, theLogFile);
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

    public static boolean isValidAdventurerClass(final int theIndex) {
        return AdventurerFactory.getInstance().isValidIndex(theIndex);
    }

    public static boolean isValidDifficulty(final int theIndex) {
        return Util.isValidIndex(theIndex, Difficulty.values().length);
    }

    private static void logDBException(final Exception theException,
                                       final String theLogFile) {
        try (FileWriter fw = new FileWriter(theLogFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println();

            pw.print(new Date());
            pw.println(':');

            pw.println(DB_ERROR);

            theException.printStackTrace(pw);
        } catch (IOException e2) {
            System.out.println(DB_ERROR);
        }
    }


    public String getAdventurer() {
        return myAdventurer.toString();
    }

    public String getAdventurerCoords() {
        return myAdventurerCoordinates.toString();
    }

    public String getMap(final boolean theLimitView) {
        return myDungeon.toString(myAdventurerCoordinates, theLimitView);
    }

    public String getRoom() {
        return getCurrentRoom().toString();
    }

    public String[] getInventoryItems() {
        return myInventory.viewItemsAsStrings();
    }

    public String useInventoryItem(final int theIndex) {
        requireAlive();

        return (Util.isValidIndex(theIndex, myInventory.size())) ?
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

    public boolean isInCombat() {
        return myIsInCombat;
    }

    public String[] tryNextCombatTurn() {
        requireAlive();

        if (myIsInCombat) {
            if (myTurnAllocator.peekNextTurn()) {
                return null; // Adventurer's turn
            }

            return runMonsterTurn();
        }

        return null; // Also waiting for input
    }

    public String getMonster() {
        return getCurrentRoom().getMonster().toString();
    }

    public String[] attack() {
        return runAdventurerTurn(true);
    }

    public String getSpecialSkill() {
        return myAdventurer.viewSpecialSkill();
    }

    public boolean canUseSpecialSkill() {
        return myAdventurer.getSpecialSkill().canUse();
    }

    public String[] useSpecialSkill() {
        return runAdventurerTurn(false);
    }

    public String[] flee(final Direction theDirection) {
        requireAlive();

        if (
                myIsInCombat && myTurnAllocator.peekNextTurn() &&
                isValidDirection(theDirection)
        ) {
            if (Util.probabilityTest(SpeedTest.evaluate(
                    myAdventurer, getCurrentRoom().getMonster()
            ))) {
                myIsInCombat = false;

                return Stream.concat(
                        Arrays.stream(moveAdventurerUnchecked(theDirection)),
                        Stream.of(AttackResult.FLED_SUCCESSFULLY.toString())
                ).toArray(String[]::new);
            }

            nextTurn();
            return advanceInCombatAndCompileResults(
                    AttackResultAndAmount.getNoAmount(
                            AttackResult.COULD_NOT_FLEE
                    )
            );
        }

        return null;
    }

    public boolean isValidDirection(final Direction theDirection) {
        return getCurrentRoom().hasDoor(theDirection);
    }

    public String[] moveAdventurer(final Direction theDirection) {
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

    public String[] useStairs(final boolean theIsUp) {
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

    private String[] moveAdventurerUnchecked(final Direction theDirection) {
        return moveToCoordsUnchecked(myAdventurerCoordinates.add(
                theDirection, myDungeon.getDimensions()
        ));
    }

    private String[] moveToCoordsUnchecked(final RoomCoordinates theCoords) {
        if (!theCoords.isSameRoom(myAdventurerCoordinates)) {
            myAdventurerCoordinates = theCoords;
            myDungeon.getMap().explore(myAdventurerCoordinates);
            getCurrentRoom().activateTrap(myAdventurer);

            return new String[]{
                    advanceOutOfCombat(),
                    getCurrentRoom().activateTrap(myAdventurer).toString()
            };
        }

        return null;
    }

    private void testDead(final AttackResultAndAmount theResult) {
        if (theResult.getResult() == AttackResult.KILL) {
            myIsDead = true;
        }
    }

    private String[] runMonsterTurn() {
        final AttackResultAndAmount attackResult =
                getCurrentRoom().getMonster().attemptDamage(
                        myAdventurer, true
                );
        testDead(attackResult);

        nextTurn();

        return advanceInCombatAndCompileResults(attackResult);
    }

    private String[] runAdventurerTurn(final boolean theIsBasicAttack) {
        requireAlive();

        if (myIsInCombat && myTurnAllocator.peekNextTurn()) {
            AttackResultAndAmount attackResult;
            if (theIsBasicAttack) {
                attackResult = getCurrentRoom().attackMonster(myAdventurer);
            } else {
                attackResult = getCurrentRoom().killMonsterOnKillResult(
                        myAdventurer.useSpecialSkill(
                                myAdventurer, getCurrentRoom().getMonster()
                        )
                );
            }

            if (attackResult.getResult() != AttackResult.EXTRA_TURN) {
                nextTurn();
            }

            return advanceInCombatAndCompileResults(attackResult);
        }

        return null;
    }

    private String[] advanceInCombatAndCompileResults(final AttackResultAndAmount theAttackResult) {
        return new String[]{advanceInCombat(), theAttackResult.toString()};
    }

    private String advanceOutOfCombat() {
        testEnterCombat();

        return myAdventurer.advanceDebuffs().toString();
    }

    private String advanceInCombat() {
        myAdventurer.getSpecialSkill().advance();

        final AttackResultAndAmount result =
                myAdventurer.advanceBuffsAndDebuffs();
        testDead(result);

        return result.toString();
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
                myAdventurer.getSpeed(),
                getCurrentRoom().getMonster().getSpeed()
        );
    }

    private void nextTurn() {
        if (testCombat() && myTurnAllocator.isCompleted()) {
            recalculateTurnAllocation();
        }

        myTurnAllocator.nextTurn();
    }

    private void requireAlive() {
        if (myIsDead) {
            throw new IllegalStateException(
                    "The adventurer is dead, and no actions other than " +
                    "viewing the final game state are allowed."
            );
        }
    }
}
