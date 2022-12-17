package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * This class creates, handles and modifies the turn allocator for the dungeon
 * adventure game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class TurnAllocator implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -5248983433683950395L;

    /**
     * The boolean array representing if it is the dungeon character's turn.
     */
    private final boolean[] myTurns;

    /**
     * The double value representing the extra turn chance for the dungeon
     * character.
     */
    private final double myExtraTurnChance;

    /**
     * The integer value representing the extra turn index.
     */
    private final int myExtraTurnIndex;

    /**
     * The integer value representing the current turn.
     */
    private int myCurrentTurn;

    /**
     * The boolean true or false if the dungeon character's turn is over.
     */
    private boolean myIsCompleted;

    /**
     * Constructor to construct the initial turn allocator for the
     * dungeon character.
     *
     * @param theAdventurerSpeed    The integer value representing the speed
     *                              of the adventurer's character.
     * @param theMonsterSpeed       The integer value representing the speed
     *                              of the dungeon monster.
     */
    TurnAllocator(final int theAdventurerSpeed, final int theMonsterSpeed) {
        final boolean adventurerAsFastOrFaster =
                theAdventurerSpeed >= theMonsterSpeed;
        final double speedRatio = calculateSpeedRatio(
                adventurerAsFastOrFaster, theAdventurerSpeed, theMonsterSpeed
        );

        myCurrentTurn = 0;
        myExtraTurnIndex = (int) speedRatio;
        myExtraTurnChance = speedRatio - myExtraTurnIndex;
        myTurns = assignTurns(adventurerAsFastOrFaster);
        // myIsCompleted is false;
    }

    /**
     * Calculates and gets the speed ratio to see which dungeon character is
     * faster.
     *
     * @param theAdventurerAsFastOrFaster   The boolean true or false if the
     *                                      adventurer's character is faster
     *                                      than the monster.
     * @param theAdventurerSpeed            The speed of the adventurer's
     *                                      character.
     * @param theMonsterSpeed               The speed of the dungeon monster.
     * @return                              The double value representing the
     *                                      speed stat of the dungeon character
     *                                      that is faster.
     */
    private static double calculateSpeedRatio(final boolean theAdventurerAsFastOrFaster,
                                              final int theAdventurerSpeed,
                                              final int theMonsterSpeed) {
        return theAdventurerAsFastOrFaster ?
                ((double) theAdventurerSpeed) / theMonsterSpeed :
                ((double) theMonsterSpeed) / theAdventurerSpeed;
    }

    /**
     * Checks if out of turns.
     *
     * @return The boolean true or false if turns are completed.
     */
    boolean isCompleted() {
        return myIsCompleted;
    }

    /**
     * Checks and peeks the next turn.
     *
     * @return The boolean value of the next turn.
     */
    boolean peekNextTurn() {
        return myTurns[myCurrentTurn];
    }

    /**
     * Forward to the next turn.
     */
    void nextTurn() {
        incrementTurn();
        incrementTurnIfSkippingExtra();
    }

    /**
     * Increment the turn.
     */
    private void incrementTurn() {
        if (++myCurrentTurn >= myTurns.length) {
            myIsCompleted = true;
            myCurrentTurn = 0;
        }
    }

    /**
     * Increment the turn if skipping extra.
     */
    private void incrementTurnIfSkippingExtra() {
        if (myCurrentTurn == myExtraTurnIndex &&
            !Util.probabilityTest(myExtraTurnChance)
        ) {
            // Note: may be skipped multiple times depending on success of
            // consecutive probability tests to add more variability to turn
            // distribution
            incrementTurn();
        }
    }

    /**
     * Assigns extra turns for the dungeon character that is faster.
     * For ratio n.x, n turns for faster, 1 possible extra turn for faster
     * (probability x each time), and 1 turn for slower
     *
     * @param theAdventurerAsFastOrFaster The dungeon character with
     *                                    greater speed stat,
     * @return                            The boolean array representing
     *                                    the turns.
     */
    private boolean[] assignTurns(final boolean theAdventurerAsFastOrFaster) {
        final boolean[] turns = new boolean[myExtraTurnIndex + 2];

        turns[turns.length - 1] = !theAdventurerAsFastOrFaster;
        Arrays.fill(
                turns,
                0, turns.length - 1,
                theAdventurerAsFastOrFaster
        );

        return turns;
    }
}
