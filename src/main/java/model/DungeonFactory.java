package model;

public class DungeonFactory {

    static Dungeon create(final RoomCoordinates theDimensions,
                          final Difficulty theDifficulty) {
        return new ArrayDungeon(theDimensions, theDifficulty);
    }
}
