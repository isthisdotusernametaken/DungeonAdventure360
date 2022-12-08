package model;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {

    private static final Item[] ALL_ITEMS = createAllItems();

    static Item createRandom() {
        return ALL_ITEMS[Util.randomIntExc(ALL_ITEMS.length)].copy();
    }

    private static Item[] createAllItems() {
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
