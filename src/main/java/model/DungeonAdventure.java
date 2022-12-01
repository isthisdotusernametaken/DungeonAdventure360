package model;

import java.io.Serializable;

public class DungeonAdventure implements Serializable {

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

        myAdventurer = AdventurerFactory.getInstance()
                .create(theAdventurerClass, theDifficulty);
        myAdventurer.setName(theAdventurerName);

        myInventory = InventoryFactory.createWithInitialItems(theDifficulty);

        myIsDead = false;
        // myInCombat false (myTurnAllocator irrelevant)
    }

    public static boolean buildFactories() {
        try {
            DBManager dbManager = new SQLiteDBManager();
            AdventurerFactory.buildInstance();
        }
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

    public String getAdventurer() {
        return myAdventurer.toString();
    }

    public String getAdventurerCoords() {
        return myAdventurerCoordinates.toString();
    }

    public String getMap() {
        return myDungeon.view(true);
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

    public String tryNextCombatTurn() {
        requireAlive();

        if (myIsInCombat) {
            if (myTurnAllocator.peekNextTurn()) {
                return Util.NONE; // Adventurer's turn
            }

            return runMonsterTurn().toString();
        }

        return Util.NONE; // Also waiting for input
    }

    public String getMonster() {
        return getCurrentRoom().getMonster().toString();
    }

    public String attack() {
        return runAdventurerAttack(true);
    }

    public String getSpecialSkill() {
        return myAdventurer.viewSpecialSkill();
    }

    public boolean canUseSpecialSkill() {
        return myAdventurer.getSpecialSkill().canUse();
    }

    public String useSpecialSkill() {
        return runAdventurerAttack(false);
    }

    public boolean flee(final Direction theDirection) {
        requireAlive();

        if (
                myIsInCombat && myTurnAllocator.peekNextTurn() &&
                isValidDirection(theDirection)
        ) {
            if (Util.probabilityTest(SpeedTest.evaluate(
                    myAdventurer, getCurrentRoom().getMonster()
            ))) {
                myIsInCombat = false;
                moveAdventurerUnchecked(theDirection);

                return true;
            }

            nextTurn();
        }

        return false;
    }

    public boolean isValidDirection(final Direction theDirection) {
        return getCurrentRoom().hasDoor(theDirection);
    }

    public boolean moveAdventurer(final Direction theDirection) {
        requireAlive();

        return (!myIsInCombat && isValidDirection(theDirection)) &&
               moveAdventurerUnchecked(theDirection);
    }

    public boolean hasStairs(final boolean theIsUp) {
        return theIsUp ?
               myDungeon.hasStairsUp(myAdventurerCoordinates) :
               myDungeon.hasStairsDown(myAdventurerCoordinates);
    }

    public boolean useStairs(final boolean theIsUp) {
        requireAlive();

        if (!myIsInCombat && hasStairs(theIsUp)) {
            myAdventurerCoordinates = myAdventurerCoordinates.addFloor(
                    theIsUp, getDimensions().getFloor()
            );

            return advanceOutOfCombat();
        }

        return false;
    }

    private Room getCurrentRoom() {
        return myDungeon.getRoom(myAdventurerCoordinates);
    }

    private RoomCoordinates getDimensions() {
        return myDungeon.getDimensions();
    }

    private boolean moveAdventurerUnchecked(final Direction theDirection) {
        final RoomCoordinates newCoords = myAdventurerCoordinates.add(
                theDirection, myDungeon.getDimensions()
        );

        if (!newCoords.isSameRoom(myAdventurerCoordinates)) {
            myAdventurerCoordinates = newCoords;

            return advanceOutOfCombat();
        }

        return false;
    }

    private AttackResult runMonsterTurn() {
        final AttackResult result =
                getCurrentRoom().getMonster().attemptDamage(
                        myAdventurer, true
                );
        if (result == AttackResult.KILL) {
            myIsDead = true;
        }

        nextTurn();

        return result;
    }

    private String runAdventurerAttack(final boolean theIsBasicAttack) {
        requireAlive();

        if (myIsInCombat && myTurnAllocator.peekNextTurn()) {
            AttackResult result;
            if (theIsBasicAttack) {
                result = getCurrentRoom().attackMonster(myAdventurer);
            } else {
                result = getCurrentRoom().killMonsterOnKillResult(
                        myAdventurer.useSpecialSkill(
                                myAdventurer, getCurrentRoom().getMonster()
                        )
                );
            }

            nextTurn();

            return result.toString();
        }

        return Util.NONE;
    }

    private void advanceInCombat() {
        myAdventurer.advanceBuffsAndDebuffs();
        myAdventurer.getSpecialSkill().advance();
    }

    private boolean advanceOutOfCombat() {
        myAdventurer.advanceDebuffs();

        return testEnterCombat();
    }

    private boolean testEnterCombat() {
        if (testCombat()) {
            recalculateTurnAllocation();
        }

        return myIsInCombat;
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
