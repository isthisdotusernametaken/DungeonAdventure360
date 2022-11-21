package model;

public enum Difficulty {

    EASY(-0.2),
    NORMAL(0.0),
    HARD(0.2);

    private final double myPositiveMultiplier;
    private final double myNegativeMultiplier;

    Difficulty(final double theDifficultyModifier) {
        myPositiveMultiplier = 1.0 - theDifficultyModifier;
        myNegativeMultiplier = 1.0 + theDifficultyModifier;
    }

    double getPositiveMultiplier() {
        return myPositiveMultiplier;
    }

    double getNegativeMultiplier() {
        return myNegativeMultiplier;
    }
}
