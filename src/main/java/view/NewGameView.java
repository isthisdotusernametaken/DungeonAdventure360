package view;

import controller.Controller;
import model.Difficulty;
import model.DungeonAdventure;

import java.util.Arrays;

public class NewGameView {

    /**
     * Sets up a Hero Class Choosing Menu panel.
     */
    private static final Menu CLASS_MENU = new Menu(
            "Choose an Adventurer class",
            DungeonAdventure.getAdventurerClasses(),
            true,
            false,
            true
    );

    /**
     * Sets up a Difficulty Choosing menu.
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
     * String description prompts for input from player for adventurer name.
     */
    private static final String ADVENTURER_NAME_PROMPT =
            "Enter the name of the Adventurer (or nothing to have a name " +
            "generated for you)";

    /**
     * String description prompts for input from player to back to previous screen.
     */
    private static final String BACK_PROMPT =
            " (or " + Menu.EXIT_OPTION + " to return to the previous screen):";

    /**
     * Displays the New Game Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the new game menu chosen by the player.
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
     * Displays descriptions prompt for player's input when in new game mode.
     *
     * @return The name of adventurer after validated.
     */
    private static String readAdventurerName() {
        System.out.print(ADVENTURER_NAME_PROMPT);
        System.out.println(BACK_PROMPT);

        return InputReader.readNameUntilValid(true);
    }
}
