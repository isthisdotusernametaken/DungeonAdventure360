package view;

import controller.Controller;
import model.Difficulty;
import model.DungeonAdventure;

import java.util.Arrays;

public class NewGameView {

    private static final Menu CLASS_MENU = new Menu(
            "Choose an Adventurer class",
            DungeonAdventure.getAdventurerClasses(),
            true
    );
    private static final Menu DIFFICULTY_MENU = new Menu(
            "Choose a difficulty",
            Arrays.stream(Difficulty.values())
                  .map(Difficulty::toString)
                  .toArray(String[]::new),
            true
    );

    private static final String GAME_NAME_PROMPT =
            "Enter the name of the new game file";
    private static final String ADVENTURER_NAME_PROMPT =
            "Enter the name of the Adventurer";
    private static final String BACK_PROMPT =
            " (or " + Menu.EXIT_MENU + " to return to the previous screen):";

    static MenuSignal open(final Controller theController) {
        displayFiles(theController);

        final int adventurerClass = CLASS_MENU.select();
        if (Menu.isBack(adventurerClass)) {
            return MenuSignal.PREVIOUS;
        }
        final int difficulty = DIFFICULTY_MENU.select();
        if (Menu.isBack(difficulty)) {
            return MenuSignal.PREVIOUS;
        }
        final String adventurerName = readAdventurerName();
        if (Menu.isBack(adventurerName)) {
            return MenuSignal.PREVIOUS;
        }
        final String gameName = readGameFileName();
        if (Menu.isBack(gameName)) {
            return MenuSignal.PREVIOUS;
        }

        return theController.createGame(
                gameName, adventurerName, adventurerClass, difficulty
        ) ?
                MenuSignal.EXPLORATION :
                MenuSignal.PREVIOUS; // Couldn't create game (might change
                                     // behavior later, but should never be
                                     // encountered anyway)
    }

    private static void displayFiles(final Controller theController) {

    }

    private static String readAdventurerName() {
        System.out.print(ADVENTURER_NAME_PROMPT);
        System.out.println(BACK_PROMPT);

        return InputReader.readNameUntilValid();
    }

    private static String readGameFileName() {
        System.out.print(GAME_NAME_PROMPT);
        System.out.println(BACK_PROMPT);

        return InputReader.readNameUntilValid();
    }
}
