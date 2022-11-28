package model;

public class MapFactory {

    static Map create(RoomCoordinates theDimensions) {
        return new ArrayMap(theDimensions);
    }
}
