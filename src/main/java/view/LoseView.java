package view;

public class LoseView {

    private static final String DEATH_MESSAGE =
            "You died. You've been returned to the title screen.\n";

    static MenuSignal open() {
        System.out.println(DEATH_MESSAGE);

        return MenuSignal.TITLE_SCREEN;
    }
}
