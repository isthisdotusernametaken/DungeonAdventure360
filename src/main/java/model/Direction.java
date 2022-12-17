package model;

/**
 * This class represents the directions that the player can move
 * in each room in the dungeon adventure game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
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
