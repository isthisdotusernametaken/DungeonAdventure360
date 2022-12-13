package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisionPotionTest {
    private static final VisionPotion myVisionPotion = new VisionPotion(99);
    private static final RoomCoordinates myCoordinates = new RoomCoordinates(1,2,2);
    private static final Map myMap = new ArrayMap(myCoordinates);

    @Test
    void testApplyEffect() {
        boolean expected = false;

        assertEquals(expected, myVisionPotion.applyEffect(myMap, myCoordinates)); //Not sure about this
    }

    @Test
    void testCopy() {
        Item myItem = new VisionPotion(2);
        ItemType expected = myItem.getType();

        assertEquals(expected, myVisionPotion.getType());
    }

    @Test
    void testGetName() {
        String expected = "Vision Potion";

        assertEquals(expected, myVisionPotion.getName());
    }
}
