package model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testAddItem() {
        final String expected = "[Vision Potion: 2, Strength Potion: 1, Health Potion: 1]";
        final Container container = new Container(CONTAINER.viewItems());
        container.addItem(new HealthPotion(1));

        assertEquals(expected, Arrays.toString(container.viewItemsAsStrings()));
    }
}
