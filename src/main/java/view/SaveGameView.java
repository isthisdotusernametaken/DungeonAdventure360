package view;

import controller.Controller;

import java.util.Arrays;

public class SaveGameView {

    /**
     * String description to alert the game is saved.
     */
    private static final String GAME_SAVED =
            "Game saved.\n";

    /**
     * String description to alert the game is saved to most recently saved file.
     */
    private static final String SAVE_MOST_RECENT =
            "Save to most recently saved file";

    /**
     * Sets up a New or Overwrite menu.
     */
    private static final Menu NEW_OR_OVERWRITE_MENU = new Menu(
            "Choose a saving option",
            new String[]{
                    "Create new file", "Overwrite existing file",
                    SAVE_MOST_RECENT // Should be replaced
            },
            true,
            false,
            true
    );

    /**
     * String format template to prompt for player's input for save file's name.
     */
    private static final String ENTER_FILENAME =
            "Enter the name of a nonexistent file to save your file to (or " +
            Menu.EXIT_OPTION + " to return to the previous menu).";

    /**
     * String format template to prompt for player's input for save file's name.
     */
    private static final String CHOOSE_FILE_PROMPT =
            "Select a file to save your game to";

    /**
     * String description to alert the saved file game is already existed.
     */
    private static final String FILE_EXISTS =
            "That file already exists.\n";


    /**
     * Displays the Save Game Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal in the save game menu.
     */
    static MenuSignal open(final Controller theController) {
        final String[] files = theController.getSaveFiles();
        final Menu selectFileMenu = new Menu(
                CHOOSE_FILE_PROMPT, files, true, false, true
        );

        int choice;
        while (true) {
            choice = NEW_OR_OVERWRITE_MENU.select(
                    SAVE_MOST_RECENT + " (" +
                    theController.getPreviousSaveName() + ")",
                    theController.hasPreviousSaveName()
            );

            if (Menu.isBack(choice)) {
                break;
            }
            if (switch (choice) {
                case 0 -> createNew(theController, files);
                case 1 -> overwriteExisting(theController, files, selectFileMenu);
                case 2 -> theController.saveGame();
                default -> false; // Should never be encountered
                }) {
                System.out.println(GAME_SAVED);
                break;
            }
        }

        return MenuSignal.PREVIOUS;
    }

    /**
     * Creates new save file's name
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @param theFiles       The list of the existed saved files.
     *
     * @return The boolean true or false if the file has already existed or exit option is selected
     */
    private static boolean createNew(final Controller theController,
                                     final String[] theFiles) {
        String file;
        while (true) {
            System.out.println(ENTER_FILENAME);
            file = InputReader.readNameUntilValid(false);

            if (Menu.EXIT_OPTION.equalsIgnoreCase(file)) {
                return false;
            }
            if (!Arrays.asList(theFiles).contains(file)) {
                return theController.saveGame(file);
            }

            System.out.println(FILE_EXISTS);
        }
    }

    /**
     * Overwrites the content of the existing saved file.
     *
     * @param theController  The game controller to call public methods of the model in response so the game updates,
     *                       and to return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @param theFiles       The list of the existed saved files.
     * @param theMenu        The menu class to get the player's input selection.
     *
     * @return The boolean true or false if the overwrite process is successfully done.
     */
    private static boolean overwriteExisting(final Controller theController,
                                             final String[] theFiles,
                                             final Menu theMenu) {
        final int choice = theMenu.select();

        if (!Menu.isBack(choice)) {
            return theController.saveGame(theFiles[choice]);
        }
        return false;
    }
}
