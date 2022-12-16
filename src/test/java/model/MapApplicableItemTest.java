package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MapApplicableItemTest {
    @Test
    void testUse() {
        final MapApplicableItem mapApplicable = new VisionPotion(6);

        assertEquals(
                MapApplicableItem.MAP_UPDATED,
                mapApplicable.use(
                        new ArrayMap(new RoomCoordinates(2,4,4)),
                        new RoomCoordinates(0, 0, 0)
                )
        );
    }
}
