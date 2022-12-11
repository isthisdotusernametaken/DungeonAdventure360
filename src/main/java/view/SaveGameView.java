package view;

import controller.Controller;

import java.util.Arrays;

public class SaveGameView {

    private static final String GAME_SAVED =
            "Game saved.\n";

    private static final String SAVE_MOST_RECENT =
            "Save to most recently saved file";
    private static final Menu NEW_OR_OVERWRITE_MENU = new Menu(
            "Choose a saving option",
            new String[]{
                    "Create new file", "Overwrite existing file",
                    SAVE_MOST_RECENT // Should be replaced
            },
            true,
            false
    );

    private static final String ENTER_FILENAME =
            "Enter the name of a nonexistent file to save your file to (or " +
            Menu.EXIT_OPTION + " to return to the previous menu).";
    private static final String CHOOSE_FILE_PROMPT =
            "Select a file to save your game to";
    private static final String FILE_EXISTS =
            "That file already exists.\n";

    static MenuSignal open(final Controller theController) {
        final String[] files = theController.getSaveFiles();
        final Menu selectFileMenu = new Menu(
                CHOOSE_FILE_PROMPT, files, true, false
        );

        int choice;
        while (true) {
            choice = NEW_OR_OVERWRITE_MENU.select(
                    theController.getPreviousSaveName()
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
