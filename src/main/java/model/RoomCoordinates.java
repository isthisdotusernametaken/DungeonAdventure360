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

    int getFloor() {
        return myFloor;
    }

    int getX() {
        return myX;
    }

    int getY() {
        return myY;
    }

    boolean isSameRoom(final RoomCoordinates theOther) {
        return myFloor == theOther.myFloor &&
               myX == theOther.myX &&
               myY == theOther.myY;
    }
}
