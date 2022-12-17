/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This class represents the directions that the player can move
 * in each room in the dungeon adventure game.
 */
public enum Direction {

    /**
     * Move up.
     */
    NORTH,
    /**
     * Move right.
     */
    EAST,
    /**
     * Move down.
     */
    SOUTH,
    /**
     * Move left.
     */
    WEST;

    /**
     * The name of the direction.
     */
    private String myName;

    /**
     * ToString method to format and display the name of
     * the direction.
     *
     * @return The name of the direction.
     */
    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }
}
