package controller;

import model.Difficulty;
import model.DungeonAdventure;
import view.ConsoleUI;
import view.UISelection;

import java.io.File;

public class Controller {

    public static final String NAME_REGEX =
            "(?=.*[\\dA-Za-z(),._-])[ \\dA-Za-z()',._-]{1,30}";

    private static final String LOG_DIR_PATH = System.getenv("APPDATA") +
            "\\Dungeon Adventure";
    private static final String LOG_PATH = LOG_DIR_PATH + "\\log.txt";
    private static final String COULD_NOT_START =
            "The application could not start. For more information, view " +
            LOG_PATH + '.';

    private DungeonAdventure myGame;
    private final ConsoleUI myUI;

    private Controller(final int theUIChoice) {
        myUI = theUIChoice == UISelection.CONSOLE_UI ?
                new ConsoleUI(this) :
                null/*new GUI(this)*/;
    }

    public static void main(String[] args) {
        new File(LOG_DIR_PATH).mkdirs();
        if (DungeonAdventure.buildFactories(LOG_PATH)) {
            final int uiChoice = UISelection.select();

            if (
                    uiChoice == UISelection.CONSOLE_UI ||
                            uiChoice == UISelection.GUI
            ) {
                new Controller(uiChoice).myUI.run();
            } // else, exit
        } else {
            System.out.println(COULD_NOT_START);
        }
    }

    public DungeonAdventure getGame() {
        return myGame;
    }

    public String[] getSaveFiles() {
        return null;
    }

    public boolean createGame(final String theGameFileName,
                              final String theAdventurerName,
                              final int theAdventurerClass,
                              final int theDifficulty) {
        if (
                DungeonAdventure.isValidAdventurerClass(theAdventurerClass) &&
                DungeonAdventure.isValidDifficulty(theDifficulty)
        ) {
            myGame = new DungeonAdventure(
                    theAdventurerName,
                    theAdventurerClass,
                    Difficulty.values()[theDifficulty]
            );

            return true;
        }

        return false;
    }

    public boolean loadGame(final String theFile) {
        return false;
    }

    public boolean saveGame(final String theFile) {
        return false;
    }
}
