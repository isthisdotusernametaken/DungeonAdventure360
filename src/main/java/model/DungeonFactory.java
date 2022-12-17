/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This factory class helps to create dungeon game
 * let subclasses use it to prevent duplication of code.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class DungeonFactory {

    /**
     * Template method to create the dungeon.
     *
     * @param theDifficulty The difficulty level of the dungeon adventure game.
     * @return The dungeon in array.
     */
    static Dungeon create(final Difficulty theDifficulty) {
        return new ArrayDungeon(theDifficulty);
    }
}
