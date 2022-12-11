package view;

import controller.Controller;

public class TitleScreen {

    private static final Menu TITLE_MENU = new Menu(
            "Dungeon Adventure",
            new String[]{
                "New Game", "Load Game",
                "Guide", "Quit"
            },
            new String[]{
                "N", "L",
                "G", "Q"
            },
            false,
            false
    );

    static MenuSignal open(final Controller theController) {
        theController.reset();

        return switch (TITLE_MENU.select()) {
            case 0 -> MenuSignal.NEW_GAME;
            case 1 -> MenuSignal.LOAD_GAME;
            case 2 -> MenuSignal.PLAY_GUIDE;
            default -> MenuSignal.EXIT;
        };
    }
}
