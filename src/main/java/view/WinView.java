package view;

import controller.Controller;

public class WinView {

    /**
     * Sets up a Win View Menu.
     */
    private static final Menu MENU = new Menu(
            "Congratulations! You beat the dungeon! Would you like to continue?",
            new String[]{"Continue", "Quit to Title Screen"},
            new String[]{"C", "Q"},
            false,
            false,
            false
    );

    /**
     * Displays the Win View Menu and prompts for the player's input to save the game
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the win view menu.
     */
    static MenuSignal open(final Controller theController) {
        while (true) {
            if (MENU.select() == 0) {
                return MenuSignal.PREVIOUS;
            } else if (SaveChangesInternalView
                        .askToContinueAndToSaveIfUnsaved(theController)) {
                return MenuSignal.TITLE_SCREEN;
            }
        }
    }
}
