/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import controller.Controller;

/**
 * This class returns to the title screen after (if the game has unsaved
 * changes) prompting the user to save. The return to the title screen may be
 * canceled during the save prompt.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class SaveAndQuitView {

    /**
     * Asks to save the game if unsaved changes exist and either returns to the
     * title screen or returns to the previous screen without saving, depending
     * on user input.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The menu signal to open the title screen if user chooses this or
     *         to return to the previous screen if the user cancels the
     *         operation.
     */
    static MenuSignal open(final Controller theController) {
        if (SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.TITLE_SCREEN;
        }

        return MenuSignal.PREVIOUS;
    }
}
