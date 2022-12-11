package view;

import java.util.Arrays;

import controller.Controller;

public class ExplorationView {

    private static final Menu ROOM_MENU = new Menu(
            "Choose an action",
            new String[]{
                    "Exit dungeon",
                    "Take stairs up", "Take stairs down",
                    "Collect items",
                    "Move", "Open Inventory", "Open Map",
                    "Save", "Load", "Quit to Title Screen"
            },
            new String[]{
                    "E",
                    "Up", "Down",
                    "C",
                    "W", "I", "M",
                    "S", "L", "Q"
            },
            false,
            true,
            true
    );
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Toggle hiding of unexplored rooms on map"
            },
            true,
            false,
            true
    );

    private static final String UNEXPLORED_HIDDEN =
            "Unexplored rooms hidden: ";

    static MenuSignal open(final Controller theController) {
        System.out.println(theController.getRoom());
        System.out.println(theController.getAdventurer());

        MenuSignal internalSignal = MenuSignal.PREVIOUS;
        while (internalSignal == MenuSignal.PREVIOUS) {
            switch (ROOM_MENU.select(getInvalidRoomOptions(theController))) {
                case 0: return MenuSignal.WIN;
                case 1: return useStairs(theController, true);
                case 2: return useStairs(theController, false);
                case 3:
                    System.out.println(theController.collectItems());
                    break;
                case 4:
                    internalSignal = MoveInternalView.open(
                            theController, theController::moveAdventurer, false
                    );
                    break;
                case 5: return MenuSignal.INVENTORY;
                case 6: return MenuSignal.MAP;
                case 7: return MenuSignal.SAVE_GAME;
                case 8: return MenuSignal.LOAD_GAME;
                case 9: return MenuSignal.SAVE_AND_QUIT_TO_TITLE;
                case Menu.SECRET:
                    // internalSignal guaranteed still previous
                    openSecretMenu(theController);
                    break;
            }
        }

        return internalSignal;
    }

    private static int[] getInvalidRoomOptions(final Controller theController) {
        final int[] invalid = new int[4];
        int index = 0;

        if (!theController.canExit()) {
            index++; // Means invalid[index++] = 0, but invalid[0] already 0
        }
        if (!theController.hasStairs(true)) {
            invalid[index++] = 1;
        }
        if (!theController.hasStairs(false)) {
            invalid[index++] = 2;
        }
        if (!theController.roomHasItems()) {
            invalid[index++] = 3;
        }

        return Arrays.copyOfRange(invalid, 0, index);
    }

    private static MenuSignal useStairs(final Controller theController,
                                        final boolean theIsUp) {
        System.out.println(theController.useStairs(theIsUp));

        return Util.nextMenuFromCombatOrExploration(theController, false);
    }

    private static void openSecretMenu(final Controller theController) {
        System.out.print(UNEXPLORED_HIDDEN);
        System.out.println(theController.isUnexploredHidden());

        if (SECRET_MENU.select() == 0) {
            theController.toggleIsUnexploredHidden();
        }
    }
}
