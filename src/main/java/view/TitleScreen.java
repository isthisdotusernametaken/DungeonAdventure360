package view;

public class TitleScreen {

    private static final Menu TITLE_MENU = new Menu(
            "Dungeon Adventure",
            new String[]{
                "New Game", "Guide", "Quit"
            },
            new String[]{
                "N", "G", "Q"
            },
            false
    );

    static MenuSignal open() {
        return switch (TITLE_MENU.select()) {
            case 0 -> MenuSignal.NEW_GAME;
            case 1 -> MenuSignal.PLAY_GUIDE;
            default -> MenuSignal.CONFIRM_EXIT;
        };
    }
}
