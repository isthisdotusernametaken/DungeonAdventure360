package view;

import controller.Controller;
import model.Util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {

    private static Scanner CONSOLE_INPUT = new Scanner(System.in);

    private static final String INVALID_INPUT = "Invalid input. Try again.\n";

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

    static String readNameUntilValid() {
        String result;

        while (true) {
            try {
                result = CONSOLE_INPUT.nextLine();
                System.out.println();

                if (result.matches(Controller.NAME_REGEX)) {
                    return result;
                }
            } catch (NoSuchElementException e) {
                CONSOLE_INPUT = new Scanner(System.in);
            }

            System.out.println(INVALID_INPUT);
        }
    }
}
