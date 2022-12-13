package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapApplicableItemTest {

    private static final MapApplicableItem myMapApplicableItem = new VisionPotion(2);
    private static final RoomCoordinates myCoordinates = new RoomCoordinates(10,4,4);
    private static final Map myMap = new ArrayMap(myCoordinates);

    @Test
    void testUse() {
        String expected = "";

        assertEquals(expected, myMapApplicableItem.use(myMap, myCoordinates));
    }
}
