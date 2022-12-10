package view;

import controller.Controller;
import model.Util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {

    private static Scanner CONSOLE_INPUT = new Scanner(System.in);

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
                result = CONSOLE_INPUT.nextLine();
                System.out.println();

                if ((theAllowEmpty && Util.NONE.equals(result)) ||
                    result.matches(Controller.NAME_REGEX)) {
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
