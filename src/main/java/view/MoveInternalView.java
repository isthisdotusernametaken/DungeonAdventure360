/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import java.util.Arrays;
import java.util.function.Function;

import controller.Controller;
import model.Direction;

/**
 * This class is opened directly by other screens in the view (rather than with
 * a MenuSignal) to move to a nearby room.
 */
public class MoveInternalView {

    /**
     * Sets up a Menu to choose a direction to move.
     */
    private static final Menu MENU = new Menu(
            "Choose a direction to move",
            Arrays.stream(Direction.values())
                    .map(Direction::toString)
                    .toArray(String[]::new),
            Arrays.stream(Direction.values())
                    .map(dir -> dir.toString().substring(0, 1))
                    .toArray(String[]::new),
            true,
            false,
            true
    );

    /**
     * Displays the movement menu and gets and performs the selected movement
     * action.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @param theMovementOperation The movement action to attempt (moving to a
     *                             new room out of combat, or fleeing)
     * @param theIsInCombatView Whether the combat menu should be opened anew
     *                          or (if already in the combat menu) returned to
     * @return The menu signal for the current room after attempting the
     *         provided movement operation.
     */
    static MenuSignal open(final Controller theController,
                           final Function<Direction, String> theMovementOperation,
                           final boolean theIsInCombatView) {
        final int choice = MENU.select(
                getInvalidDirections(theController)
        );

        switch (choice) {
            case 0, 1, 2, 3 -> {
                System.out.println(
                        theMovementOperation.apply(Direction.values()[choice])
                );
                return Util.nextMenuFromCombatOrExploration(
                        theController, theIsInCombatView
                );
            }
            default -> {
                return MenuSignal.PREVIOUS;
            }
        }
    }

    /**
     * Gets which doors are valid in the current room.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The options to exclude from the menu. The array's size depends
     *         on how many invalid options there are.
     */
    private static int[] getInvalidDirections(final Controller theController) {
        final int[] invalidDirections = new int[Direction.values().length];
        int index = 0;

        for (Direction direction : Direction.values()) {
            if (!theController.isValidDirection(direction)) {
                invalidDirections[index++] = direction.ordinal();
            }
        }

        return Arrays.copyOfRange(invalidDirections, 0, index);
    }
}
