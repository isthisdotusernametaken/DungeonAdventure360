package model;

public class RoomCoordinates {

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
}
