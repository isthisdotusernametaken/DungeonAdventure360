package view;

import controller.Controller;

public class ConsoleUI {

    /**
     * The game controller to handle and access other utilities of the game
     */
    private final Controller myController;

    public ConsoleUI(final Controller theController) {
        myController = theController;
    }

    /**
     * Begins the processing of the console user interface menu for the dungeon game
     *
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
     * Calls and performs actions of the
     * selected menu option chosen by the player
     *
     * @param theSignal The menu signal of the dungeon game to decide
     *                  which menu option to function
     * @return The type of menu signal after the actions has already performed or finished
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
