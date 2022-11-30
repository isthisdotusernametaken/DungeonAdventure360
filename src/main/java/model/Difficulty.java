package model;

public enum Difficulty {

    EASY(-0.25, 1, 4),
    NORMAL(0.0, 2, 5),
    HARD(0.25, 3, 5);

    private final double myPositiveMultiplier;
    private final double myNegativeMultiplier;
    private final RoomCoordinates myDimensions;

    Difficulty(final double theDifficultyModifier,
               final int theFloors,
               final int theWidthAndLength) {
        myPositiveMultiplier = 1.0 - theDifficultyModifier;
        myNegativeMultiplier = 1.0 + theDifficultyModifier;

        myDimensions = new RoomCoordinates(
                theFloors, theWidthAndLength, theWidthAndLength
        );
    }

    double getPositiveMultiplier() {
        return myPositiveMultiplier;
    }

    double getNegativeMultiplier() {
        return myNegativeMultiplier;
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
