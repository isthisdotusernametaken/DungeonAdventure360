package view;

import controller.Controller;

/**
 * This class displays and executes the player's choices for loading instances
 * of the game.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class LoadGameView {

    /**
     * Alert that a game was loaded.
     */
    private static final String GAME_LOADED = "Game loaded.\n";

    /**
     * Prompt for player's input for file to load.
     */
    private static final String LOAD_PROMPT = "Choose a file to load";

    /**
     * Displays the Load Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The menu signal to return to the previous menu if canceled or
     *         open the current menu for the loaded game.
     */
    static MenuSignal open(final Controller theController) {
        if (!SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.PREVIOUS;
        }

        final String[] files = theController.getSaveFiles();
        final Menu selectFileMenu = new Menu(
                LOAD_PROMPT, files, true, false, true
        );

        int choice;
        while (true) {
            choice = selectFileMenu.select();

            if (Menu.isBack(choice)) {
                return MenuSignal.PREVIOUS;
            }
            if (theController.loadGame(files[choice])) {
                System.out.println(GAME_LOADED);

                return Util.nextMenuFromCombatOrExploration(
                        theController, false
                );
            }
        }
    }
}
