package model;

/**
 * This factory class helps to create dungeon game
 * let subclasses use it to prevent duplication of code.
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
