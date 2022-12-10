package view;

import controller.Controller;

public class Util {

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
