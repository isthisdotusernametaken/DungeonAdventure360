package view;

import controller.Controller;
import model.Direction;

import java.util.Arrays;

public class ExplorationView {

    private static final Menu ROOM_MENU = new Menu(
            "Current room:",
            new String[]{"Move", "Open Inventory", "Open Map", "Save", "Load", "Quit to Title Screen"},
            false
    );
    private static final Menu MOVE_MENU = new Menu(
            "Choose a direction to move",
            Arrays.stream(Direction.values())
                  .map(Direction::toString)
                  .toArray(String[]::new),
            Arrays.stream(Direction.values())
                  .map(dir -> dir.toString().substring(0, 1))
                  .toArray(String[]::new),
            true
    );

    static MenuSignal open(Controller theController) {
        return null;
    }


    private static void displayRoom(Controller theController) {

    }

    private static boolean move(Controller theController) {
        return false;
    }

    private static void collectItems(Controller theController) {

    }
}
