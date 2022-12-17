package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory produces Items for Monster drops, initial Room contents, and
 * initial Inventory contents.
 */
public class ItemFactory {

    /**
     * A list containing all usable items.
     */
    private static final Item[] ALL_ITEMS = createAllItemsWeighted();

    /**
     * Gets All usable items and their character representations.
     *
     * @return The string list representing the items and
     *         their character representations.
     */
    static List<String> getItemsAndRepresentation() {
        final List<String> itemsAndRepresentations = new ArrayList<>();

        for (Item item : createAllItemsMaxed()) {
            itemsAndRepresentations.add(
                    item.charRepresentation() + ": " + item.getName()
            );
        }

        return itemsAndRepresentations;
    }

    /**
     * Creates a random item from the list containing all usable items.
     *
     * @return A random usable item.
     */
    static Item createRandom() {
        return ALL_ITEMS[Util.randomIntExc(ALL_ITEMS.length)].copy();
    }

    /**
     * Creates a max stack of each usable item.
     *
     * @return The item array containing the generated items
     *         with maximum count.
     */
    static Item[] createAllItemsMaxed() {
        final List<Item> items = new ArrayList<>(List.of(
                new HealthPotion(Item.MAX_STACK_SIZE),
                new VisionPotion(Item.MAX_STACK_SIZE),
                new Planks(Item.MAX_STACK_SIZE)
        ));

        for (BuffType buffType : BuffType.getAllPositiveBuffTypes()) {
            items.add(new BuffPotion(Item.MAX_STACK_SIZE, buffType));
        }

        return items.toArray(new Item[0]);
    }

    /**
     * Create a list of usable items with 1 count, giving more weight to some
     * items so that they are more likely to be randomly selected.
     *
     * @return A weighted collection of usable items with 1 count.
     */
    private static Item[] createAllItemsWeighted() {
        final List<Item> items = new ArrayList<>(List.of(
                new HealthPotion(1),
                new HealthPotion(1),
                new HealthPotion(1),
                new VisionPotion(1),
                new Planks(1)
        ));

        for (BuffType buffType : BuffType.getAllPositiveBuffTypes()) {
            items.add(new BuffPotion(1, buffType));
        }

        return items.toArray(new Item[0]);
    }
}
