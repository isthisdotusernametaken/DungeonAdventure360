package model;

import java.io.Serial;

/**
 * This class creates a 3D array for explored dungeon's rooms, checks
 * and updates the explored dungeon's rooms into the array
 */
public class ArrayMap extends Map {

    /**
     * String representing the exit option's key.
     */
    @Serial
    private static final long serialVersionUID = -2679508810748662333L;

    /**
     * The 3D boolean representing the explored rooms.
     */
    private final boolean[][][] myExplored;

    /**
     * Constructor of array map to get explored rooms.
     *
     * @param theDimensions The RoomCoordinates class to obtain and access the
     *                      dimension of the rooms.
     */
    ArrayMap(final RoomCoordinates theDimensions) {
        myExplored = new boolean[theDimensions.getFloor()]
                                [theDimensions.getX()]
                                [theDimensions.getY()];
    }

    /**
     * Checks and validates if the room has been explored.
     *
     * @param theFloor The integer value representing the floor location.
     * @param theX The integer value representing the x location of the room.
     * @param theY The integer value representing the y location of the room.
     * @return The boolean true or false if the room has been explored.
     */
    boolean isExplored(final int theFloor,
                       final int theX,
                       final int theY) {
        return theFloor >= 0 && theFloor < myExplored.length &&
               theX >= 0 && theX < myExplored[0].length &&
               theY >= 0 && theY < myExplored[0][0].length &&
               myExplored[theFloor][theX][theY];
    }

    /**
     * Checks and validates if the room has been explored.
     *
     * @param theCoords The coordinates of the room to get its dimension.
     * @return The boolean true or false if the room has been explored.
     */
    boolean isExplored(final RoomCoordinates theCoords) {
        return isExplored(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    /**
     * Checks and adds the explored rooms into the 3D array of explored rooms.
     *
     * @param theFloor The integer value representing the floor location.
     * @param theX The integer value representing the x location of the room.
     * @param theY The integer value representing the y location of the room.
     */
    void explore(final int theFloor,
                 final int theX,
                 final int theY) {
        if (
                theFloor >= 0 && theFloor < myExplored.length &&
                theX >= 0 && theX < myExplored[0].length &&
                theY >= 0 && theY < myExplored[0][0].length
        ) {
            myExplored[theFloor][theX][theY] = true;
        }
    }

    /**
     * Checks and adds the explored rooms into the 3D array of explored rooms.
     *
     * @param theCoords The coordinates of the room to get its dimension.
     */
    void explore(final RoomCoordinates theCoords) {
        explore(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }
}
