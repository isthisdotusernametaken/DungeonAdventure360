package view;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Util;

public class InputReader {

    private static Scanner CONSOLE_INPUT = new Scanner(System.in);

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 30;
    private static final String SPECIAL_CHARS = "()',._-";
    private static final String NAME_REGEX =
            "(?=.*[\\dA-Za-z" + SPECIAL_CHARS + "])" +
            "[ \\dA-Za-z" + SPECIAL_CHARS + "]" +
            "{" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH + "}";
    private static final String NAME_PROMPT =
            "Enter a name between " + MIN_NAME_LENGTH + " and " +
            MAX_NAME_LENGTH + " characters.\nAlphanumeric characters, " +
            "spaces (though not exclusively spaces), and the special " +
            "characters " + SPECIAL_CHARS + " are allowed.";

    private static final String WAIT_PROMPT = "Press enter to continue.";
    private static final String INVALID_INPUT = "Invalid input. Try again.\n";

    private static final String SECRET_OPTION = "oop";

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

    static boolean waitForEnter() {
        System.out.println(WAIT_PROMPT);
        return isSecret(readLine());
    }

    static boolean isSecret(final String theInput) {
        return SECRET_OPTION.equalsIgnoreCase(theInput);
    }
}
