package view;

import controller.Controller;

public class MapView {

    private static final String PROMPT = "Press enter to close the map.";

    static MenuSignal open(final Controller theController) {
        System.out.println(theController.getGame().getMap());
        waitForExit();

        return MenuSignal.EXPLORATION;
    }

    private static void waitForExit() {
        System.out.println(PROMPT);
        InputReader.readLine();
    }
}
