package model;

/**
 * This factory class helps to create the initial game inventory
 * and let subclasses use it to prevent duplication of code.
 */
public class InventoryFactory {

    /**
     * The integer value representing the given initial healing potions
     * to adventurer's inventory.
     */
    private static final int BASE_HEALTH_POTION_COUNT = 3;

    /**
     * Creates inventory with initial items.
     *
     * @param theDifficulty The difficulty of the dungeon adventure game.
     * @return              The inventory.
     */
    static Container createWithInitialItems(final Difficulty theDifficulty) {
        final Container inventory = new Container(new HealthPotion(
                BASE_HEALTH_POTION_COUNT - theDifficulty.ordinal()
        ));

        inventory.addItem(ItemFactory.createRandom());

        return inventory;
    }
}
