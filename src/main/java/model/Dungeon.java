/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class is a template to construct and handle the dungeon map and rooms.
 */
public abstract class Dungeon implements Serializable {

    /**
     * The character representing the character symbol of a stair
     * going up.
     */
    static final char UP_STAIRS = '^';

    /**
     * The character representing the character symbol of a stair
     * going down.
     */
    static final char DOWN_STAIRS = 'v';

    /**
     * The character representing the character symbol of an unexplored
     * room.
     */
    static final char UNKNOWN = '~';

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -6235438913940504967L;

    /**
     * The dungeon map.
     */
    private final Map myMap;

    /**
     * Constructor to construct the dungeon map.
     * @param theMap The dungeon map.
     */
    Dungeon(final Map theMap) {
        myMap = theMap;
    }

    /**
     * Creates a list containing character symbols representing the
     * map components, including the stairs and rooms.
     *
     * @return The string list containing character symbols representing the
     *         map components, including the stairs and rooms.
     */
    static List<String> getMapRepresentations() {
        return List.of(
                UP_STAIRS + ": Stairs to floor above",
                DOWN_STAIRS + ": Stairs to floor below",
                UNKNOWN + ": Unexplored area"
        );
    }

    /**
     * Template for toString method.
     *
     * @return A string.
     */
    public abstract String toString();

    /**
     * Gets the dungeon map.
     *
     * @return The dungeon map.
     */
    Map getMap() {
        return myMap;
    }

    /**
     * Template for toString method.
     *
     * @param theAdventurerCoords The current coordinates of the adventurer.
     * @param theHideUnknown      The boolean true or false if the hide is
     *                            unknown.
     * @return A string.
     */
    abstract String toString(RoomCoordinates theAdventurerCoords,
                             boolean theHideUnknown);

    /**
     * Template for getDimensions method.
     *
     * @return The room coordinates.
     */
    abstract RoomCoordinates getDimensions();


    /**
     * Template for getRoom method.
     *
     * @param theCoords The coordinates of the room.
     * @return          The room.
     */
    abstract Room getRoom(RoomCoordinates theCoords);


    /**
     * Template for hasStairsUp method.
     *
     * @param theCoords The coordinates of the room.
     * @return          The boolean true or false.
     */
    abstract boolean hasStairsUp(RoomCoordinates theCoords);

    /**
     * Template for hasStairsDown method.
     *
     * @param theCoords The coordinates of the room.
     * @return          The boolean true or false.
     */
    abstract boolean hasStairsDown(RoomCoordinates theCoords);

    /**
     * Template for getEntrance method.
     *
     * @return The room coordinates.
     */
    abstract RoomCoordinates getEntrance();
}
