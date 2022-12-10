package view;

import controller.Controller;
import model.Direction;

import java.util.Arrays;
import java.util.function.Function;

public class MoveInternalView {

    private static final Menu MENU = new Menu(
            "Choose a direction to move",
            Arrays.stream(Direction.values())
                    .map(Direction::toString)
                    .toArray(String[]::new),
            Arrays.stream(Direction.values())
                    .map(dir -> dir.toString().substring(0, 1))
                    .toArray(String[]::new),
            true,
            false
    );

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
