package view;

import controller.Controller;

public class ConsoleUI {
    private final Controller myController;
    private MenuSignal myPreviousMenu;

    public ConsoleUI(Controller theController) {
        myController = theController;
    }

    public void run() {

    }

    private MenuSignal callFromSignal(MenuSignal theSignal) {
        return theSignal;
    }
}
