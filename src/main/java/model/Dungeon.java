package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Dungeon implements Serializable {

    @Serial
    private static final long serialVersionUID = -6235438913940504967L;

    static final char UP_STAIRS = '^';
    static final char DOWN_STAIRS = 'v';

    private final Map myMap;

    Dungeon(final Map theMap) {
        myMap = theMap;
    }

    static List<String> getStairRepresentations() {
        return List.of(
                UP_STAIRS + ": Stairs to floor above",
                DOWN_STAIRS + ": Stairs to floor below"
        );
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
