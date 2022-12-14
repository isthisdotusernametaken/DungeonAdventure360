package view;

import controller.Controller;

public class TitleScreen {


    /**
     * Sets up a Title Screen Menu panel/Main Menu panel.
     */
    private static final Menu TITLE_MENU = new Menu(
            "Dungeon Adventure",
            new String[]{
                "New Game", "Load Game",
                "Guide", "Quit"
            },
            new String[]{
                "N", "L",
                "G", "Q"
            },
            false,
            false,
            true
    );

    /**
     * Displays the Title Screen Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the title menu.
     */
    static MenuSignal open(final Controller theController) {
        theController.reset();

        return switch (TITLE_MENU.select()) {
            case 0 -> MenuSignal.NEW_GAME;
            case 1 -> MenuSignal.LOAD_GAME;
            case 2 -> MenuSignal.PLAY_GUIDE;
            default -> MenuSignal.EXIT;
        };
    }
}
