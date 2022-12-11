package model;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {

    private static final Item[] ALL_ITEMS = createAllItemsWeighted();

    static List<String> getItemsAndRepresentation() {
        final List<String> itemsAndRepresentations = new ArrayList<>();

        for (Item item : createAllItemsMaxed()) {
            itemsAndRepresentations.add(
                    item.charRepresentation() + ": " + item.getName()
            );
        }

        return itemsAndRepresentations;
    }

    static Item createRandom() {
        return ALL_ITEMS[Util.randomIntExc(ALL_ITEMS.length)].copy();
    }

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
