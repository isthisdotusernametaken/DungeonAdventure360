package model;

import java.io.Serializable;

public abstract class Map implements Serializable {

    abstract boolean isExplored(int theFloor, int theX, int theY);

    abstract boolean isExplored(RoomCoordinates theCoords);

    abstract void explore(int theFloor, int theX, int theY);

    abstract void explore(RoomCoordinates theCoords);
}
