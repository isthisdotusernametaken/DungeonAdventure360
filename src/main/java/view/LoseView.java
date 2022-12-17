/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

/**
 * This class alerts the player that they have lost the game and returns them
 * to the title screen.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class LoseView {

    /**
     * Alert that the game is over.
     */
    private static final String DEATH_MESSAGE =
            "You died. You've been returned to the title screen.\n";

    /**
     * Opens and displays the loss menu alerting the player
     * that the game is over.
     *
     * @return Menu signal to open the title screen.
     */
    static MenuSignal open() {
        System.out.println(DEATH_MESSAGE);

        return MenuSignal.TITLE_SCREEN;
    }
}
