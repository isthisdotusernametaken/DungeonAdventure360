package view;

import controller.Controller;

public class WinView {

    private static final Menu MENU = new Menu(
            "Congratulations! You beat the dungeon! Would you like to continue?",
            new String[]{"Continue", "Quit to Title Screen"},
            new String[]{"C", "Q"},
            false,
            false
    );

    static MenuSignal open(final Controller theController) {
        while (true) {
            if (MENU.select() == 0) {
                return MenuSignal.PREVIOUS;
            } else if (SaveChangesInternalView
                        .askToContinueAndToSaveIfUnsaved(theController)) {
                return MenuSignal.TITLE_SCREEN;
            }
        }
    }
}
