package model;

import java.io.Serial;

/**
 * This class constructs a 3D array
 */
public class ArrayMap extends Map {

    @Serial
    private static final long serialVersionUID = -2679508810748662333L;

    private final boolean[][][] myExplored;

    ArrayMap(final RoomCoordinates theDimensions) {
        myExplored = new boolean[theDimensions.getFloor()]
                                [theDimensions.getX()]
                                [theDimensions.getY()];
    }

    @Override
    boolean isExplored(final int theFloor,
                       final int theX,
                       final int theY) {
        return isInBounds(theFloor, theX, theY) &&
               myExplored[theFloor][theX][theY];
    }

    @Override
    boolean isExplored(final RoomCoordinates theCoords) {
        return isExplored(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    @Override
    void explore(final int theFloor,
                 final int theX,
                 final int theY) {
        if (isInBounds(theFloor, theX, theY)) {
            myExplored[theFloor][theX][theY] = true;
        }
    }

    @Override
    void explore(final RoomCoordinates theCoords) {
        explore(
                theCoords.getFloor(),
                theCoords.getX(),
                theCoords.getY()
        );
    }

    @Override
    boolean isInBounds(final int theFloor,
                       final int theX,
                       final int theY) {
        return theFloor >= 0 && theFloor < myExplored.length &&
               theX >= 0 && theX < myExplored[0].length &&
               theY >= 0 && theY < myExplored[0][0].length;
    }
}
