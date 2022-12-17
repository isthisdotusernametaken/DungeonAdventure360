/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import java.util.Arrays;

import controller.Controller;

/**
 * This class displays the contents of the player's inventory and allows the
 * use of the items that are visible in the current context (combat or
 * exploration).
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class InventoryView {

    /**
     * Prompt for player's input for inventory menu.
     */
    private static final String SELECT_PROMPT =
            "Select an item to use, or press " + Menu.EXIT_OPTION +
            " to return";

    /**
     * Sets up a Secret Menu panel for inventory use.
     * The menu will serve as the cheat option for player.
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
     * Displays the Inventory menu and tries to use the selected item or
     * performs another action selected by the player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The menu signal to go back to the previous menu when the
     *         inventory is closed.
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
     * Displays and selects an item, back, or the secret option.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The integer associated with the selected item's location in the
     *         inventory, or the signal for another option.
     */
    private static int selectItem(final Controller theController) {
        // Redone every loop because may change after use or cheat
        final String[] items = theController.getInventoryItems();

        return new Menu(
                SELECT_PROMPT, items, true, true, true
        ).select(getUnusableItems(theController, items.length));
    }

    /**
     * Finds which items in the inventory should not be viewed in the current
     * context (does not display items that don't apply to combat)
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The options to exclude from the menu. The array's size depends
     *         on how many invalid options there are.
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
     * @param theController The game controller to handle and access other
     *                      utilities of the game.
     */
    private static void openSecretMenu(final Controller theController) {
        if (SECRET_MENU.select() == 0) {
            System.out.println(theController.addMaxItems());
        }
    }
}
