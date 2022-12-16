package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VisionPotionTest {

    private static final VisionPotion VISION_POTION = new VisionPotion(99);

    @Test
    void testApplyEffectSuccess() {
        final Map map = new ArrayMap(new RoomCoordinates(1, 3, 3));

        assertTrue(VISION_POTION.applyEffect(
                map, new RoomCoordinates(0, 0, 0)
        ));
    }

    @Test
    void testApplyEffectFail() {
        final Map map = new ArrayMap(new RoomCoordinates(1, 3, 3));

        VISION_POTION.applyEffect(map, new RoomCoordinates(0, 0, 0));
        assertFalse(VISION_POTION.applyEffect( // Already explored
                map, new RoomCoordinates(0, 0, 0)
        ));
    }

    @Test
    void testCopyType() {
        assertEquals(VISION_POTION.getType(), VISION_POTION.copy().getType());
    }

    @Test
    void testCopyCount() {
        assertEquals(VISION_POTION.getCount(), VISION_POTION.copy().getCount());
    }

    @Test
    void testGetName() {
        String expected = "Vision Potion";

        assertEquals(expected, VISION_POTION.getName());
    }
}
