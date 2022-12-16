package model;

import controller.ProgramFileManager;

public enum Difficulty {

    EASY(-0.25, 1, 4),
    NORMAL(0.0, 2, 5),
    HARD(0.3, 3, 5);

    private String myName;
    private final double myPositiveMultiplier;
    private final double myNegativeMultiplier;
    private final RoomCoordinates myDimensions;

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

    static int calculateMinFloorSize() {
        // Need enough distinct rooms for pillars, entrance, and exit.
        // To use in Enum constructor, can't be static final field
        return (int) Math.ceil(Math.sqrt(Pillar.createPillars().length + 2));
    }

    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }

    RoomCoordinates getDimensions() {
        return myDimensions;
    }

    double modifyPositive(final double theValue) {
        return theValue * myPositiveMultiplier;
    }

    double modifyNegative(final double theValue) {
        return theValue * myNegativeMultiplier;
    }

    int modifyNegative(final int theValue) {
        return (int) (theValue * myNegativeMultiplier);
    }
}
