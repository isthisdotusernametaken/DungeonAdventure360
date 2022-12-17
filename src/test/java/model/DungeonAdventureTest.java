package model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import controller.ProgramFileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.Difficulty.*;
import static model.TestingUtil.*;

public class DungeonAdventureTest {

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
        ProgramFileManager.tryCreateInstance();
    }

    @Test
    void testConstructorInvalidClass() {
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> new DungeonAdventure("", -1, EASY),
                "Adventurer class index out of bounds: -1.\nCheck " +
                        "AdventurerFactory.isValidIndex to validate index"
        );
    }

    @Test
    void testConstructorNullDifficulty() {
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> new DungeonAdventure("", 0, null),
                "Difficulty passed to DungeonAdventure constructor must" +
                        "not be null"
        );
    }

    @Test
    void testConstructorFieldsValid() {
        final DungeonAdventure game = new DungeonAdventure("", 0, NORMAL);

        assertNotNull(game.myDungeon);
        assertTrue(
                NORMAL.getDimensions()
                        .isSameRoom(game.myDungeon.getDimensions())
        );

        assertNotNull(game.myAdventurerCoordinates);

        assertNotNull(game.myDungeon.getMap());
        assertTrue(game.myDungeon.getMap().isExplored(
                game.myDungeon.getEntrance()
        ));

        assertNotNull(game.myAdventurer);
        assertNotNull(game.myInventory);

        assertTrue(game.myIsAlive);
        assertTrue(game.myIsUnexploredHidden);
        assertFalse(game.myIsInCombat);
    }

    @Test
    void testGetAdventurerClasses() {
        assertArrayEquals(
                AdventurerFactory.getInstance().getClasses(),
                DungeonAdventure.getAdventurerClasses()
        );
    }

    @Test
    void testGetCharRepresentations() {
        final List<List<String>> representations =
                DungeonAdventure.getCharRepresentations();

        assertArrayEquals(
                Room.getContentTypesAndRepresentations()
                        .toArray(new String[0]),
                representations.get(0).toArray(new String[0])
        );
        assertArrayEquals(
                Dungeon.getMapRepresentations()
                        .toArray(new String[0]),
                representations.get(1).toArray(new String[0])
        );
        assertArrayEquals(
                ItemFactory.getItemsAndRepresentation()
                        .toArray(new String[0]),
                representations.get(2).toArray(new String[0])
        );
        assertArrayEquals(
                TrapFactory.getInstance().getClassesAndRepresentations()
                        .toArray(new String[0]),
                representations.get(3).toArray(new String[0])
        );
    }

    @Test
    void testIsValidAdventurerClassValid() {
        assertTrue(DungeonAdventure.isValidAdventurerClass(0));
    }

    @Test
    void testIsValidAdventurerClassInvalid() {
        assertFalse(DungeonAdventure.isValidAdventurerClass(-1));
    }

    @Test
    void testIsValidDifficultyValid() {
        assertTrue(DungeonAdventure.isValidDifficulty(0));
    }

    @Test
    void testIsValidDifficultyTooSmall() {
        assertFalse(DungeonAdventure.isValidDifficulty(-1));
    }

    @Test
    void testIsValidDifficultyTooLarge() {
        assertFalse(DungeonAdventure.isValidDifficulty(values().length));
    }

    @Test
    void testGetAdventurer() {
        final DungeonAdventure game = buildGame();

        assertEquals(game.myAdventurer.toString(), game.getAdventurer());
    }

    @Test
    void testGetAdventurerName() {
        final DungeonAdventure game = buildGame();

        assertEquals(game.myAdventurer.getName(), game.getAdventurerName());
    }

    @Test
    void testGetAdventurerDebuffType() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myAdventurer.getDamageType().getDebuffType().toString(),
                game.getAdventurerDebuffType()
        );
    }

    @Test
    void testGetMap() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myDungeon.toString(
                game.myAdventurerCoordinates, game.myIsUnexploredHidden
                ),
                game.getMap()
        );
    }

    @Test
    void testIsUnexploredHidden() {
        final DungeonAdventure game = buildGame();

        assertEquals(game.myIsUnexploredHidden, game.isUnexploredHidden());
    }

    @Test
    void testToggleIsUnexploredHidden() {
        final DungeonAdventure game = buildGame();

        assertTrue(game.isUnexploredHidden());
        game.toggleIsUnexploredHidden();
        assertFalse(game.isUnexploredHidden());
    }

    @Test
    void testGetRoom() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myDungeon.getRoom(game.myAdventurerCoordinates)
                        .toString(),
                game.getRoom()
        );
    }

    @Test
    void testGetInventoryItems() {
        final DungeonAdventure game = buildGame();

        assertArrayEquals(game.myInventory.viewItemsAsStrings(), game.getInventoryItems());
    }

    @Test
    void testAddMaxItems() {
        final DungeonAdventure game = buildGame();

        game.addMaxItems();

        assertTrue(
                game.myInventory.myItems.stream().map(Item::getType).toList()
                        .containsAll(
                                Arrays.stream(ItemFactory.createAllItemsMaxed())
                                        .map(Item::getType).toList()
                        )
        );
    }

    @Test
    void testCanUseInventoryItem() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myInventory.canUse(0, false),
                game.canUseInventoryItem(0)
        );
    }

    @Test
    void testUseInventoryItem() {
        final DungeonAdventure game = buildGame();

        // Is health potion, and won't use since full HP
        assertEquals(
                game.myInventory.useItem(
                        0,
                        game.myAdventurer,
                        game.myDungeon.getMap(),
                        game.getCurrentRoom(),
                        game.myAdventurerCoordinates,
                        game.myIsInCombat
                ),
                game.useInventoryItem(0)
        );
    }

    @Test
    void testRoomHasItems() {
        final DungeonAdventure game = buildGame();

        // Always false, since in entrance
        assertEquals(
                game.getCurrentRoom().getContainer().hasItems(),
                game.roomHasItems()
        );
    }

    @Test
    void testCollectItems() {
        final DungeonAdventure game = buildGame();

        // No items in room
        assertArrayEquals(new String[0], game.collectItems());
    }

    @Test
    void testCanExit() {
        final DungeonAdventure game = buildGame();

        // Not at exit and no pillars
        assertFalse(game.canExit());
    }

    @Test
    void testIsInCombat() {
        final DungeonAdventure game = buildGame();

        assertFalse(game.isInCombat());
        game.myIsInCombat = true;
        assertTrue(game.isInCombat());
    }

    @Test
    void testIsMonsterTurn() {
        final DungeonAdventure game = buildGame();

        assertFalse(game.isMonsterTurn());
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        game.myTurnAllocator = new TurnAllocator(1, 1);
        game.myTurnAllocator.nextTurn();
        assertTrue(game.isMonsterTurn());
    }

    @Test
    void testGetMonster() {
        final DungeonAdventure game = buildGame();

        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        assertEquals(
                game.getCurrentRoom().getMonster().toString(),
                game.getMonster()
        );
    }

    @Test
    void testGetMonsterName() {
        final DungeonAdventure game = buildGame();

        assertEquals(game.getCurrentRoom().getMonsterName(), game.getMonsterName());
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        assertEquals(game.getCurrentRoom().getMonsterName(), game.getMonsterName());
    }

    @Test
    void testGetMonsterDebuffType() {
        final DungeonAdventure game = buildGame();

        assertEquals(Util.NONE, game.getMonsterDebuffType());
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        assertEquals(
                game.getCurrentRoom().getMonster()
                        .getDamageType().getDebuffType().toString(),
                game.getMonsterDebuffType()
        );
    }

    @Test
    void testIsAlive() {
        final DungeonAdventure game = buildGame();

        assertTrue(game.isAlive());
        game.myIsAlive = false;
        assertFalse(game.isAlive());
    }

    @Test
    void testGetSpecialSkill() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myAdventurer.viewSpecialSkill(),
                game.getSpecialSkill()
        );
    }

    @Test
    void testCanUseSpecialSkill() {
        final DungeonAdventure game = buildGame();

        assertTrue(game.canUseSpecialSkill());
    }

    @Test
    void testIsValidDirection() {
        final DungeonAdventure game = buildGame();

        for (Direction direction : Direction.values()) {
            assertEquals(
                    game.getCurrentRoom().hasDoor(direction),
                    game.isValidDirection(direction)
            );
        }
    }

    @Test
    void testGetTrap() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.getCurrentRoom().getTrapDebuffType(),
                game.getTrapDebuffType()
        );
    }

    @Test
    void testHasStairs() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myDungeon.hasStairsUp(game.myAdventurerCoordinates),
                game.hasStairs(true)
        );
        assertEquals(
                game.myDungeon.hasStairsDown(game.myAdventurerCoordinates),
                game.hasStairs(false)
        );
    }

    @Test
    void testGetCurrentRoom() {
        final DungeonAdventure game = buildGame();

        assertEquals(
                game.myDungeon.getRoom(game.myAdventurerCoordinates),
                game.getCurrentRoom()
        );
    }

    @Test
    void testGetDimensions() {
        final DungeonAdventure game = buildGame();

        assertTrue(EASY.getDimensions().isSameRoom(game.getDimensions()));
    }

    @Test
    void testTestDeadNotKilled() {
        final DungeonAdventure game = buildGame();

        game.testDead(AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION));
        assertTrue(game.isAlive());
    }

    @Test
    void testTestDeadKilled() {
        final DungeonAdventure game = buildGame();

        game.testDead(AttackResultAndAmount.getNoAmount(AttackResult.KILL));
        assertFalse(game.isAlive());
    }

    @Test
    void testEnterCombatNoMonster() {
        final DungeonAdventure game = buildGame();

        game.testEnterCombat();
        assertFalse(game.isInCombat());
        assertNull(game.myTurnAllocator);
    }

    @Test
    void testEnterCombatMonster() {
        final DungeonAdventure game = buildGame();
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);

        game.testEnterCombat();
        assertTrue(game.isInCombat());
        assertNotNull(game.myTurnAllocator);
    }

    @Test
    void testRecalculateTurnAllocation() {
        final DungeonAdventure game = buildGame();
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        game.recalculateTurnAllocation();

        final TurnAllocator allocator = game.myTurnAllocator;
        game.recalculateTurnAllocation();
        assertNotEquals(allocator, game.myTurnAllocator);
    }

    @Test
    void testNextTurn() {
        final DungeonAdventure game = buildGame();
        game.getCurrentRoom().myMonster =
                MonsterFactory.getInstance().createRandom(EASY);
        game.myTurnAllocator = new TurnAllocator(1, 1);

        final int turn = game.myTurnAllocator.myCurrentTurn;
        game.nextTurn();
        // Adventurer turn and extra turn (0% chance) skipped
        assertEquals(turn + 2, game.myTurnAllocator.myCurrentTurn);
    }

    @Test
    void testRequireAliveNoException() {
        final DungeonAdventure game = buildGame();

        assertDoesNotThrow(game::requireAlive);
    }

    @Test
    void testRequireAliveException() {
        final DungeonAdventure game = buildGame();
        game.myIsAlive = false;

        assertThrowsWithMessage(
                IllegalStateException.class,
                game::requireAlive,
                "The adventurer is dead, and no actions other than " +
                        "viewing the final game state are allowed.\n"
        );
    }

    private DungeonAdventure buildGame() {
        return new DungeonAdventure("", 0, EASY);
    }
}
