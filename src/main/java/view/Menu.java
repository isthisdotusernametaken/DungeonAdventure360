package view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Menu {

    static final String EXIT_MENU = "Q";

    private static final boolean[] ALL_INCLUDED = new boolean[0];

    private final int INVALID_SELECTION = -1;
    private static final String INVALID_SELECTION_MESSAGE =
            "Invalid menu option selected.";

    private static final int BACK = -2;


    private final String myTitle;
    private final String[] myMenuDescriptions;
    private final String[] myMenuOptions;
    private final boolean myIncludeBack;

    private final List<String> myLowerCaseDescriptions;
    private final List<String> myLowerCaseOptions;

    Menu(final String theTitle,
         final String[] theMenuDescriptions,
         final String[] theMenuOptions,
         final boolean theIncludeBack) {
        myTitle = theTitle;

        myMenuDescriptions = theMenuDescriptions;
        myMenuOptions = theMenuOptions;

        myLowerCaseDescriptions = toLower(myMenuDescriptions);
        myLowerCaseOptions = toLower(myMenuOptions);

        myIncludeBack = theIncludeBack;
    }

    Menu(final String theTitle,
         final String[] theMenuDescriptions,
         final boolean theIncludeBack) {
        this(
                theTitle,
                theMenuDescriptions,
                IntStream.rangeClosed(1, theMenuDescriptions.length)
                         .mapToObj(index -> "" + index)
                         .toArray(String[]::new),
                theIncludeBack
        );
    }

    static boolean isBack(final int theSelection) {
        return BACK == theSelection;
    }

    static boolean isBack(final String theSelection) {
        return EXIT_MENU.equalsIgnoreCase(theSelection);
    }

    private static List<String> toLower(final String[] theStrings) {
        return Arrays.stream(theStrings).map(String::toLowerCase).toList();
    }

    int select() {
        printMenu(true, ALL_INCLUDED);

        return readOptionUntilValid(true, ALL_INCLUDED);
    }

    int select(final int[] theExcludedOptions) {
        final boolean[] includedOptions = includedOptions(theExcludedOptions);
        printMenu(false, includedOptions);

        return readOptionUntilValid(false, includedOptions);
    }

    private void printMenu(final boolean theAllIncluded,
                           final boolean[] theIncludedOptions) {
        System.out.print(myTitle);
        System.out.println(':');

        for (int i = 0; i < myMenuOptions.length; i++) {
            if (isIncluded(theAllIncluded, theIncludedOptions, i)) {
                System.out.print(myMenuOptions[i]);
                System.out.print(": ");
                System.out.println(myMenuDescriptions[i]);
            }
        }

        if (myIncludeBack) {
            System.out.print(EXIT_MENU);
            System.out.println(": Back");
        }
    }

    private int readOptionUntilValid(final boolean theAllIncluded,
                                     final boolean[] theIncludedOptions) {
        int selection;
        do {
            selection = readOption(theAllIncluded, theIncludedOptions);
        } while (INVALID_SELECTION == selection);

        return selection;
    }

    private int readOption(final boolean theAllIncluded,
                           final boolean[] theIncludedOptions) {
        final String input = InputReader.readLine().toLowerCase();

        if (myIncludeBack && EXIT_MENU.equalsIgnoreCase(input)) {
            return BACK;
        }

        int index = myLowerCaseOptions.indexOf(input);
        if (index < 0) {
            index = myLowerCaseDescriptions.indexOf(input);
        }

        if (
                index >= 0 &&
                isIncluded(theAllIncluded, theIncludedOptions, index)
        ) {
            System.out.println();
            return index;
        }

        System.out.println(INVALID_SELECTION_MESSAGE);
        System.out.println();
        return INVALID_SELECTION;
    }

    private boolean isIncluded(final boolean theAllIncluded,
                               final boolean[] theIncludedOptions,
                               final int theIndex) {
        return theAllIncluded || theIncludedOptions[theIndex];
    }

    private boolean[] includedOptions(final int[] theExcludedOptions) {
        final boolean[] included = new boolean[myMenuOptions.length];
        for (int i = 0; i < myMenuOptions.length; i++) {
            included[i] = isIncluded(theExcludedOptions, i);
        }

        return included;
    }

    private boolean isIncluded(final int[] theExcludedOptions,
                               final int theOption) {
        for (int option : theExcludedOptions) {
            if (theOption == option) {
                return false;
            }
        }

        return true;
    }
}
