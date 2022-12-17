package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class TurnAllocator implements Serializable {

    @Serial
    private static final long serialVersionUID = -5248983433683950395L;

    final boolean[] myTurns;
    final double myExtraTurnChance;
    final int myExtraTurnIndex;
    int myCurrentTurn;
    boolean myIsCompleted;

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

    static double calculateSpeedRatio(final boolean theAdventurerAsFastOrFaster,
                                      final int theAdventurerSpeed,
                                      final int theMonsterSpeed) {
        return theAdventurerAsFastOrFaster ?
                ((double) theAdventurerSpeed) / theMonsterSpeed :
                ((double) theMonsterSpeed) / theAdventurerSpeed;
    }

    boolean isCompleted() {
        return myIsCompleted;
    }

    boolean peekNextTurn() {
        return myTurns[myCurrentTurn];
    }

    void nextTurn() {
        incrementTurn();
        incrementTurnIfSkippingExtra();
    }

    void incrementTurn() {
        if (++myCurrentTurn >= myTurns.length) {
            myIsCompleted = true;
            myCurrentTurn = 0;
        }
    }

    void incrementTurnIfSkippingExtra() {
        if (myCurrentTurn == myExtraTurnIndex &&
            !Util.probabilityTest(myExtraTurnChance)
        ) {
            incrementTurn();
        }
    }

    boolean[] assignTurns(final boolean theAdventurerAsFastOrFaster) {
        // for ratio n.x, n turns for faster, 1 possible extra turn for faster
        // (probability x each time), and 1 turn for slower
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
