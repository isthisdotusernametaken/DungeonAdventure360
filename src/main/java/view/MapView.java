package view;

import controller.Controller;

public class MapView {

    /**
     * Opens and displays the current map of the dungeon game.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return To the exploration menu.
     */
    static MenuSignal open(final Controller theController) {
        System.out.println(theController.getMap());
        InputReader.waitForEnter();

        return MenuSignal.EXPLORATION;
    }
}
