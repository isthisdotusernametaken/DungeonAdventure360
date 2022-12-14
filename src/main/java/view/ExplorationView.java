package view;

import controller.Controller;

import java.util.Arrays;

public class ExplorationView {

    /**
     * Sets up an Exploration Menu panel when not in combat.
     * The Exploration Menu includes the menu descriptions
     * and the menu key options associated with that descriptions.
     *
     */
    private static final Menu ROOM_MENU = new Menu(
            "Choose an action",
            new String[]{
                    "Exit dungeon",
                    "Take stairs up", "Take stairs down",
                    "Collect items",
                    "Move", "Open Inventory", "Open Map",
                    "Save", "Load", "Quit to Title Screen",
                    "Open Play Guide"
            },
            new String[]{
                    "E",
                    "Up", "Down",
                    "C",
                    "W", "I", "M",
                    "S", "L", "Q",
                    "P"
            },
            false,
            true,
            true
    );

    /**
     * Sets up Secret Menu panel for exploration-use.
     * The menu will serve as the cheat option for player.
     *
     */
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Toggle hiding of unexplored rooms on map"
            },
            true,
            false,
            true
    );

    /**
     * String description for the unexplored rooms.
     *
     */
    private static final String UNEXPLORED_HIDDEN =
            "Unexplored rooms hidden: ";

    /**
     * Displays the Exploration Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the exploration menu chosen by the player.
     */
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
                case 10: return MenuSignal.PLAY_GUIDE;
                case Menu.SECRET:
                    // internalSignal guaranteed still previous
                    openSecretMenu(theController);
                    break;
            }
        }

        return internalSignal;
    }

    /**
     * Checks, gets and adds the invalid options available for the player to the exploration menu.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The range of integer array depends on how many invalid options has satisfied the condition
     *         to be available in the exploration menu.
     */
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

    /**
     * Perform action of going up the stair or going down the stair when
     * selected by the player from the exploration menu.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal to return to the exploration menu.
     */
    private static MenuSignal useStairs(final Controller theController,
                                        final boolean theIsUp) {
        System.out.println(theController.useStairs(theIsUp));

        return Util.nextMenuFromCombatOrExploration(theController, false);
    }

    /**
     * Accesses the secret menu and performs cheat action in exploration mode
     * when selected by the player.
     *
     * @param theController The game controller to handle
     *                     and access other utilities of the game.
     */
    private static void openSecretMenu(final Controller theController) {
        System.out.print(UNEXPLORED_HIDDEN);
        System.out.println(theController.isUnexploredHidden());

        if (SECRET_MENU.select() == 0) {
            theController.toggleIsUnexploredHidden();
        }
    }
}
