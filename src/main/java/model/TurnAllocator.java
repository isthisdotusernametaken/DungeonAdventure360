package model;

import java.util.Arrays;

public class TurnAllocator {

    private final boolean[] myTurns;
    private final double myExtraTurnChance;
    private final int myExtraTurnIndex;
    private int myCurrentTurn;

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
    }

    boolean nextTurn() {
        final boolean turn = myTurns[myCurrentTurn];

        incrementTurn();
        incrementIfSkippingExtraTurn();

        return turn;
    }

    private void incrementTurn() {
        if (++myCurrentTurn >= myTurns.length) {
            myCurrentTurn = 0;
        }
    }

    private void incrementIfSkippingExtraTurn() {
        if (myCurrentTurn == myExtraTurnIndex &&
            Util.probabilityTest(myExtraTurnChance)
        ) {
            incrementTurn();
        }
    }

    private double calculateSpeedRatio(final boolean theAdventurerAsFastOrFaster,
                                       final int theAdventurerSpeed,
                                       final int theMonsterSpeed) {
        return theAdventurerAsFastOrFaster ?
               ((double) theAdventurerSpeed) / theMonsterSpeed :
               ((double) theMonsterSpeed) / theAdventurerSpeed;
    }

    private boolean[] assignTurns(final boolean theAdventurerAsFastOrFaster) {
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
