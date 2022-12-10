package view;

import controller.Controller;

public class MapView {

    static MenuSignal open(final Controller theController) {
        System.out.println(theController.getMap());
        InputReader.waitForEnter();

        return MenuSignal.EXPLORATION;
    }
}
