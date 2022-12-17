package model;

import controller.ProgramFileManager;

/**
 * This class constructs and sets the difficulty for the dungeon adventure
 * game when chosen by the adventurer. Each difficulty level will have
 * its own adjustment effect on the whole dungeon including the monsters
 * , the traps, and the number of rooms.
 */
public enum Difficulty {

    /**
     * Easy mode.
     */
    EASY(-0.25, 1, 4),
    /**
     * Normal mode.
     */
    NORMAL(0.0, 2, 5),
    /**
     * Hard mode.
     */
    HARD(0.3, 3, 5);

    /**
     * The name of the difficulty level.
     */
    private String myName;

    /**
     * The difficulty modifier managing value.
     */
    private final double myPositiveMultiplier;

    /**
     * The difficulty modifier managing value.
     */
    private final double myNegativeMultiplier;

    /**
     * The room coordinates to get its dimension.
     */
    private final RoomCoordinates myDimensions;

    /**
     * Constructor to construct the difficulty level of the dungeon
     * adventure game.
     *
     * @param theDifficultyModifier The difficulty modifier managing value.
     * @param theFloors             The number of floors.
     * @param theSize               The size of the floor.
     */
    Difficulty(final double theDifficultyModifier,
               final int theFloors,
               final int theSize) {
        myPositiveMultiplier = 1.0 - theDifficultyModifier;
        myNegativeMultiplier = 1.0 + theDifficultyModifier;

        final int minFloorSize = calculateMinFloorSize();
        if (theFloors < 1 || theSize < minFloorSize) {
            if (ProgramFileManager.tryCreateInstance()) {
                ProgramFileManager.getInstance().logException(
                        new IllegalArgumentException(
                                "Invalid dungeon dimensions: " +
                                theFloors + " floors, floor size " +
                                theSize
                        ), false
                );
            }

            // Should never be encountered.
            // Backup guaranteed valid dimensions if Enum constructors have
            // invalid values
            myDimensions = new RoomCoordinates(1, minFloorSize, minFloorSize);
        } else {
            myDimensions = new RoomCoordinates(
                    theFloors, theSize, theSize
            );
        }
    }

    /**
     * Calculates and obtains the minimum floor size.
     *
     * @return The integer value representing the minimum floor size.
     */
    private static int calculateMinFloorSize() {
        // Need enough distinct rooms for pillars, entrance, and exit.
        // To use in Enum constructor, can't be static final field
        return (int) Math.ceil(Math.sqrt(Pillar.createPillars().length + 2));
    }

    /**
     * ToString method to format and display the name of
     * difficulty level of the dungeon adventure game.
     *
     * @return The string representing the difficulty level.
     */
    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }

    /**
     * Gets the dimension of the rooms.
     *
     * @return The dimension of the rooms.
     */
    RoomCoordinates getDimensions() {
        return myDimensions;
    }

    /**
     * Calculates and obtains the positive difficulty modifier managing value.
     *
     * @param theValue The double value for adjusting purpose.
     * @return         The difficulty modifier managing value.
     */
    double modifyPositive(final double theValue) {
        return theValue * myPositiveMultiplier;
    }

    /**
     * Calculates and obtains the negative difficulty modifier managing value.
     *
     * @param theValue The double value for adjusting purpose.
     * @return         The difficulty modifier managing value.
     */
    double modifyNegative(final double theValue) {
        return theValue * myNegativeMultiplier;
    }

    /**
     * Calculates and obtains the positive difficulty modifier managing value.
     *
     * @param theValue The integer value for adjusting purpose.
     * @return         The difficulty modifier managing value.
     */
    int modifyNegative(final int theValue) {
        return (int) (theValue * myNegativeMultiplier);
    }
}
