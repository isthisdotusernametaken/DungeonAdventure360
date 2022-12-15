package view;

import controller.Controller;

/**
 * This class displays the options to start or exit the game. It is displayed
 * when the application first opens.
 */
public class TitleScreen {

    /**
     * Menu to start a game, view the guide, or close the application.
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
     * Displays the title screen's menu and performs the action selected by the
     * player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return A menu signal to start the game, open the play guide, or close
     *         the application.
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
