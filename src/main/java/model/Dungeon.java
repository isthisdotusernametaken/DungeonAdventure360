package model;

public class Dungeon {

    private final Room[][][] myRooms;
    private final Map myMap;

    Dungeon(final RoomCoordinates theDimensions){
        myRooms = generateMaze(theDimensions);
        myMap = new Map(theDimensions);
    }

    public String toString() {
        return "";
    }

    Room getRoom(final RoomCoordinates theCoords) {
        return myRooms[theCoords.getFloor()]
                      [theCoords.getX()]
                      [theCoords.getY()];
    }

    Map getMap() {
        return myMap;
    }

    private Room[][][] generateMaze(final RoomCoordinates theDimensions) {
        Room[][][] maze = new Room[theDimensions.getFloor()]
                                  [theDimensions.getX()]
                                  [theDimensions.getY()];

        // generate maze

        return maze;
    }
}
