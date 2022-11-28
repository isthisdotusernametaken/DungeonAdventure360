package model;

public enum Difficulty {

    EASY(-0.25),
    NORMAL(0.0),
    HARD(0.25);

    private final double myPositiveMultiplier;
    private final double myNegativeMultiplier;

    Difficulty(final double theDifficultyModifier) {
        myPositiveMultiplier = 1.0 - theDifficultyModifier;
        myNegativeMultiplier = 1.0 + theDifficultyModifier;
    }

    double modifyPositive(final double theValue) {
        return theValue * myPositiveMultiplier;
    }

    double modifyNegative(final double theValue) {
        return theValue * myNegativeMultiplier;
    }

    int modifyPositive(final int theValue) {
        return (int) (theValue * myPositiveMultiplier);
    }

    int modifyNegative(final int theValue) {
        return (int) (theValue * myNegativeMultiplier);
    }

    double getPositiveMultiplier() {
        return myPositiveMultiplier;
    }

    double getNegativeMultiplier() {
        return myNegativeMultiplier;
    }
}
