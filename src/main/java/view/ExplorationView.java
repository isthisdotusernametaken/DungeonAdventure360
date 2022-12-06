package view;

import java.util.Arrays;

import controller.Controller;
import model.Direction;

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
            false
    );
    private static final Menu SECRET_MENU = new Menu(
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
        System.out.println(theController.getRoom());
        System.out.println(theController.getAdventurer());

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
        return theController.hasStairs(true) ?
               new int[]{1} : theController.hasStairs(false) ?
               new int[]{0} :
               new int[]{0, 1};
    }

    private static MenuSignal useStairs(final Controller theController,
                                        final boolean theIsUp) {
        // Print with AttackResultAndAmount formatting from Controller
        theController.useStairs(theIsUp);

        return theController.isInCombat() ?
               MenuSignal.COMBAT :
               MenuSignal.EXPLORATION;
    }

    private static MenuSignal move(final Controller theController) {
        final int choice = MOVE_MENU.select(
                getInvalidDirections(theController)
        );
        System.out.println(
                theController.moveAdventurer(Direction.values()[choice])
        );

        return switch (choice) {
            case 0, 1, 2, 3 -> theController.isInCombat() ?
                    MenuSignal.COMBAT : MenuSignal.EXPLORATION;
            default -> MenuSignal.PREVIOUS;
        };
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

    private static void openSecretMenu(final Controller theController) {
        System.out.print(UNEXPLORED_HIDDEN);
        System.out.println(theController.isUnexploredHidden());

        if (SECRET_MENU.select() == 0) {
            theController.toggleIsUnexploredHidden();
        }
    }
}
