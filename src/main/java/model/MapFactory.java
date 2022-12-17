package model;

/**
 * This factory class helps to create dungeon map
 * let subclasses use it to prevent duplication of code.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
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
