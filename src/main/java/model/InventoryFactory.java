/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This factory creates a Container for the player's inventory.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class InventoryFactory {

    /**
     * The number of health potions on the easiest difficulty, decreasing by
     * one for each higher difficulty level
     */
    private static final int BASE_HEALTH_POTION_COUNT = 3;

    /**
     * Creates a Container with health potions and another random item
     * (possibly also a health potion) as the initial inventory.
     *
     * @param theDifficulty Determines number of health potions.
     * @return The filled inventory.
     */
    static Container createWithInitialItems(final Difficulty theDifficulty) {
        final Container inventory = new Container(new HealthPotion(
                BASE_HEALTH_POTION_COUNT - theDifficulty.ordinal()
        ));

        inventory.addItem(ItemFactory.createRandom());

        return inventory;
    }
}
