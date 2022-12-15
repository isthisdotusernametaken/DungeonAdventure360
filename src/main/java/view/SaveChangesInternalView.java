package view;

import controller.Controller;

/**
 * This class prompts for the user to save if unsaved changes exist and
 * indicates to the caller whether the action should then be continued.
 */
public class SaveChangesInternalView {

    /**
     * Menu to prompt for saving unsaved changes.
     */
    private static final Menu MENU = new Menu(
            "You have unsaved changes. Would you like to save the game first?",
            new String[]{"Save", "Continue without saving"},
            new String[]{"Y", "N"},
            true,
            false,
            false
    );

    /**
     * Prompts for player to choose if they want to save the changes
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return True if the action should be continued (the game is saved or the
     *         user chooses to continue anyway). False if the action should be
     *         cancelled (the game is not saved and the user chose to go back).
     */
    static boolean askToContinueAndToSaveIfUnsaved(final Controller theController) {
        if (theController.isSaved()) {
            return true; // Continue if already saved;
        }

        // If not saved, decide whether to save and whether to continue
        switch (MENU.select()) {
            case 0: // Save and continue if user doesn't just exit save view
                SaveGameView.open(theController);
                return theController.isSaved();
            case 1: // Don't save, but continue anyway
                return true;
            default: // Cancel action
                return false;
        }
    }
}
