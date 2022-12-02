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
            nextMenu = callFromSignal(currentMenu, previousMenu);
            previousMenu = currentMenu;
            currentMenu = nextMenu;
        }
    }

    private MenuSignal callFromSignal(final MenuSignal theSignal,
                                      final MenuSignal thePrevious) {
        return switch (theSignal) {
            case PREVIOUS -> callFromSignal(thePrevious, theSignal);
            case TITLE_SCREEN -> TitleScreen.open();
            case PLAY_GUIDE -> PlayGuide.open();
            case NEW_GAME -> NewGameView.open(myController);
            case LOAD_GAME -> LoadGameView.open(myController);
            case SAVE_GAME -> SaveGameView.open(myController);
            case EXPLORATION -> ExplorationView.open(myController);
            case COMBAT -> CombatView.open(myController);
            case WIN -> MenuSignal.EXIT; // incomplete
            case LOSE -> MenuSignal.EXIT; // incomplete
            case CONFIRM_EXIT -> ConfirmExitView.open(myController);
            case EXIT -> MenuSignal.EXIT; // incomplete
        };
    }
}
