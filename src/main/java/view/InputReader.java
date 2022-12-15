package view;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Util;

/**
 * This class centralizes the behavior for reading valid input from the
 * console.
 */
public class InputReader {

    /**
     * A scanner to get console input from the player.
     */
    private static Scanner CONSOLE_INPUT = new Scanner(System.in);

    /**
     * Integer value representing the allowed minimum length for names within
     * the program, including game names and Adventurer names.
     */
    private static final int MIN_NAME_LENGTH = 1;

    /**
     * Integer value representing the allowed maximum length for names.
     */
    private static final int MAX_NAME_LENGTH = 30;

    /**
     * Characters representing the allowed special characters for names.
     */
    private static final String SPECIAL_CHARS = "()',._-";

    /**
     * Regex string representing the allowed patterns and range of characters
     * and length for names.
     */
    private static final String NAME_REGEX =
            "(?=.*[\\dA-Za-z" + SPECIAL_CHARS + "])" +
            "[ \\dA-Za-z" + SPECIAL_CHARS + "]" +
            "{" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH + "}";

    /**
     * Description and prompt to enter a name that fits the regex
     */
    private static final String NAME_PROMPT =
            "Enter a name between " + MIN_NAME_LENGTH + " and " +
            MAX_NAME_LENGTH + " characters.\nAlphanumeric characters, " +
            "spaces (though not exclusively spaces), and the special " +
            "characters " + SPECIAL_CHARS + " are allowed.";


    /**
     * Prompt for any input.
     */
    private static final String WAIT_PROMPT = "Press enter to continue.";

    /**
     * Reporting that entered input was invalid
     */
    private static final String INVALID_INPUT = "Invalid input. Try again.\n";

    /**
     * Hidden option to open a secret menu wherever one exists in the game
     */
    private static final String SECRET_OPTION = "oop";

    /**
     * Scans and reads player's input.
     *
     * @return The input entered by the player.
     */
    static String readLine() {
        String input = Util.NONE;
        try {
            input = CONSOLE_INPUT.nextLine();
        } catch (NoSuchElementException e) {
            CONSOLE_INPUT = new Scanner(System.in);
        }
        System.out.println();

        return input;
    }

    /**
     * Repeatedly reads and checks the name entered by the player until it
     * matches the allowed name format of the game.
     *
     * @param theAllowEmpty The boolean indicates if the name is allowed to be
     *                      empty.
     * @return The validated name entered by the player.
     */
    static String readNameUntilValid(final boolean theAllowEmpty) {
        String result;

        while (true) {
            try {
                System.out.println(NAME_PROMPT);
                result = CONSOLE_INPUT.nextLine();
                System.out.println();

                if ((theAllowEmpty && Util.NONE.equals(result)) ||
                    result.matches(NAME_REGEX)) {
                    return result;
                }
            } catch (NoSuchElementException e) {
                CONSOLE_INPUT = new Scanner(System.in);
            }

            System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Waits for the player to press enter and indicates this to the player.
     *
     * @return Whether the player has selected the secret option. May be
     *         ignored if not needed.
     */
    static boolean waitForEnter() {
        System.out.println(WAIT_PROMPT);
        return isSecret(readLine());
    }

    /**
     * Checks whether the player has selected the secret option.
     *
     * @param theInput The input entered by the player.
     * @return Whether the player has selected the secret menu.
     */
    static boolean isSecret(final String theInput) {
        return SECRET_OPTION.equalsIgnoreCase(theInput);
    }
}
