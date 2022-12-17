/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory class helps to handle, access, and modify the item
 *  and let subclasses use it to prevent duplication of code.
 */
public class ItemFactory {

    /**
     * A list containing all usable items.
     */
    private static final Item[] ALL_ITEMS = createAllItemsWeighted();

    /**
     * Gets the item and its character representation.
     *
     * @return The string list representing the item and
     *          its character representation.
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
     * Creates random items using the list containing all
     * usable items and put it back into the list.
     *
     * @return The list containing all usable items.
     */
    static Item createRandom() {
        return ALL_ITEMS[Util.randomIntExc(ALL_ITEMS.length)].copy();
    }

    /**
     * Create a list of usable items with maximum count.
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
     * Create a list of usable items with 1 count.
     *
     * @return The item array containing the generated items
     *         with 1 count.
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
