package model;

public class DungeonFactory {

    static Dungeon create(final Difficulty theDifficulty) {
        return new ArrayDungeon(theDifficulty);
    }
}
