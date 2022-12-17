package view;

import java.util.Arrays;

import controller.Controller;
import model.Difficulty;
import model.DungeonAdventure;

/**
 * This class displays the options for creating a new game and provides the
 * corresponding input to the controller.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class NewGameView {

    /**
     * Menu to choose a class.
     */
    private static final Menu CLASS_MENU = new Menu(
            "Choose an Adventurer class",
            DungeonAdventure.getAdventurerClasses(),
            true,
            false,
            true
    );

    /**
     * Menu to choose a difficulty.
     */
    private static final Menu DIFFICULTY_MENU = new Menu(
            "Choose a difficulty",
            Arrays.stream(Difficulty.values())
                  .map(Difficulty::toString)
                  .toArray(String[]::new),
            true,
            false,
            true
    );

    /**
     * Prompts for input from player for Adventurer name.
     */
    private static final String ADVENTURER_NAME_PROMPT =
            "Enter the name of the Adventurer (or nothing to have a name " +
            "generated for you)";

    /**
     * Prompts for input from player to go back to previous screen.
     */
    private static final String BACK_PROMPT =
            " (or " + Menu.EXIT_OPTION + " to return to the previous screen):";

    /**
     * Displays the options to make a new game and either provides the player's
     * input to the controller to make the game or returns to the previous
     * screen.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The signal to open the exploration screen for the entrance of
     *         the new game if successful, otherwise the signal to return to
     *         the previous screen.
     */
    static MenuSignal open(final Controller theController) {
        if (!SaveChangesInternalView.askToContinueAndToSaveIfUnsaved(theController)) {
            return MenuSignal.PREVIOUS;
        }

        final int adventurerClass = CLASS_MENU.select();
        if (Menu.isBack(adventurerClass)) {
            return MenuSignal.PREVIOUS;
        }
        final int difficulty = DIFFICULTY_MENU.select();
        if (Menu.isBack(difficulty)) {
            return MenuSignal.PREVIOUS;
        }
        final String adventurerName = readAdventurerName();
        if (Menu.isBack(adventurerName)) {
            return MenuSignal.PREVIOUS;
        }

        return theController.createGame(
                adventurerName, adventurerClass, difficulty
        ) ?
                MenuSignal.EXPLORATION :
                MenuSignal.PREVIOUS; // Couldn't create game (might change
                                     // behavior later, but should never be
                                     // encountered anyway)
    }

    /**
     * Displays prompt for inputting Adventurer's name and waits for valid
     * input (or nothing to keep the randomly generated name).
     *
     * @return The validated name of the Adventurer.
     */
    private static String readAdventurerName() {
        System.out.print(ADVENTURER_NAME_PROMPT);
        System.out.println(BACK_PROMPT);

        return InputReader.readNameUntilValid(true);
    }
}
