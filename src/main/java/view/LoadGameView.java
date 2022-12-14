package view;

import controller.Controller;

public class LoadGameView {

    /**
     * String format template to alert the game has loaded.
     *
     */
    private static final String GAME_LOADED = "Game loaded.\n";


    /**
     * String format template to prompt player's input for file load.
     *
     */
    private static final String LOAD_PROMPT = "Choose a file to load";

    /**
     * Displays the Load Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal to open the next menu type.
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
