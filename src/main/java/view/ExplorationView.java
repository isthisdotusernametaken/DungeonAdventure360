package view;

import controller.Controller;
import model.Direction;

import java.util.Arrays;

public class ExplorationView {

    private static final Menu ROOM_MENU = new Menu(
            "Choose an action",
            new String[]{
                    "Move", "Open Inventory", "Open Map",
                    "Save", "Load", "Quit to Title Screen"
            },
            new String[]{
                    "W", "I", "M",
                    "S", "L", "Q"
            },
            false
    );
    private static final Menu MOVE_MENU = new Menu(
            "Choose a direction to move",
            Arrays.stream(Direction.values())
                  .map(Direction::toString)
                  .toArray(String[]::new),
            Arrays.stream(Direction.values())
                  .map(dir -> dir.toString().substring(0, 1))
                  .toArray(String[]::new),
            true
    );

    static MenuSignal open(Controller theController) {
        System.out.println(theController.getGame().getRoom());

        MenuSignal fromMoveMenu = MenuSignal.PREVIOUS;
        while (fromMoveMenu == MenuSignal.PREVIOUS) {
            switch (ROOM_MENU.select()) {
                case 0:
                    fromMoveMenu = move(theController);
                    break;
                case 1: return MenuSignal.INVENTORY;
                case 2: return MenuSignal.MAP;
                case 3: return MenuSignal.SAVE_GAME;
                case 4: return MenuSignal.LOAD_GAME;
                case 5: return MenuSignal.SAVE_AND_QUIT_TO_TITLE;
            }
        }

        return fromMoveMenu;
    }

    private static MenuSignal move(final Controller theController) {
        final int choice = MOVE_MENU.select(
                getInvalidDirections(theController)
        );

        return switch (choice) {
            case 0, 1, 2, 3 -> move(Direction.values()[choice], theController);
            default -> MenuSignal.PREVIOUS;
        };
    }

    private static int[] getInvalidDirections(final Controller theController) {
        final int[] invalidDirections = new int[Direction.values().length];
        int index = 0;

        for (Direction direction : Direction.values()) {
            if (!theController.getGame().isValidDirection(direction)) {
                invalidDirections[index++] = direction.ordinal();
            }
        }

        return Arrays.copyOfRange(invalidDirections, 0, index);
    }

    private static MenuSignal move(final Direction theDirection,
                                   final Controller theController) {
        return theController.getGame().moveAdventurer(theDirection) ?
               MenuSignal.COMBAT :
               MenuSignal.EXPLORATION;
    }
}
