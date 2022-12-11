package view;

import controller.Controller;

public class LoadGameView {

    private static final String GAME_LOADED = "Game loaded.\n";

    private static final String LOAD_PROMPT = "Choose a file to load";

    static MenuSignal open(final Controller theController) {
        if (!SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.PREVIOUS;
        }

        final String[] files = theController.getSaveFiles();
        final Menu selectFileMenu = new Menu(
                LOAD_PROMPT, files, true, false, true
        );

        int choice;
        while (true) {
            choice = selectFileMenu.select();

            if (Menu.isBack(choice)) {
                return MenuSignal.PREVIOUS;
            }
            if (theController.loadGame(files[choice])) {
                System.out.println(GAME_LOADED);

                return Util.nextMenuFromCombatOrExploration(
                        theController, false
                );
            }
        }
    }
}
