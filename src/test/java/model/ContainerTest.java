package model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ContainerTest {

    private static final boolean[] DOORS = {true, true, true, true};
    private static final Container CONTAINER = new Container(
            new VisionPotion(2),
            new BuffPotion(1, BuffType.STRENGTH)
    );
    private static final Monster MONSTER = new Monster(
            "Skeleton",
            "Skeleton",
            110,
            15,
            25,
            0.5,
            0.1,
            2,
            DamageType.SHARP,
            4,
            0.05,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2})
    );
    private static final Trap TRAP = new Trap(
            "Pit",
            true,
            true,
            1,
            2,
            0.0,
            0.0,
            0,
            DamageType.SHARP,
            0,
            'P');
    private static final Map MAP = new ArrayMap(new RoomCoordinates(1,2,3));
    private static final Item ITEM = new VisionPotion(1);
    private static final Room ROOM = new Room(DOORS, TRAP, MONSTER, true, true, ITEM);
    private static final RoomCoordinates COORDS = new RoomCoordinates(1,2,3);

    @Test
    void testViewItemAsStrings() {
        String expected = "[Vision Potion: 2, Strength Potion: 1]";

        assertEquals(expected, Arrays.toString(CONTAINER.viewItemsAsStrings()));
    }

    @Test
    void testViewItems() {
        Item[] actual = CONTAINER.viewItems();
        Item[] expected = new Item[2];
        expected[0] =  new VisionPotion(2);
        expected[1] =  new BuffPotion(1, BuffType.STRENGTH);

        assertEquals(expected[0].getType(), actual[0].getType());
    }

    @Test
    void testHasItemsEmpty() {
        assertFalse(new Container().hasItems());
    }

    @Test
    void testHasItemsNotEmpty() {
        assertTrue(new Container(new HealthPotion(1)).hasItems());
    }

    @Test
    void testCanUseNotCharacterApplicableItemNotInCombat() {
        assertTrue(new Container(new VisionPotion(1)).canUse(0,  false));
    }

    @Test
    void testCanUseCharacterApplicableItemInCombat() {
        assertTrue(new Container(new HealthPotion(1)).canUse(0,  true));
    }

    @Test
    void testCanUseNotCharacterApplicableItemInCombat() {
        assertFalse(new Container(new VisionPotion(1)).canUse(0,  true));
    }

    @Test
    void testCanUseInvalidIndex() {
        assertFalse(CONTAINER.canUse(10,  false));
    }

    @Test
    void testUseItemCharacterApplicableItem() {
        final Container container = new Container(new HealthPotion(1));
        MONSTER.myHP--; // So health potion works

        assertEquals(
                "Healed 1 hp and cleared all debuffs.",
                container.useItem(0, MONSTER, MAP, ROOM, COORDS, true)
        );
        assertEquals(0, container.viewItems().length);
    }

    @Test
    void testUseItemMapApplicableItemInCombat() {
        final Container container = new Container(new VisionPotion(1));

        assertEquals(
                Util.NONE, // Should be prevented by UI after canUse check
                container.useItem(
                        0, null,
                        MapFactory.create(new RoomCoordinates(1, 5, 5)),
                        null, new RoomCoordinates(1, 2, 2),
                        true
                )
        );
        assertEquals(1, container.viewItems().length);
    }

    @Test
    void testUseItemMapApplicableItemNotInCombat() {
        final Container container = new Container(new VisionPotion(1));

        assertEquals(
                MapApplicableItem.MAP_UPDATED,
                container.useItem(
                        0, null,
                        MapFactory.create(new RoomCoordinates(1, 5, 5)),
                        null, new RoomCoordinates(0, 2, 2),
                        false
                )
        );
        assertEquals(0, container.viewItems().length);
    }

    @Test
    void testUseItemRoomApplicableItemInCombat() {
        final Container container = new Container(new Planks(1));
        TRAP.myIsBroken = false;

        assertEquals(
                Util.NONE, // Should be stopped by UI after canUse check
                container.useItem(0, null, null, ROOM, null, true)
        );
        assertEquals(1, container.viewItems().length);
        assertFalse(TRAP.isBroken());
    }

    @Test
    void testUseItemRoomApplicableItemNotInCombat() {
        final Container container = new Container(new Planks(1));
        TRAP.myIsBroken = false;

        assertEquals(
                Planks.SUCCESS_MSG,
                container.useItem(0, null, null, ROOM, null, false)
        );
        assertEquals(0, container.viewItems().length);
        assertTrue(TRAP.isBroken());

        TRAP.myIsBroken = false;
    }

    @Test
    void testAddItem() {
        final String expected = "[Vision Potion: 2, Strength Potion: 1, Health Potion: 1]";
        final Container container = new Container(CONTAINER.viewItems());
        container.addItem(new HealthPotion(1));

        assertEquals(expected, Arrays.toString(container.viewItemsAsStrings()));
    }

    @Test
    void testAddItems() {
        final Container container = new Container(CONTAINER.viewItems());
        container.addItems(new Item[]{
                new HealthPotion(1), new VisionPotion(2)
        });

        assertEquals(3, container.viewItems().length);
        assertEquals(4, container.viewItems()[0].getCount()); // Vision pots
    }

    @Test
    void testClearItems() {
        final Container container = new Container(CONTAINER.viewItems());
        assertTrue(container.hasItems());

        container.clearItems();
        assertFalse(container.hasItems());
    }

    @Test
    void testHasAllPillarsNoPillars() {
        assertFalse(new Container().hasAllPillars());
    }

    @Test
    void testHasAllPillarsSomePillars() {
        assertFalse(new Container(Pillar.createPillars()[1]).hasAllPillars());
    }

    @Test
    void testHasAllPillarsAllPillars() {
        // Don't need to consider case of duplicate pillars, since pillars
        // guaranteed to only be generated once per dungeon by
        // ArrayDungeon.MazeGenerator, no cheats to add pillars, and
        // Item.addToStack does nothing if a player tries to add a duplicate
        // Pillar to their inventory
        assertTrue(new Container(Pillar.createPillars()).hasAllPillars());
    }
}
