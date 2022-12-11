package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Dungeon implements Serializable {

    @Serial
    private static final long serialVersionUID = -6235438913940504967L;

    private final Map myMap;

    Dungeon(final Map theMap) {
        myMap = theMap;
    }

    public abstract String toString();

    Map getMap() {
        return myMap;
    }

    abstract String toString(RoomCoordinates theAdventurerCoords,
                             boolean theHideUnknown);

    abstract RoomCoordinates getDimensions();

    abstract Room getRoom(RoomCoordinates theCoords);

    abstract boolean hasStairsUp(RoomCoordinates theCoords);

    abstract boolean hasStairsDown(RoomCoordinates theCoords);

    abstract RoomCoordinates getEntrance();
}
