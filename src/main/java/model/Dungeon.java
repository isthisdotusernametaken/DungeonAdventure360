package model;

import java.io.Serializable;

public abstract class Dungeon implements Serializable {

    private final Map myMap;

    Dungeon(final Map theMap) {
        myMap = theMap;
    }

    public abstract String toString();

    Map getMap() {
        return myMap;
    }

    abstract String view(boolean theHideUnknown);

    abstract RoomCoordinates getDimensions();

    abstract Room getRoom(RoomCoordinates theCoords);

    abstract boolean hasStairsUp(RoomCoordinates theCoords);

    abstract boolean hasStairsDown(RoomCoordinates theCoords);

    abstract RoomCoordinates getEntrance();

    abstract RoomCoordinates getExit();
}
