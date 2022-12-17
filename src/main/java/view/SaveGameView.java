/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import java.util.Arrays;

import controller.Controller;

/**
 * This class allows the player to save their game as a new file, the most
 * recently saved/loaded existing file, or another existing file.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class SaveGameView {

    /**
     * Alert that the game was saved.
     */
    private static final String GAME_SAVED =
            "Game saved.\n";

    /**
     * Choice for saving to the most recent file.
     */
    private static final String SAVE_MOST_RECENT =
            "Save to most recently saved file";

    /**
     * Menu to choose the file to save to (or to cancel the operation).
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
     * Prompt for player's input for new save file's name.
     */
    private static final String ENTER_FILENAME =
            "Enter the name of a nonexistent file to save your file to (or " +
            Menu.EXIT_OPTION + " to return to the previous menu).";

    /**
     * Menu title for player choosing an existing file to overwrite.
     */
    private static final String CHOOSE_FILE_PROMPT =
            "Select a file to save your game to";

    /**
     * Alert that the specified file for creating a new save already exists.
     */
    private static final String FILE_EXISTS =
            "That file already exists.\n";


    /**
     * Displays the saving menu and attempts to save to a file chosen by the
     * player until the game is successfully saved or the player chooses to
     * cancel the operation.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The signal to return to the previous menu after saving or not
     *         saving the game.
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
     * Attempts to save the game to a new file specified by the player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @param theFiles The existing save files.
     *
     * @return Whether the game was successfully saved.
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
     * Attempts to save the game to an existing file.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @param theFiles The existing save files.
     * @param theMenu The menu to display and select an existing save file.
     *
     * @return Whether the game was saved successfully.
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
