package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import static model.DungeonFactory.*;

public class DungeonFactoryTest {

    private static Dungeon createTestHelper() {
        DungeonAdventure.buildFactories();
        return create(Difficulty.NORMAL);
    }

    @Test
    void testCreateNotNull() {
        assertNotNull(createTestHelper());
    }

    @Test
    void testCreateNoException() {
        assertDoesNotThrow(DungeonFactoryTest::createTestHelper);
    }

    @Test
    void testCreateCorrectType() {
        assertEquals(ArrayDungeon.class, createTestHelper().getClass());
    }
}
