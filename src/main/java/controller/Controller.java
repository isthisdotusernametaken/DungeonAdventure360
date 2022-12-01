package controller;


import model.AdventurerFactory;
import model.Difficulty;
import model.DungeonAdventure;
import view.ConsoleUI;
import view.UISelection;

public class Controller {

    public static final String NAME_REGEX =
            "(?=.*[\\dA-Za-z(),._-])[ \\dA-Za-z()',._-]{1,30}";

    private DungeonAdventure myGame;
    private final ConsoleUI myUI;

    static {
        AdventurerFactory
    }

    private Controller(final int theUIChoice) {
        myUI = theUIChoice == UISelection.CONSOLE_UI ?
                new ConsoleUI(this) :
                null/*new GUI(this)*/;
    }

    public static void main(String[] args) {
        final int uiChoice = UISelection.select();

        if (
                uiChoice == UISelection.CONSOLE_UI ||
                uiChoice == UISelection.GUI
        ) {
            new Controller(uiChoice).myUI.run();
        } // else, exit
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
