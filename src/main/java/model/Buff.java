package model;

public abstract class Buff {

    private final BuffType myType;
    private int myDuration;

    Buff( BuffType theType,
          int theDuration) {
        myType = theType;
        myDuration = theDuration;

    }
    final BuffType getType() {

        return myType;
    }
    final int getMyDuration() {

        return myDuration;
    }
    final void changeDuration(int theAmount) {

    }
    final void advance() {

    }
    final boolean isCompleted() {

        return false;
    }
    void adjustStats(AdjustedCharacterStats theStats) {

    }
}
