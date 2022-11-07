package view;

import java.util.Scanner;

public class InputReader {

    private static final Scanner CONSOLE_INPUT = new Scanner(System.in);
    private static final String EXIT_MENU = "Q";

    static String readLine() {
        return CONSOLE_INPUT.nextLine();
    }

    static boolean exitSent(String theUserInput) {
        return EXIT_MENU.equalsIgnoreCase(theUserInput);
    }
}
