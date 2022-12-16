package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.AttackResult.*;
import static model.Difficulty.*;
import static model.Direction.*;
import static model.Room.*;
import static model.TestingUtil.*;

public class RoomTest {

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testConstructorWithNotNullMonsterAndTrap() {
        final boolean[] doors = {true, true, true, true};
        final Monster monster =
                MonsterFactory.getInstance().createRandom(Difficulty.NORMAL);
        final Trap trap =
                TrapFactory.getInstance().createRandom(Difficulty.NORMAL);
        final Room room = new Room(doors, trap, monster, true, false);

        assertNotEquals(doors, room.myDoors);
        assertArrayEquals(doors, room.myDoors);
        assertEquals(monster.getName(), room.myMonsterName);
        assertNotNull(room.myMonster);
        assertNotNull(room.myTrap);
        assertTrue(room.isEntrance());
        assertFalse(room.isExit());
    }

    @Test
    void testConstructorWithNullMonsterAndTrap() {
        final boolean[] doors = {true, true, true, true};
        final Room room = new Room(doors, null, null, false, true);

        assertNotEquals(doors, room.myDoors);
        assertArrayEquals(doors, room.myDoors);
        assertEquals("", room.myMonsterName);
        assertNull(room.myTrap);
        assertNull(room.myMonster);
        assertFalse(room.isEntrance());
        assertTrue(room.isExit());
    }

    @Test
    void testGetContentTypesAndRepresentations() {
        assertLinesMatch(
                List.of(
                        ADVENTURER + ": Adventurer",
                        MONSTER + ": Monster",
                        ENTRANCE + ": Entrance",
                        EXIT + ": Exit",
                        BROKEN_TRAP + ": Broken Trap",
                        WALL + ": Wall",
                        HORIZONTAL_DOOR + " or " + VERTICAL_DOOR + ": Door",
                        MORE + ": More contents than could be displayed"
                ),
                getContentTypesAndRepresentations()
        );
    }

    @Test
    void testToStringNoParam() {
        final Room room = buildRoom(false, false);
        assertEquals(room.toString(false), room.toString());
    }

    @Test
    void testToStringWithAdventurer() {
        assertTrue(
                buildRoom(false, false).toString(true)
                        .contains("" + ADVENTURER)
        );
    }

    @Test
    void testToStringWithoutAdventurer() {
        assertFalse(
                buildRoom(false, false).toString(false)
                        .contains("" + ADVENTURER)
        );
    }

    @Test
    void testToStringWithItems() {
        final Item item = new HealthPotion(1);
        assertTrue(
                buildRoom(false, false, item).toString(false)
                        .contains("" + item.charRepresentation())
        );
    }

    @Test
    void testToStringEmptyRoom() {
        for (char character : buildRoom(false, false).toString().toCharArray()) {
            switch (character) {
                case WALL, EMPTY, VERTICAL_DOOR, HORIZONTAL_DOOR, '\n':
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void testGetContainerEmpty() {
        assertFalse(buildRoom(false, false).myContainer.hasItems());
    }

    @Test
    void testGetContainerNotEmpty() {
        final Container inventory = new Container();
        final Room room = buildRoom(false, false, new Planks(1));

        room.collectItems(inventory);
        assertTrue(inventory.hasItems());
        assertFalse(room.myContainer.hasItems());
    }

    @Test
    void testCollectItemsEmpty() {
        final Container inventory = new Container();
        final Room room = buildRoom(false, false);

        room.collectItems(inventory);
        assertFalse(inventory.hasItems());
        assertFalse(room.myContainer.hasItems());
    }

    @Test
    void testHasDoor() {
        final boolean[] doors = {true, false, true, false};
        final Room room = new Room(doors, null, null, false, false);

        int i = 0;
        for (Direction direction : Direction.values()) {
            assertEquals(doors[i++], room.hasDoor(direction));
        }
    }

    @Test
    void testIsEntrance() {
        assertTrue(
                new Room(
                        new boolean[]{true, true, true, true},
                        null, null, true, false
                ).isEntrance()
        );
        assertFalse(
                new Room(
                        new boolean[]{true, true, true, true},
                        null, null, false, false
                ).isEntrance()
        );
    }

    @Test
    void testIsExit() {
        assertTrue(
                new Room(
                        new boolean[]{true, true, true, true},
                        null, null, false, true
                ).isExit()
        );
        assertFalse(
                new Room(
                        new boolean[]{true, true, true, true},
                        null, null, false, false
                ).isExit()
        );
    }

    @Test
    void testActivateTrapNull() {
        assertEquals(
                NO_ACTION,
                buildRoom(false, false).activateTrap(
                        AdventurerFactory.getInstance().createRandom(EASY)
                ).getResult()
        );
    }

    @Test
    void testActivateTrapNotNull() {
        final AttackResult result = buildRoom(false, true).activateTrap(
                AdventurerFactory.getInstance().createRandom(EASY)
        ).getResult();

        assertNotEquals(NO_ACTION, result);
        assertIsAttemptDamageResultType(result, DODGE);
    }

    @Test
    void testBoardTrapNotNull() {
        final Trap trap = TrapFactory.getInstance().create(0, EASY);
        assertTrue(
                new Room(
                        new boolean[]{true, true, true, true},
                        trap, null, false, false
                ).boardTrap()
        );
    }

    @Test
    void testBoardTrapNull() {
        assertFalse(buildRoom(false, false).boardTrap());
    }

    @Test
    void testGetTrapClassNotNull() {
        final Trap trap = TrapFactory.getInstance().createRandom(EASY);
        assertEquals(
                trap.getClassName(),
                new Room(
                        new boolean[]{true, true, true, true},
                        trap, null, false, false
                ).getTrapClass()
        );
    }

    @Test
    void testGetTrapClassNull() {
        assertEquals(Util.NONE, buildRoom(false, false).getTrapClass());
    }

    @Test
    void testGetMonster() {
        final Room room = buildRoom(true, false);
        assertEquals(room.myMonster, room.getMonster());
    }

    @Test
    void testGetMonsterName() {
        final Monster monster = MonsterFactory.getInstance().createRandom(EASY);
        assertEquals(
                monster.getName(),
                new Room(
                        new boolean[]{true, true, true, true},
                        null, monster, false, false
                ).getMonsterName()
        );
    }

    @Test
    void testAttackMonsterNotNull() {
        assertIsAttemptDamageResultType(
                buildRoom(true, false).attackMonster(
                        AdventurerFactory.getInstance().createRandom(EASY)
                ).getResult()
        );
    }

    @Test
    void testAttackMonsterNull() {
        assertEquals(
                NO_ACTION,
                buildRoom(false, false).attackMonster(
                        AdventurerFactory.getInstance().createRandom(EASY)
                ).getResult()
        );
    }

    @Test
    void testKillMonsterOnKillResultKill() {
        final Room room = buildRoom(true, false);
        room.killMonsterOnKillResult(AttackResultAndAmount.getNoAmount(KILL));

        assertNull(room.myMonster);
    }

    @Test
    void testKillMonsterOnKillResultNotKill() {
        final Room room = buildRoom(true, false);
        room.killMonsterOnKillResult(AttackResultAndAmount.getNoAmount(HEAL));

        assertNotNull(room.myMonster);
    }

    @Test
    void testAppendHorizontalWall() {
        final StringBuilder builder = new StringBuilder();
        buildRoom(false, false).appendHorizontalWall(builder, NORTH);
        assertEquals(
                "**-**\n",
                builder.toString()
        );
    }

    @Test
    void testStepBackSameRow() {
        final int[] position = {1, 2};
        buildRoom(false, false).stepBack(position);
        assertArrayEquals(new int[]{1, 1}, position);
    }

    @Test
    void testStepBackPreviousRow() {
        final int[] position = {1, 0};
        buildRoom(false, false).stepBack(position);
        assertArrayEquals(new int[]{0, ROOM_SIZE - 1}, position);
    }

    @Test
    void testContentsFullNotFull() {
        final char[][] contents = {{'a'}};
        assertFalse(
                buildRoom(false, false)
                        .testContentsFull(contents, new int[]{0, 0})
        );
        assertEquals('a', contents[0][0]);
    }

    @Test
    void testContentsFullFull() {
        final char[][] contents = {{'a'}};
        assertTrue(
                buildRoom(false, false)
                        .testContentsFull(contents, new int[]{-1, 0})
        );
        assertEquals(MORE, contents[0][0]);
    }

    private Room buildRoom(final boolean theHasMonster,
                           final boolean theHasTrap,
                           final Item ... theItems) {
        return new Room(
                new boolean[]{true, true, true, true},
                theHasTrap ?
                        TrapFactory.getInstance()
                                   .createRandom(Difficulty.NORMAL) :
                        null,
                theHasMonster ?
                        MonsterFactory.getInstance()
                                .createRandom(Difficulty.NORMAL) :
                        null,
                false,
                false,
                theItems
        );
    }
}
