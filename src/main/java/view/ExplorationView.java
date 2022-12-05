package view;

import controller.Controller;
import model.Direction;

import java.util.Arrays;

public class ExplorationView {

    private static final Menu ROOM_MENU = new Menu(
            "Choose an action",
            new String[]{
                    "Take stairs up", "Take stairs down",
                    "Move", "Open Inventory", "Open Map",
                    "Save", "Load", "Quit to Title Screen"
            },
            new String[]{
                    "Up", "Down",
                    "W", "I", "M",
                    "S", "L", "Q"
            },
            false,
            true
    );
    private static final Menu MOVE_MENU = new Menu(
            "Choose a direction to move",
            Arrays.stream(Direction.values())
                  .map(Direction::toString)
                  .toArray(String[]::new),
            Arrays.stream(Direction.values())
                  .map(dir -> dir.toString().substring(0, 1))
                  .toArray(String[]::new),
            true,
            true
    );
    private static final Menu SECRET_ROOM_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Toggle hiding of unexplored rooms on map"
            },
            true,
            false
    );

    private static final String UNEXPLORED_HIDDEN =
            "Unexplored rooms hidden: ";

    static MenuSignal open(final Controller theController) {
        System.out.println(theController.getGame().getRoom());

        MenuSignal internalSignal = MenuSignal.PREVIOUS;
        while (internalSignal == MenuSignal.PREVIOUS) {
            switch (ROOM_MENU.select(getInvalidStairs(theController))) {
                case 0: return useStairs(theController, true);
                case 1: return useStairs(theController, false);
                case 2:
                    internalSignal = move(theController);
                    break;
                case 3: return MenuSignal.INVENTORY;
                case 4: return MenuSignal.MAP;
                case 5: return MenuSignal.SAVE_GAME;
                case 6: return MenuSignal.LOAD_GAME;
                case 7: return MenuSignal.SAVE_AND_QUIT_TO_TITLE;
                case Menu.SECRET:
                    // internalSignal guaranteed still previous
                    openSecretMenu(theController);
                    break;
            }
        }

        return internalSignal;
    }

    private static int[] getInvalidStairs(final Controller theController) {
        return theController.getGame().hasStairs(true) ?
               new int[]{1} : theController.getGame().hasStairs(false) ?
               new int[]{0} :
               new int[]{0, 1};
    }

    private static MenuSignal useStairs(final Controller theController,
                                        final boolean theIsUp) {
        // Print with AttackResultAndAmount formatting from Controller
        theController.getGame().useStairs(theIsUp);

        return theController.getGame().isInCombat() ?
               MenuSignal.COMBAT :
               MenuSignal.EXPLORATION;
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
        // Print with AttackResultAndAmount formatting from Controller
        theController.getGame().moveAdventurer(theDirection);

        return theController.getGame().isInCombat() ?
               MenuSignal.COMBAT :
               MenuSignal.EXPLORATION;
    }

    private static void openSecretMenu(final Controller theController) {
        System.out.print(UNEXPLORED_HIDDEN);
        System.out.println(theController.getGame().isUnexploredHidden());

        if (SECRET_ROOM_MENU.select() == 0) {
            theController.getGame().toggleIsUnexploredHidden();
        }
    }
}
