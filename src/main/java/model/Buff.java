package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Buff implements Serializable {

    static final int MAX_TURNS = 999;

    @Serial
    private static final long serialVersionUID = -2519735318269057642L;

    int myDuration;

    private final BuffType myType;
    private final String myChangedStats;
    private final double myStatMultiplier;
    private final double myDamagePercent;

    Buff(final BuffType theType,
         final String theChangedStats,
         final double theStatMultiplier,
         final double theDamagePercent,
         final int theDuration) {
        myType = theType;
        myChangedStats = theChangedStats;
        myStatMultiplier = theStatMultiplier;
        myDamagePercent = theDamagePercent;

        myDuration = theDuration;
    }

    @Override
    public final String toString() {
        return myType.toString() + ": " +
               myChangedStats + " x " + myStatMultiplier +
               " (" + myDuration + " turns)";
    }

    final BuffType getType() {
        return myType;
    }

    final double getStatMultiplier() {
        return myStatMultiplier;
    }

    final double getDamagePercent() {
        return myDamagePercent;
    }

    final void changeDuration(final int theTurns) {
        myDuration = Util.addAndClampInt(1, MAX_TURNS, myDuration, theTurns);
    }

    final void advance() {
        myDuration--;
    }

    final boolean isCompleted() {
        return myDuration <= 0;
    }

    abstract void adjustStats(AdjustedCharacterStats theStats);
}
