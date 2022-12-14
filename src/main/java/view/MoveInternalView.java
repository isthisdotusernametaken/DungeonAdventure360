package view;

import controller.Controller;
import model.Direction;

import java.util.Arrays;
import java.util.function.Function;

public class MoveInternalView {

    /**
     * Sets up a Move Internal Menu panel when not in combat.
     * The Move Internal includes the menu descriptions
     * and the menu key options associated with that descriptions.
     *
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
     * Displays the Move Internal Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the move internal menu chosen by the player.
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
     * Gets and checks invalid directions in the current room.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The range of integer array depends on how many invalid directions.
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
