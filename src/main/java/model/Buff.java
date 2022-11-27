package model;

import java.io.Serializable;

public abstract class Buff implements Serializable {

    private final BuffType myType;
    private final String myChangedStats;
    private final double myStatMultiplier;
    private final double myDamagePercent;

    private int myDuration;

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
        return myChangedStats + " x " + myStatMultiplier +
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

    final int getDuration() {
        return myDuration;
    }

    final void changeDuration(final int theTurns) {
        myDuration += theTurns;
    }

    final void advance() {
        myDuration--;
    }

    final boolean isCompleted() {
        return myDuration <= 0;
    }

    abstract void adjustStats(AdjustedCharacterStats theStats);
}
