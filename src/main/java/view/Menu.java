package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Menu {

    /**
     * String representing the exit option's key.
     */
    static final String EXIT_OPTION = "Q";

    /**
     * Integer value representing secret selection.
     */
    static final int SECRET = -3;

    /**
     * Integer value representing back selection.
     */
    private static final int BACK = -2;

    /**
     * Integer value representing invalid selection.
     */
    private static final int INVALID_SELECTION = -1;

    /**
     * String description to alert the selected option is invalid.
     *
     */
    private static final String INVALID_SELECTION_MESSAGE =
            "Invalid menu option selected.";

    /**
     *  Initial array representing all included option.
     */
    private static final boolean[] ALL_INCLUDED = new boolean[0];
    private final String myTitle;
    private final String[] myMenuDescriptions;
    private final String[] myMenuOptions;
    private final boolean myIncludeBack;
    private final boolean myIncludeSecret;
    private final boolean myIsColonAfterTitle;
    private final List<String> myLowerCaseDescriptions;
    private final List<String> myLowerCaseOptions;

    /**
     * Constructor to create the Menu panels
     *
     * @param theTitle The title of the menu.
     * @param theMenuDescriptions The description of the menu.
     * @param theMenuOptions    The list of options available in the menu.
     * @param theIncludeBack    The boolean true or false if there is back option,
     *                          or go back to previous menu.
     * @param theIncludeSecret  The boolean true or false if the menu has secret menu.
     * @param theIsColonAfterTitle The boolean true of false if there is colon after the title.
     */
    Menu(final String theTitle,
         final String[] theMenuDescriptions,
         final String[] theMenuOptions,
         final boolean theIncludeBack,
         final boolean theIncludeSecret,
         final boolean theIsColonAfterTitle) {
        myTitle = theTitle;

        myMenuDescriptions = theMenuDescriptions.clone();
        myMenuOptions = theMenuOptions.clone();

        myLowerCaseDescriptions = toLower(myMenuDescriptions);
        myLowerCaseOptions = toLower(myMenuOptions);

        myIncludeBack = theIncludeBack;
        myIncludeSecret = theIncludeSecret;
        myIsColonAfterTitle = theIsColonAfterTitle;
    }

    /**
     * Constructor to create the Menu panels.
     *
     * @param theTitle The title of the menu.
     * @param theMenuDescriptions The description of the menu.
     * @param theIncludeBack    The boolean true or false if there is back option,
     *                          or go back to previous menu.
     * @param theIncludeSecret  The boolean true or false if the menu has secret menu.
     * @param theIsColonAfterTitle The boolean true of false if there is colon after the title.
     */
    Menu(final String theTitle,
         final String[] theMenuDescriptions,
         final boolean theIncludeBack,
         final boolean theIncludeSecret,
         final boolean theIsColonAfterTitle) {
        this(
                theTitle,
                theMenuDescriptions,
                IntStream.rangeClosed(1, theMenuDescriptions.length)
                         .mapToObj(index -> "" + index)
                         .toArray(String[]::new),
                theIncludeBack,
                theIncludeSecret,
                theIsColonAfterTitle
        );
    }

    /**
     * @param theSelection The input entered by the player.
     *
     * @return The boolean true or false if the input is equal to the value
     *          of back selection.
     */
    static boolean isBack(final int theSelection) {
        return BACK == theSelection;
    }

    /**
     * @param theSelection The input entered by the player.
     *
     * @return The boolean true or false if the input is matches
     *          the exit option's key.
     */
    static boolean isBack(final String theSelection) {
        return EXIT_OPTION.equalsIgnoreCase(theSelection);
    }


    /**
     * @param theSelection The input entered by the player.
     *
     * @return The boolean true or false if the input is equal to the value
     *          of secret selection.
     */
    static boolean isSecret(final int theSelection) {
        return SECRET == theSelection;
    }

    /**
     * Lowers and formats the string from the list.
     *
     * @param theStrings The list of strings.
     *
     * @return The list of string in formatted lower case.
     */
    private static List<String> toLower(final String[] theStrings) {
        return new ArrayList<>(
                Arrays.stream(theStrings).map(String::toLowerCase).toList()
        );
    }

    /**
     * Gets the selected option from player's input.
     *
     * @return The input from the player.
     */
    int select() {
        return promptAndReadOptionUntilValid(true, ALL_INCLUDED);
    }

    /**
     * Get the selected option from player's input
     * form the menu with all included options.
     *
     * @param theVariableChoice The description of added option choice.
     * @param theVariableChoiceIsIncluded The boolean to check if the choice is included.
     *
     * @return To select() to get input from player.
     */
    int select(final String theVariableChoice,
               final boolean theVariableChoiceIsIncluded) {
        final int lastIndex = myMenuDescriptions.length - 1;

        if (!theVariableChoiceIsIncluded) {
            return select(new int[]{lastIndex});
        }

        myMenuDescriptions[lastIndex] = theVariableChoice;
        myLowerCaseDescriptions.set(lastIndex, theVariableChoice.toLowerCase());

        return select();
    }

    /**
     * Get the selected option from player's input
     * form the menu with some excluded options.
     *
     * @return The input selection from the player.
     */
    int select(final int[] theExcludedOptions) {
        return promptAndReadOptionUntilValid(
                false, includedOptions(theExcludedOptions)
        );
    }

    /**
     * Displays the menu panel with the included options.
     *
     * @param theAllIncluded The boolean true or false to check if all options are included.
     * @param theIncludedOptions The boolean array representing the boolean value of included options.
     */
    private void printMenu(final boolean theAllIncluded,
                           final boolean[] theIncludedOptions) {
        System.out.print(myTitle);
        System.out.println(myIsColonAfterTitle ? ":" : "");

        for (int i = 0; i < myMenuOptions.length; i++) {
            if (isIncluded(theAllIncluded, theIncludedOptions, i)) {
                System.out.print(myMenuOptions[i]);
                System.out.print(": ");
                System.out.println(myMenuDescriptions[i]);
            }
        }

        if (myIncludeBack) {
            System.out.print(EXIT_OPTION);
            System.out.println(": Back");
        }
    }

    /**
     * Prompts, accesses, reads, and checks the selected option choice from the player.
     *
     * @param theAllIncluded The boolean true or false to check if all options are included.
     * @param theIncludedOptions The boolean array representing the boolean value of included options.
     *
     * @return The selection choice inputted by the player.
     */
    private int promptAndReadOptionUntilValid(final boolean theAllIncluded,
                                              final boolean[] theIncludedOptions) {
        int selection;
        do {
            printMenu(theAllIncluded, theIncludedOptions);

            selection = readOption(theAllIncluded, theIncludedOptions);
        } while (INVALID_SELECTION == selection);

        return selection;
    }

    /**
     * Prompts, accesses, reads, and checks the selected option choice from the player if it is valid.
     *
     * @param theAllIncluded The boolean true or false to check if all options are included.
     * @param theIncludedOptions The boolean array representing the boolean value of included options.
     *
     * @return The selection choice inputted by the player.
     */
    private int readOption(final boolean theAllIncluded,
                           final boolean[] theIncludedOptions) {
        final String input = InputReader.readLine().toLowerCase();

        if (myIncludeBack && EXIT_OPTION.equalsIgnoreCase(input)) {
            System.out.println();
            return BACK;
        }
        if (myIncludeSecret && InputReader.isSecret(input)) {
            System.out.println();
            return SECRET;
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


    /**
     * Checks if the all option is included in the menu.
     *
     * @param theAllIncluded The boolean true or false to check if all options are included.
     * @param theIncludedOptions The boolean array representing the boolean value of included options.
     * @param theIndex The index representing the location of each option.
     *
     * @return The boolean true or false if the option is included.
     */
    private boolean isIncluded(final boolean theAllIncluded,
                               final boolean[] theIncludedOptions,
                               final int theIndex) {
        return theAllIncluded || theIncludedOptions[theIndex];
    }

    /**
     * Checks if the excluded option is included in the menu and adds its boolean value to the menu.
     *
     * @param theExcludedOptions The integer array of the excluded options.
     *
     * @return The boolean true or false if the option is included.
     */
    private boolean[] includedOptions(final int[] theExcludedOptions) {
        final boolean[] included = new boolean[myMenuOptions.length];
        for (int i = 0; i < myMenuOptions.length; i++) {
            included[i] = isIncluded(theExcludedOptions, i);
        }

        return included;
    }

    /**
     * Checks if the excluded option is included in the menu.
     *
     * @param theExcludedOptions The integer array of the excluded options.
     * @param theOption The index location of the option.
     *
     * @return The boolean true or false if the option is included.
     */
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
