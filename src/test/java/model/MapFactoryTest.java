package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import static model.MapFactory.*;

public class MapFactoryTest {

    private static Map createTestHelper() {
        return create(new RoomCoordinates(1, 1, 1));
    }

    @Test
    void testCreateNotNull() {
        assertNotNull(createTestHelper());
    }

    @Test
    void testCreateNoException() {
        assertDoesNotThrow(MapFactoryTest::createTestHelper);
    }

    @Test
    void testCreateCorrectType() {
        assertEquals(ArrayMap.class, createTestHelper().getClass());
    }
}
