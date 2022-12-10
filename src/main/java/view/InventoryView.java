package view;

import java.util.Arrays;

import controller.Controller;

public class InventoryView {

    private static final String SELECT_PROMPT =
            "Select an item to use, or press " + Menu.EXIT_OPTION +
            " to return";
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Add maximum stack size of all items to inventory"
            },
            true,
            false
    );

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

    private static int selectItem(final Controller theController) {
        // Redone every loop because may change after use or cheat
        final String[] items = theController.getInventoryItems();
        final Menu inventory = new Menu(SELECT_PROMPT, items, true, true);

        return (
                theController.isInCombat() ?
                inventory.select(
                        getUnusableItems(theController, items.length)
                ) :
                inventory.select()
        );
    }

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

    private static void openSecretMenu(final Controller theController) {
        if (SECRET_MENU.select() == 0) {
            theController.addMaxItems();
        }
    }
}
