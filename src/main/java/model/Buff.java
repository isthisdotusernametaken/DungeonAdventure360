package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a template to construct and handle all the buffs
 * and debuffs in the dungeon adventure game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class Buff implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -2519735318269057642L;

    /**
     * The integer value representing the maximum turns a buff/debuff can have.
     */
    private static final int MAX_TURNS = 999;

    /**
     * The type of the buff/debuff.
     */
    private final BuffType myType;

    /**
     * The string representing the stat that the buff/debuff will affect on.
     */
    private final String myChangedStats;

    /**
     * The double value representing the stat multiplier of the buff/debuff.
     */
    private final double myStatMultiplier;

    /**
     * The double value representing the damage percentage of the buff/debuff.
     */
    private final double myDamagePercent;

    /**
     * The integer value representing the durations of the buff.
     */
    private int myDuration;

    /**
     * Constructors to construct the buff/debuff with its effects.
     *
     * @param theType           The type of the buff/debuff.
     * @param theChangedStats   The string representing the stat that the
     *                          buff/debuff will affect on.
     * @param theStatMultiplier The double value representing
     *                          the stat multiplier of the buff/debuff.
     * @param theDamagePercent  The double value representing
     *                          the damage percentage of the buff/debuff.
     * @param theDuration       The integer value representing
     *                          the durations of the buff.
     */
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

    /**
     * ToString method to format all the information of the buff/debuff,
     * including the type, the changed stats, stat multiplier,duration.etc.
     *
     * @return The string represent all the information of the buff/debuff.
     */
    @Override
    public final String toString() {
        return myType.toString() + ": " +
               myChangedStats + " x " + myStatMultiplier +
               " (" + myDuration + " turns)";
    }

    /**
     * Gets the type of the buff/debuff.
     *
     * @return The type of the buff/debuff.
     */
    final BuffType getType() {
        return myType;
    }

    /**
     * Gets the stat multiplier value of the buff/debuff.
     *
     * @return The stat multiplier value of the buff/debuff.
     */
    final double getStatMultiplier() {
        return myStatMultiplier;
    }

    /**
     * Gets the damage percentage value of the buff/debuff.
     *
     * @return The damage percentager value of the buff/debuff.
     */
    final double getDamagePercent() {
        return myDamagePercent;
    }

    /**
     * Adjusts the duration of the buff/debuff
     *
     * @param theTurns The current duration of the buff/debuff.
     */
    final void changeDuration(final int theTurns) {
        myDuration = Util.addAndClampInt(1, MAX_TURNS, myDuration, theTurns);
    }

    /**
     * Advance the duration of the buff/debuff by 1.
     */
    final void advance() {
        myDuration--;
    }

    /**
     * Checks if the current duration of the buff/debuff is over.
     *
     * @return The boolean true or false if the duration is over.
     */
    final boolean isCompleted() {
        return myDuration <= 0;
    }

    /**
     * Template method adjustStats for buffs/debuffs to
     * adjust the stats of the dungeon character,
     * the effects is varies from each buff/debuff.
     *
     * @param theStats The stats of the dungeon character to be adjusted on.
     */
    abstract void adjustStats(AdjustedCharacterStats theStats);
}
