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
 * This class displays the winning message and allows the player to continue
 * their game or quit to the title screen.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class WinView {

    /**
     * Menu to choose between continuing and quiting.
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
     * Displays the win message and lets the player choose between continuing
     * the current game or quiting to the menu. If the player chooses to quit
     * to the menu, they will first be prompted to save if there are unsaved
     * changes.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The menu signal to return to the previous screen (exploration)
     *         or return to the title screen.
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
