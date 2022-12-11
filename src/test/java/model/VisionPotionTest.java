package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisionPotionTest {
    private final VisionPotion myVisionPotion = new VisionPotion(99);
    private final RoomCoordinates myCoordinates = new RoomCoordinates(1,2,2);
    private final Map myMap = new ArrayMap(myCoordinates);

    @Test
    void testApplyEffect() {
        assertEquals(false, myVisionPotion.applyEffect(myMap, myCoordinates)); //Not sure about this
    }

    @Test
    void testCopy() {
        Item expected = new VisionPotion(2);

        assertEquals(expected.getType(), myVisionPotion.getType());
    }

    @Test
    void testGetName() {
        String expected = "Vision Potion";

        assertEquals(expected, myVisionPotion.getName());
    }
}
