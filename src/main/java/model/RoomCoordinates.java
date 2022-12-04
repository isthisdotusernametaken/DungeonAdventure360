package model;

import java.io.Serializable;

public class RoomCoordinates implements Serializable {

    private final int myFloor;
    private final int myX;
    private final int myY;

    RoomCoordinates(final int theFloor,
                    final int theX,
                    final int theY) {
        myFloor = theFloor;
        myX = theX;
        myY = theY;
    }

    @Override
    public String toString() {
        return new StringBuilder()
               .append("Floor: ").append(myFloor)
               .append("; X: ").append(myX)
               .append("; Y: ").append(myY)
               .toString();
    }

    int getFloor() {
        return myFloor;
    }

    int getX() {
        return myX;
    }

    int getY() {
        return myY;
    }

    RoomCoordinates add(final Direction theDirection,
                        final RoomCoordinates theDimensions) {
        return switch (theDirection) {
            case NORTH -> addY(-1, theDimensions.getY());
            case SOUTH -> addY(1, theDimensions.getY());
            case WEST -> addX(-1, theDimensions.getX());
            case EAST -> addX(1, theDimensions.getX());
        };
    }

    RoomCoordinates addFloor(final boolean theIsUp,
                             final int theFloorCount) {
        return new RoomCoordinates(
                Util.clampInt(0, theFloorCount, myFloor + (theIsUp ? -1 : 1)),
                myX,
                myY
        );
    }

    boolean isSameRoom(final RoomCoordinates theOther) {
        return myFloor == theOther.myFloor &&
               myX == theOther.myX &&
               myY == theOther.myY;
    }

    boolean isSameRoom(final int theFloor, final int theX, final int theY) {
        return myFloor == theFloor &&
               myX == theX &&
               myY == theY;
    }

    private RoomCoordinates addX(final int theChange, final int theMaxX) {
        return new RoomCoordinates(
                myFloor,
                Util.clampInt(0, theMaxX, myX + theChange),
                myY
        );
    }

    private RoomCoordinates addY(final int theChange, final int theMaxY) {
        return new RoomCoordinates(
                myFloor,
                myX,
                Util.clampInt(0, theMaxY, myY + theChange)
        );
    }
}
