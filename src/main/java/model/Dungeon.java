package model;

import java.io.Serializable;

public abstract class Dungeon implements Serializable {

    private final Map myMap;

    Dungeon(final Map theMap) {
        myMap = theMap;
    }

    public abstract String toString();

    void explore(final RoomCoordinates theCoords) {
        myMap.explore(theCoords);
    }

    boolean isExplored(final RoomCoordinates theCoords) {
        return myMap.isExplored(theCoords);
    }

    void explore(final int theFloor, final int theX, final int theY) {
        myMap.explore(theFloor, theX, theY);
    }

    boolean isExplored(final int theFloor, final int theX, final int theY) {
        return myMap.isExplored(theFloor, theX, theY);
    }

    abstract String view(boolean theHideUnknown);

    abstract Room getRoom(RoomCoordinates theCoords);

    abstract boolean hasStairs(RoomCoordinates theCoords);

    abstract RoomCoordinates getEntrance();

    abstract RoomCoordinates getExit();
}
