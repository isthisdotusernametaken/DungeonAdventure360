package view;

import controller.Controller;

public class SaveGameView {

    private static final Menu CONFIRM_OVERWRITE_MENU = null;

    static MenuSignal open(final Controller theController) {
        return MenuSignal.PREVIOUS;
    }

    private static void displayFiles(final Controller theController) {

    }

    private static String readFileName() {
        return "";
    }

    private static String attemptSaveGame(final Controller theController) {
        return "";
    }
}
