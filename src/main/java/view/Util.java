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
 * This class provides a common behavior for choosing the next menu within
 * several of the view's screens.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Util {

    /**
     * Determines the next screen to open, given the current state of the game.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @param theIsInCombatView Whether the UI currently has the combat screen
     *                          open
     *
     * @return The menu signal for combat (or the previous screen if already in
     * the combat screen), exploration, or a loss, depending on the game's
     * state.
     */
    static MenuSignal nextMenuFromCombatOrExploration(final Controller theController,
                                                      final boolean theIsInCombatView) {
        return theController.isAlive() ? (
                    theController.isInCombat() ?
                            theIsInCombatView ?
                                    MenuSignal.PREVIOUS :
                                    MenuSignal.COMBAT :
                            MenuSignal.EXPLORATION
               ) :
               MenuSignal.LOSE;
    }
}
