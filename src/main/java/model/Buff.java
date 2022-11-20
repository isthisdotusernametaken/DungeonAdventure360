package model;

public abstract class Buff {

    private final BuffType myType;
    private int myDuration;

    Buff (final BuffType theType,
          final int theDuration) {
        myType = theType;
        myDuration = theDuration;
    }


    final BuffType getType() {
        return myType;
    }

    final int getDuration() {
        return myDuration;
    }

    final void changeDuration (final int theTurns) {
        myDuration += theTurns;
    }

    final void advance() {
        myDuration--;
    }

    final boolean isCompleted() {
        return myDuration <= 0;
    }

    void adjustStats(AdjustedCharacterStats theStats) {
    }
}
