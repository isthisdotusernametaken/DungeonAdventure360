package model;

import java.io.Serial;
import java.io.Serializable;

/**
 *This class accesses and handles the coordinates of the room in the
 * dungeon.
 */
public class RoomCoordinates implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -4630802384169539704L;

    /**
     * The integer value representing the floor location.
     */
    private final int myFloor;

    /**
     * The integer value representing the x-value of the
     * room.
     */
    private final int myX;

    /**
     * The integer value representing the y-value of the
     * room.
     */
    private final int myY;

    /**
     * Constructor to construct the coordination of the
     * dungeon room.
     *
     * @param theFloor  The integer value representing the floor location.
     * @param theX      The integer value representing the x-value of the room.
     * @param theY      The integer value representing the y-value of the room.
     */
    RoomCoordinates(final int theFloor,
                    final int theX,
                    final int theY) {
        myFloor = theFloor;
        myX = theX;
        myY = theY;
    }

    /**
     * ToString method to formats and constructs the information of the
     * room's coordination.
     *
     * @return The string representing the information of the room's
     *          coordination.
     */
    @Override
    public String toString() {
        return "Floor: " + myFloor + "; X: " + myX + "; Y: " + myY;
    }

    /**
     * Gets the floor location.
     *
     * @return The floor location.
     */
    int getFloor() {
        return myFloor;
    }

    /**
     * Gets the x-value of the room.
     *
     * @return The x-value of the room.
     */
    int getX() {
        return myX;
    }

    /**
     * Gets the y-value of the room.
     *
     * @return The y-value of the room.
     */
    int getY() {
        return myY;
    }

    /**
     * Adds the room coordinates.
     *
     * @param theDirection  The direction of the room.
     * @param theDimensions The dimension of the room.
     * @return              The room coordinates.
     */
    RoomCoordinates add(final Direction theDirection,
                        final RoomCoordinates theDimensions) {
        return switch (theDirection) {
            case NORTH -> addY(-1, theDimensions.getY());
            case SOUTH -> addY(1, theDimensions.getY());
            case WEST -> addX(-1, theDimensions.getX());
            case EAST -> addX(1, theDimensions.getX());
        };
    }

    /**
     * Adds floor.
     *
     * @param theIsUp       The boolean true or false if the floor is up.
     * @param theFloorCount The integer value representing the number
     *                      of floors.
     * @return              The new room coordinates
     */
    RoomCoordinates addFloor(final boolean theIsUp,
                             final int theFloorCount) {
        return new RoomCoordinates(
                Util.clampIntZeroToMaxExc(
                        theFloorCount, myFloor + (theIsUp ? -1 : 1)
                ),
                myX,
                myY
        );
    }

    /**
     * Checks if the current room coordinate is one below.
     *
     * @param theOther  The other room coordinate.
     * @return          The boolean true or false if
     *                  the room coordinate is one below.
     */
    boolean isOneBelow(final RoomCoordinates theOther) {
        return myFloor == theOther.myFloor + 1 &&
               myX == theOther.myX &&
               myY == theOther.myY;
    }

    /**
     * Checks if the current room coordinate is the same
     * as the other.
     *
     * @param theOther  The other room coordinate.
     * @return          The boolean true or false if
     *                  the room coordinate is the same
     *                  as the other.
     */
    boolean isSameRoom(final RoomCoordinates theOther) {
        return myFloor == theOther.myFloor &&
               myX == theOther.myX &&
               myY == theOther.myY;
    }

    /**
     * Checks if the current room coordinate is the same
     * as the other.
     *
     * @param theFloor The integer value representing the floor location.
     * @param theX     The integer value representing the x-value of the room.
     * @param theY     The integer value representing the y-value of the room.
     * @return         The boolean true or false if is the same room
     *                 coordinate.
     */
    boolean isSameRoom(final int theFloor, final int theX, final int theY) {
        return myFloor == theFloor &&
               myX == theX &&
               myY == theY;
    }

    /**
     * Adds x-value to the room coordinate.
     *
     * @param theChange The integer value representing the
     *                  amount to change.
     * @param theMaxX   The integer value representing the
     *                  allowed maximum x-value of the room.
     * @return          The new room coordinates with adjusted
     *                  x-value.
     */
    private RoomCoordinates addX(final int theChange, final int theMaxX) {
        return new RoomCoordinates(
                myFloor,
                Util.clampIntZeroToMaxExc(theMaxX, myX + theChange),
                myY
        );
    }

    /**
     * Adds x-value to the room coordinate.
     *
     * @param theChange The integer value representing the
     *                  amount to change.
     * @param theMaxY   The integer value representing the
     *                  allowed maximum y-value of the room.
     * @return          The new room coordinates with adjusted
     *                  y-value.
     */
    private RoomCoordinates addY(final int theChange, final int theMaxY) {
        return new RoomCoordinates(
                myFloor,
                myX,
                Util.clampIntZeroToMaxExc(theMaxY, myY + theChange)
        );
    }
}
