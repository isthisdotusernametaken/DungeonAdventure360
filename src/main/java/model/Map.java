package model;

public class Map {

    private final boolean[][][] myExplored;

    Map(final RoomCoordinates theDimensions) {
        myExplored = new boolean[theDimensions.getFloor()]
                                [theDimensions.getX()]
                                [theDimensions.getY()];
    }

    boolean isExplored(final int theFloor,
                       final int theX,
                       final int theY) {
        return myExplored[theFloor][theX][theY];
    }

    boolean isExplored(final RoomCoordinates theCoords) {
        return isExplored(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    void explore(final int theFloor,
                 final int theX,
                 final int theY) {
        myExplored[theFloor][theX][theY] = true;
    }

    void explore(final RoomCoordinates theCoords) {
        explore(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }
}
