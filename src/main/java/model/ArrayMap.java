/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;

/**
 * This class constructs and handles the dungeon map
 * using a 3D array.
 */
public class ArrayMap extends Map {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -2679508810748662333L;

    /**
     * The boolean 3D array representing the explored rooms.
     */
    private final boolean[][][] myExplored;

    /**
     * Constructor to construct the dungeon map using 3D array.
     *
     * @param theDimensions The dimensions of the rooms.
     */
    ArrayMap(final RoomCoordinates theDimensions) {
        myExplored = new boolean[theDimensions.getFloor()]
                                [theDimensions.getX()]
                                [theDimensions.getY()];
    }

    /**
     *
     * Checks if the current room is explored.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-position
     *                 of the current room.
     * @param theY     The integer value representing the y-position
     *                 of the current room.
     * @return         The boolean true or false if the room has
     *                 been explored.
     */
    @Override
    boolean isExplored(final int theFloor,
                       final int theX,
                       final int theY) {
        return isInBounds(theFloor, theX, theY) &&
               myExplored[theFloor][theX][theY];
    }

    /**
     *
     * Checks if the current room is explored.
     *
     * @param theCoords The coordinates of the current room.
     * @return          The boolean true or false if the room has
     *                  been explored.
     */
    @Override
    boolean isExplored(final RoomCoordinates theCoords) {
        return isExplored(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    /**
     * Explores the dungeon room.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-value
     *                 of the room.
     * @param theY     The integer value representing the y-value
     *                 of the room.
     */
    @Override
    void explore(final int theFloor,
                 final int theX,
                 final int theY) {
        if (isInBounds(theFloor, theX, theY)) {
            myExplored[theFloor][theX][theY] = true;
        }
    }

    /**
     * Explores the dungeon room.
     *
     * @param theCoords The coordinates of the room.
     */
    @Override
    void explore(final RoomCoordinates theCoords) {
        explore(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    /**
     * Validates if the room is in bound.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-value
     *                 of the room.
     * @param theY     The integer value representing the y-value
     *                 of the room.
     * @return         The boolean true or false if the room is
     *                 in bound.
     */
    @Override
    boolean isInBounds(final int theFloor,
                       final int theX,
                       final int theY) {
        return theFloor >= 0 && theFloor < myExplored.length &&
               theX >= 0 && theX < myExplored[0].length &&
               theY >= 0 && theY < myExplored[0][0].length;
    }
}
