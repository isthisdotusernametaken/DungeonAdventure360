package view;

import controller.Controller;

public class SaveAndQuitView {

    /**
     * Displays the Save and Quit Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the save and quit menu.
     */
    static MenuSignal open(final Controller theController) {
        if (SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.TITLE_SCREEN;
        }

        return MenuSignal.PREVIOUS;
    }
}
