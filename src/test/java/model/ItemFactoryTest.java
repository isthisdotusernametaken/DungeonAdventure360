package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.ItemFactory.*;

public class ItemFactoryTest {

    @Test
    void testGetItemsAndRepresentationsSize() {
        assertEquals( // # of reps should be # of different items
                createAllItemsMaxed().length,
                getItemsAndRepresentation().size()
        );
    }

    @Test
    void testGetItemsAndRepresentationsAllNotNullOrEmpty() {
        for (String rep : getItemsAndRepresentation()) {
            assertNotNull(rep);
            assertNotEquals(0, rep.length());
        }
    }

    @Test
    void testCreateRandomNonNull() {
        assertNotNull(createRandom());
    }

    @Test
    void testCreateRandomOnlyOne() {
        assertEquals(1, createRandom().getCount());
    }

    @Test
    void testCreateAllItemsMaxedIncludesAllTypes() {
        final Item[] expectedItems = createAllItemsMaxedManually();
        final Item[] items = createAllItemsMaxed();

        Item expectedItem;
        Item item;
        assertEquals(expectedItems.length, items.length);
        for (int i = 0; i < expectedItems.length; i++) {
            expectedItem = expectedItems[i];
            item = items[i];

            assertEquals(expectedItem.getType(), item.getType());
            if (expectedItem.getType() == ItemType.BUFF_POTION) {
                assertEquals(
                        ((BuffPotion) expectedItem).getBuffType(),
                        ((BuffPotion) item).getBuffType()
                );
            }
        }
    }

    @Test
    void testCreateAllItemsMaxedCorrectCounts() {
        for (Item item : createAllItemsMaxed()) {
            assertEquals(Item.MAX_STACK_SIZE, item.getCount());
        }
    }

    @Test
    void testCreateAllItemsWeightedIncludesAllTypes() {
        final List<Item> weighted = List.of(createAllItemsWeighted());
        final List<ItemType> weightedTypes =
                weighted.stream().map(Item::getType).toList();
        final List<BuffType> weightedBuffTypes =
                weighted.stream().map(
                        item -> item.getType() == ItemType.BUFF_POTION ?
                                ((BuffPotion) item).getBuffType() :
                                BuffType.NONE
                ).toList();

        for (Item item : createAllItemsMaxedManually()) {
            assertTrue(weightedTypes.contains(item.getType()));

            if (item.getType() == ItemType.BUFF_POTION) {
                assertTrue(weightedBuffTypes.contains(
                        ((BuffPotion) item).getBuffType()
                ));
            }
        }
    }

    private Item[] createAllItemsMaxedManually() {
        return new Item[]{
                new HealthPotion(Item.MAX_STACK_SIZE),
                new VisionPotion(Item.MAX_STACK_SIZE),
                new Planks(Item.MAX_STACK_SIZE),
                new BuffPotion(Item.MAX_STACK_SIZE, BuffType.STRENGTH),
                new BuffPotion(Item.MAX_STACK_SIZE, BuffType.SPEED),
                new BuffPotion(Item.MAX_STACK_SIZE, BuffType.ACCURACY),
                new BuffPotion(Item.MAX_STACK_SIZE, BuffType.RESISTANCE)
        };
    }
}
