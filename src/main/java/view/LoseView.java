package view;

public class LoseView {

    /**
     * String description to alert the game is over.
     */
    private static final String DEATH_MESSAGE =
            "You died. You've been returned to the title screen.\n";

    /**
     * Opens and displays the loss menu alerting the player
     * that the game is over.
     *
     * @return To the main menu or the title screen.
     */
    static MenuSignal open() {
        System.out.println(DEATH_MESSAGE);

        return MenuSignal.TITLE_SCREEN;
    }
}
