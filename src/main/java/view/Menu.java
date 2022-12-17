package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class displays the player's options from a certain menu (possibly
 * excluding options the player cannot use) and returns the index of the
 * player's choice once a valid option has been entered.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class Menu {

    /**
     * String representing the exit option's key.
     */
    static final String EXIT_OPTION = "Q";

    /**
     * Signal to open a secret menu.
     */
    static final int SECRET = -3;

    /**
     * Signal to go back to the previous menu.
     */
    private static final int BACK = -2;

    /**
     * Signal indicating the choice was invalid.
     */
    private static final int INVALID_SELECTION = -1;

    /**
     * Alert that the selected option is invalid.
     */
    private static final String INVALID_SELECTION_MESSAGE =
            "Invalid menu option selected.";

    /**
     * Array indicating that no options are excluded from the menu.
     */
    private static final boolean[] ALL_INCLUDED = new boolean[0];

    /**
     * The title to be displayed at the start of the menu
     */
    private final String myTitle;
    /**
     * The descriptions to display for each option
     */
    private final String[] myMenuDescriptions;
    /**
     * The options the player can select. This will be printed to the player so
     * the player knows what choices are possible
     */
    private final String[] myMenuOptions;
    /**
     * Whether a back option is included in the menu
     */
    private final boolean myIncludeBack;
    /**
     * Whether a secret option is included in the menu. If so, this information
     * will not be communicated to the player
     */
    private final boolean myIncludeSecret;
    /**
     * Whether a colon should be printed at the end of the title
     */
    private final boolean myIsColonAfterTitle;

    /**
     * A copy of the options' descriptions to compare case-insensitively
     * against the player's choice and return the index of the chosen option
     */
    private final List<String> myLowerCaseDescriptions;
    /**
     * A copy of the options to compare case-insensitively against the player's
     * choice and return the index of the chosen option
     */
    private final List<String> myLowerCaseOptions;

    /**
     * Creates a Menu with the provided options and descriptions.
     *
     * @param theTitle The title of the menu.
     * @param theMenuDescriptions The descriptions of the menu's options.
     * @param theMenuOptions The list of options available in the menu.
     * @param theIncludeBack Whether a back option will be included.
     * @param theIncludeSecret Whether the menu allows the secret option to be
     *                         chosen.
     * @param theIsColonAfterTitle Whether a colon should be printed after the
     *                             title.
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
     * Creates a menu with the provided descriptions. Indices 1, 2, 3, ... are
     * used for the options associated with each description.
     *
     * @param theTitle The title of the menu.
     * @param theMenuDescriptions The descriptions of the menu's options.
     * @param theIncludeBack Whether a back option will be included.
     * @param theIncludeSecret Whether the menu allows the secret option to be
     *                         chosen.
     * @param theIsColonAfterTitle Whether a colon should be printed after the
     *                             title.
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
     * @return Whether the input matches the signal to go back.
     */
    static boolean isBack(final int theSelection) {
        return BACK == theSelection;
    }

    /**
     * @param theSelection The input entered by the player.
     *
     * @return Whether the input matches the option to go back.
     */
    static boolean isBack(final String theSelection) {
        return EXIT_OPTION.equalsIgnoreCase(theSelection);
    }


    /**
     * @param theSelection The input entered by the player.
     *
     * @return Whether the input matches the signal to open a secret menu.
     */
    static boolean isSecret(final int theSelection) {
        return SECRET == theSelection;
    }

    /**
     * Copies the provided array of Strings into a lowercase List. This allows
     * List.indexOf to be used to find the index of the player's choice
     * case-insensitively.
     *
     * @param theStrings The options or descriptions to place in a List.
     *
     * @return The lowercase List of the provided Strings.
     */
    private static List<String> toLower(final String[] theStrings) {
        return new ArrayList<>(
                Arrays.stream(theStrings).map(String::toLowerCase).toList()
        );
    }

    /**
     * Gets the selected option from player's input.
     *
     * @return The index of the player's choice.
     */
    int select() {
        return promptAndReadOptionUntilValid(true, ALL_INCLUDED);
    }

    /**
     * Get the selected option from player's input after replacing the last
     * description with the provided String.
     *
     * @param theVariableChoice The new description of the last choice.
     * @param theVariableChoiceIsIncluded Whether this choice should be
     *                                    included.
     *
     * @return The index of the player's choice.
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
     * out of the options that are not excluded.
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
     * @param theAllIncluded Whether all options are included.
     * @param theIncludedOptions Which options should be included.
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
     * Prompts for, reads, and checks the selected option choice from the
     * player until a valid option is selected.
     *
     * @param theAllIncluded Whether all options are included.
     * @param theIncludedOptions Which options are included.
     *
     * @return The selection choice input by the player.
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
     * Reads a menu option from the player until a valid option is entered.
     *
     * @param theAllIncluded Whether all options are included.
     * @param theIncludedOptions Which options are included.
     *
     * @return The selection choice input by the player.
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
     * Checks whether the specified index is included in the menu.
     *
     * @param theAllIncluded Whether all options are included.
     * @param theIncludedOptions Which options are included.
     * @param theIndex The index representing an option.
     *
     * @return Whether the option at the specified index should be included.
     */
    private boolean isIncluded(final boolean theAllIncluded,
                               final boolean[] theIncludedOptions,
                               final int theIndex) {
        return theAllIncluded || theIncludedOptions[theIndex];
    }

    /**
     * Converts an array of excluded options to an array with booleans
     * indicating whether to include each option.
     *
     * @param theExcludedOptions The indices of the excluded options.
     *
     * @return An array to immediately indicate whether an option is included
     *         from its index.
     */
    private boolean[] includedOptions(final int[] theExcludedOptions) {
        final boolean[] included = new boolean[myMenuOptions.length];
        for (int i = 0; i < myMenuOptions.length; i++) {
            included[i] = isIncluded(theExcludedOptions, i);
        }

        return included;
    }

    /**
     * Initially checks whether the current option is to be excluded.
     *
     * @param theExcludedOptions The excluded options.
     * @param theOption The index of the option to be checked.
     *
     * @return Whether the option should be included in the menu.
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
