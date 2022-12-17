package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a template to construct and create abstract methods
 * and let subclasses implement and use the methods to handle and modified
 * the dungeon map.
 */
public abstract class Map implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 6208381576431507315L;

    /**
     * The abstract method for isExplored method.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-value
     *                 of the room.
     * @param theY     The integer value representing the y-value
     *                 of the room.
     * @return         The boolean true or false.
     */
    abstract boolean isExplored(int theFloor, int theX, int theY);

    /**
     * The abstract method for isExplored method.
     *
     * @param theCoords The coordinates of the room.
     * @return          The boolean true or false.
     */
    abstract boolean isExplored(RoomCoordinates theCoords);

    /**
     * The abstract method for explore method.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-value
     *                 of the room.
     * @param theY     The integer value representing the y-value
     *                 of the room.
     */
    abstract void explore(int theFloor, int theX, int theY);

    /**
     * The abstract method for explore method.
     *
     * @param theCoords The coordinates of the room.
     */
    abstract void explore(RoomCoordinates theCoords);

    /**
     * The abstract method for isInBounds method.
     *
     * @param theFloor The integer value representing the location
     *                 of the floor in the dungeon map.
     * @param theX     The integer value representing the x-value
     *                 of the room.
     * @param theY     The integer value representing the y-value
     *                 of the room.
     * @return          The boolean true or false.
     */
    abstract boolean isInBounds(int theFloor, int theX, int theY);
}
