package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import static model.InventoryFactory.*;

public class InventoryFactoryTest {

    private static final Difficulty DIFFICULTY = Difficulty.EASY;

    private static Container createWithInitialItemsTestHelper() {
        return createWithInitialItems(DIFFICULTY);
    }

    @Test
    void testCreateWithInitialItemsNotNull() {
        assertNotNull(createWithInitialItemsTestHelper());
    }

    @Test
    void testCreateWithInitialItemsNoException() {
        assertDoesNotThrow(
                InventoryFactoryTest::createWithInitialItemsTestHelper
        );
    }

    @Test
    void testCreateWithInitialItemsValidItemCount() {
        boolean firstNotChecked = true, lastNotChecked = true;

        while (firstNotChecked || lastNotChecked) {
            final Item[] items = createWithInitialItemsTestHelper().viewItems();
            if (items.length == 1) {
                // Health pots and extra item was also health pot
                assertEquals(getHealthPotionCount() + 1, items[0].getCount());
                firstNotChecked = false;
            } else {
                // Health pots + extra random item
                assertEquals(2, items.length);
                lastNotChecked = false;
            }
        }
    }

    private int getHealthPotionCount() {
        return BASE_HEALTH_POTION_COUNT - DIFFICULTY.ordinal();
    }
}
