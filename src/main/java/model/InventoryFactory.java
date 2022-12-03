package model;

public class InventoryFactory {

    private static final int BASE_HEALTH_POTION_COUNT = 3;

    static Container createWithInitialItems(final Difficulty theDifficulty) {
        final Container inventory = new Container(new HealthPotion(
                BASE_HEALTH_POTION_COUNT - theDifficulty.ordinal()
        ));

        inventory.addItem(ItemFactory.createRandom());

        return inventory;
    }
}
