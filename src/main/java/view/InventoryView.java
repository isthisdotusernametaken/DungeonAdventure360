package view;

import controller.Controller;

import java.util.Arrays;

public class InventoryView {

    /**
     * String format template to prompt for player's input for inventory menu.
     */
    private static final String SELECT_PROMPT =
            "Select an item to use, or press " + Menu.EXIT_OPTION +
            " to return";

    /**
     * Sets up a Secret Menu panel for inventory-use.
     * The menu will serve as the cheat option for player.
     *
     */
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Add maximum stack size of all items to inventory"
            },
            true,
            false,
            true
    );

    /**
     * Displays the Inventory Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal to go back to the previous menu panel if selected choice is to go back.
     */
    static MenuSignal open(final Controller theController) {
        int choice;
        while (true) {
            System.out.println(theController.getAdventurer());
            System.out.println();

            choice = selectItem(theController);

            if (Menu.isBack(choice)) {
                return MenuSignal.PREVIOUS;
            }

            if (Menu.isSecret(choice)) {
                openSecretMenu(theController);
            } else {
                System.out.println(theController.useInventoryItem(choice));
                System.out.println();
            }
        }
    }

    /**
     * Gets the list of collected item in the inventory.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The integer associated with the selected item's location in the inventory.
     */
    private static int selectItem(final Controller theController) {
        // Redone every loop because may change after use or cheat
        final String[] items = theController.getInventoryItems();

        return new Menu(
                SELECT_PROMPT, items, true, true, true
        ).select(getUnusableItems(theController, items.length));
    }

    /**
     * Gets and checks the amount of unusable items from the item list in.
     * the inventory
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The range of integer array depends on how many available items in the inventory.
     */
    private static int[] getUnusableItems(final Controller theController,
                                          final int theItemCount) {
        final int[] unusableItems = new int[theItemCount];
        int index = 0;

        for (int i = 0; i < theItemCount; i++) {
            if (!theController.canUseInventoryItem(i)) {
                unusableItems[index++] = i;
            }
        }

        return Arrays.copyOfRange(unusableItems, 0, index);
    }

    /**
     * Accesses the secret menu and performs cheat action in the inventory
     * when selected by the player.
     *
     * @param theController The game controller to handle
     *                     and access other utilities of the game.
     */
    private static void openSecretMenu(final Controller theController) {
        if (SECRET_MENU.select() == 0) {
            System.out.println(theController.addMaxItems());
        }
    }
}
