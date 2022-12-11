package view;

import controller.Controller;

public class SaveAndQuitView {

    static MenuSignal open(final Controller theController) {
        if (SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.TITLE_SCREEN;
        }

        return MenuSignal.PREVIOUS;
    }
}
