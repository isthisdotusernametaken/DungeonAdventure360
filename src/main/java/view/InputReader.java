package view;

import model.Util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {

    /**
     * A scanner to get console input from the player.
     *
     */
    private static Scanner CONSOLE_INPUT = new Scanner(System.in);

    /**
     * Integer value representing the allowed minimum length for player's name.
     *
     */
    private static final int MIN_NAME_LENGTH = 1;

    /**
     * Integer value representing the allowed maximum length for player's name.
     *
     */
    private static final int MAX_NAME_LENGTH = 30;

    /**
     * Characters representing the allowed special characters for player's name.
     *
     */
    private static final String SPECIAL_CHARS = "()',._-";

    /**
     * Regex string representing the allowed patterns and range of characters and length
     * for player's name.
     *
     */
    private static final String NAME_REGEX =
            "(?=.*[\\dA-Za-z" + SPECIAL_CHARS + "])" +
            "[ \\dA-Za-z" + SPECIAL_CHARS + "]" +
            "{" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH + "}";

    /**
     * String format template to prompt for player name
     *
     */
    private static final String NAME_PROMPT =
            "Enter a name between " + MIN_NAME_LENGTH + " and " +
            MAX_NAME_LENGTH + " characters.\nAlphanumeric characters, " +
            "spaces (though not exclusively spaces), and the special " +
            "characters " + SPECIAL_CHARS + " are allowed.";


    /**
     * String format template to wait for input prompt from the player.
     *
     */
    private static final String WAIT_PROMPT = "Press enter to continue.";

    /**
     * String format template to alert invalid input from the player.
     *
     */
    private static final String INVALID_INPUT = "Invalid input. Try again.\n";

    /**
     * String format template to get secret option.
     *
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
     * Scans, reads and checks the string name entered by the player to see
     * if it matches the allowed string name format of the game.
     *
     * @param theAllowEmpty The boolean indicates if the string name is allowed to be empty.
     * @return The result string entered by the player.
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
     * Displays the string context asking/prompting for player next input.
     *
     * @return The boolean true or false if the player has
     *          selected the secret menu.
     */
    static boolean waitForEnter() {
        System.out.println(WAIT_PROMPT);
        return isSecret(readLine());
    }

    /**
     * Scans and checks the player's input if matches
     * the secret menu's string context to open secret menu.
     *
     * @param theInput The input entered by the player.
     * @return The boolean true or false if the player has
     *          selected the secret menu.
     */
    static boolean isSecret(final String theInput) {
        return SECRET_OPTION.equalsIgnoreCase(theInput);
    }
}
