package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Map implements Serializable {

    @Serial
    private static final long serialVersionUID = 6208381576431507315L;

    abstract boolean isExplored(int theFloor, int theX, int theY);

    abstract boolean isExplored(RoomCoordinates theCoords);

    abstract void explore(int theFloor, int theX, int theY);

    abstract void explore(RoomCoordinates theCoords);
}
