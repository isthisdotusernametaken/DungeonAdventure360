package view;

import controller.Controller;

/**
 * This class starts the UI and repeatedly calls the next menu (as specified by
 * the user's choices in the previous menu) until the user chooses to exit the
 * game.
 */
public class ConsoleUI {

    /**
     * The game controller to handle and access other utilities of the game.
     */
    private final Controller myController;

    /**
     * Creates a new ConsoleUI that will use the specified controller to
     * interact with the game.
     *
     * @param theController The game controller to call public methods of the
     *                      model in response so the game updates,
     *                      and to return the result of interacting with the
     *                      game to the UI in a format the UI can print.
     */
    public ConsoleUI(final Controller theController) {
        myController = theController;
    }

    /**
     * Opens whichever menu the previous menu specifies until the exit signal
     * is received.
     */
    public void run() {
        MenuSignal previousMenu = MenuSignal.TITLE_SCREEN;
        MenuSignal currentMenu = MenuSignal.TITLE_SCREEN;
        MenuSignal nextMenu;

        while (currentMenu != MenuSignal.EXIT) {
            nextMenu = callFromSignal(currentMenu);

            if (nextMenu == MenuSignal.PREVIOUS) {
                nextMenu = previousMenu; // Only swap prev and curr
            }
            previousMenu = currentMenu;
            currentMenu = nextMenu;
        }
    }

    /**
     * Opens and returns the result from the specified menu.
     *
     * @param theSignal The signal for which menu to open.
     * @return The next menu signal after the actions have been performed.
     */
    private MenuSignal callFromSignal(final MenuSignal theSignal) {
        return switch (theSignal) {
            case TITLE_SCREEN -> TitleScreen.open(myController);
            case PLAY_GUIDE -> PlayGuide.open();
            case NEW_GAME -> NewGameView.open(myController);
            case LOAD_GAME -> LoadGameView.open(myController);
            case SAVE_GAME -> SaveGameView.open(myController);
            case SAVE_AND_QUIT_TO_TITLE -> SaveAndQuitView.open(myController);
            case EXPLORATION -> ExplorationView.open(myController);
            case MAP -> MapView.open(myController);
            case INVENTORY -> InventoryView.open(myController);
            case COMBAT -> CombatView.open(myController);
            case WIN -> WinView.open(myController);
            case LOSE -> LoseView.open();
            default -> throw new IllegalStateException(
                    "Unknown menu: " + theSignal
            );
        };
    }
}
