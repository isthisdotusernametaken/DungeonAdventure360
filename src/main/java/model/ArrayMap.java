package model;

public class ArrayMap extends Map {

    private final boolean[][][] myExplored;

    ArrayMap(final RoomCoordinates theDimensions) {
        myExplored = new boolean[theDimensions.getFloor()]
                                [theDimensions.getX()]
                                [theDimensions.getY()];
    }

    boolean isExplored(final int theFloor,
                       final int theX,
                       final int theY) {
        return theFloor >= 0 && theFloor < myExplored.length &&
               theX >= 0 && theX < myExplored[0].length &&
               theY >= 0 && theY < myExplored[0][0].length &&
               myExplored[theFloor][theX][theY];
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
        if (
                theFloor >= 0 && theFloor < myExplored.length &&
                theX >= 0 && theX < myExplored[0].length &&
                theY >= 0 && theY < myExplored[0][0].length
        ) {
            myExplored[theFloor][theX][theY] = true;
        }
    }

    void explore(final RoomCoordinates theCoords) {
        explore(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }
}
