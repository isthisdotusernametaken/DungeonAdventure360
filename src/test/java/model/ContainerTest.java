package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTest {

    private final boolean[] theBoolean = {true};
    private Container myContainer = new Container(
            new VisionPotion(2),
            new BuffPotion(1, BuffType.STRENGTH)
    );

    private Monster myMonster = new Monster(
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

    Trap myTrap = new Trap(
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

    Map myMap = new ArrayMap(new RoomCoordinates(1,2,3));
    Item myItem = new VisionPotion(1);
    Room myRoom = new Room(theBoolean, myTrap, myMonster, true, true, myItem );
    RoomCoordinates myCoords = new RoomCoordinates(1,2,3);

    @Test
    void testGetSize() {
         assertEquals(2,myContainer.size());
    }

    @Test
    void testViewItemAsStrings() {
        assertEquals("[Vision Potion: 2, Strength Potion: 1]", Arrays.toString(myContainer.viewItemsAsStrings()));
    }

    @Test
    void testViewItems() {
        Item[] actual = myContainer.viewItems();
        Item[] expected = new Item[myContainer.size()];
        expected[0] =  new VisionPotion(2);
        expected[1] =  new BuffPotion(1, BuffType.STRENGTH);

        assertEquals(expected[0].getType(), actual[0].getType());
    }

    @Test
    void testUseItem() {
        myContainer.useItem(1, myMonster, myMap, myRoom, myCoords, true); //Not sure about this one
        assertEquals(2,myContainer.size());
    }

    @Test
    void testAddItem() {
        myContainer.addItem(new HealthPotion(1));
        assertEquals(3,myContainer.size());
        assertEquals("[Vision Potion: 2, Strength Potion: 1, Health Potion: 1]",
                Arrays.toString(myContainer.viewItemsAsStrings()));
    }
}
