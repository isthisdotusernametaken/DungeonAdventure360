package model;

public class MapFactory {

    static Map create(final RoomCoordinates theDimensions) {
        return new ArrayMap(theDimensions);
    }
}
