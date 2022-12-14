package view;

import controller.Controller;

public class Util {

    /**
     * Analyzes and checks if the next menu signal from combat or exploration
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @param theIsInCombatView The boolean true or false if currently in combat
     *
     * @return The menu signal.
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
