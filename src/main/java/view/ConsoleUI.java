package view;

import controller.Controller;

public class ConsoleUI {

    private final Controller myController;

    public ConsoleUI(Controller theController) {
        myController = theController;
    }

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

    private MenuSignal callFromSignal(final MenuSignal theSignal) {
        return switch (theSignal) {
            case TITLE_SCREEN -> TitleScreen.open();
            case PLAY_GUIDE -> PlayGuide.open();
            case NEW_GAME -> NewGameView.open(myController);
            case LOAD_GAME -> LoadGameView.open(myController);
            case SAVE_GAME -> SaveGameView.open(myController);
            case SAVE_AND_QUIT_TO_TITLE -> SaveAndQuitView.open(myController);
            case EXPLORATION -> ExplorationView.open(myController);
            case MAP -> MapView.open(myController);
            case INVENTORY -> InventoryView.open(myController);
            case COMBAT -> CombatView.open(myController);
            case WIN -> MenuSignal.EXIT; // incomplete
            case LOSE -> MenuSignal.EXIT; // incomplete
            case CONFIRM_EXIT -> ConfirmExitView.open(myController);
            case EXIT -> MenuSignal.EXIT; // incomplete
            default -> throw new IllegalStateException(
                    "Unknown menu: " + theSignal
            );
        };
    }
}
