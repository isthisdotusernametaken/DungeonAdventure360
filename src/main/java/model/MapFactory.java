/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This factory class helps to create dungeon map
 * let subclasses use it to prevent duplication of code.
 */
public class MapFactory {

    /**
     * Template method to create the dungeon map.
     *
     * @param theDimensions The dimensions of the dungeon room.
     * @return The dungeon map in array.
     */
    static Map create(final RoomCoordinates theDimensions) {
        return new ArrayMap(theDimensions);
    }
}
