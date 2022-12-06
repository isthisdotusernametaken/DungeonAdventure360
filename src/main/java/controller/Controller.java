package controller;

import model.AttackResult;
import model.AttackResultAndAmount;
import model.Difficulty;
import model.Direction;
import model.DungeonAdventure;
import model.Util;
import view.ConsoleUI;

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

    private static final String COULD_NOT_MOVE =
            "Could not move the Adventurer.";

    private DungeonAdventure myGame;
    private final ConsoleUI myUI;

    private Controller() {
        myUI = new ConsoleUI(this);
    }

    public static void main(String[] args) {
        new File(LOG_DIR_PATH).mkdirs();
        if (DungeonAdventure.buildFactories(LOG_PATH)) {
            new Controller().myUI.run();
        } else {
            System.out.println(COULD_NOT_START);
        }
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

    public String getAdventurer() {
        return myGame.getAdventurer();
    }

    public String getMap() {
        return myGame.getMap();
    }

    public boolean isUnexploredHidden() {
        return myGame.isUnexploredHidden();
    }

    public void toggleIsUnexploredHidden() {
        myGame.toggleIsUnexploredHidden();
    }

    public String getRoom() {
        return myGame.getRoom();
    }

    public String[] getInventoryItems() {
        return myGame.getInventoryItems();
    }

    public void addMaxItems() {
        myGame.addMaxItems();
    }

    public boolean canUseInventoryItem(final int theIndex) {
        return myGame.canUseInventoryItem(theIndex);
    }

    public String useInventoryItem(final int theIndex) {
        return myGame.useInventoryItem(theIndex);
    }

    public boolean isInCombat() {
        return myGame.isInCombat();
    }

    public String moveAdventurer(final Direction theDirection) {
        return move(myGame.moveAdventurer(theDirection));
    }

    public boolean hasStairs(final boolean theIsUp) {
        return myGame.hasStairs(theIsUp);
    }

    public String useStairs(final boolean theIsUp) {
        return move(myGame.useStairs(theIsUp));
    }

    public boolean isValidDirection(final Direction theDirection) {
        return myGame.isValidDirection(theDirection);
    }

    private String move(final AttackResultAndAmount[] theResults) {
        return theResults == null ?
               COULD_NOT_MOVE :
               parseBuffDamage(theResults[0], true) +
                       parseTrapDamage(theResults[1]);
    }

    private String parseBuffDamage(final AttackResultAndAmount theBuffDamage,
                                   final boolean theIsOnAdventurer) {
        return theBuffDamage.getResult() == AttackResult.NO_ACTION ?
               Util.NONE :
               (theIsOnAdventurer ?
                       myGame.getAdventurerName() :
                       myGame.getMonsterName()
               ) + " took " + theBuffDamage.getAmount() +
                       " damage from debuffs" +
                       (theBuffDamage.getResult() == AttackResult.KILL ?
                            " and died" : ""
                       ) + ".\n";
    }

    private String parseTrapDamage(final AttackResultAndAmount theTrapActivation) {
        final String trap = "a " + myGame.getTrap();

        return theTrapActivation.getResult() == AttackResult.NO_ACTION ?
                    Util.NONE :
               theTrapActivation.getResult() == AttackResult.DODGE ?
                    myGame.getAdventurerName() + " dodged " + trap + "\n" :
               parseDamage(
                       theTrapActivation, myGame.getTrapDebuffType(), trap,
                       true
               );
    }

    private String parseDamage(final AttackResultAndAmount theDamage,
                               final String theDebuffType,
                               final String theAttacker,
                               final boolean theTargetIsAdventurer) {
        final String target = theTargetIsAdventurer ?
                myGame.getAdventurerName() :
                myGame.getMonsterName();

        return theDamage.getResult() == AttackResult.BLOCK ?
               target + " blocked an attack from " + theAttacker + ".\n" :
               target + " took " + theDamage.getAmount() + " damage from " +
                    theAttacker + (
                          theDamage.getResult() == AttackResult.KILL ?
                                 " and died" :
                          theDamage.getResult() == AttackResult.HIT_DEBUFF ?
                                 " and gained a " + theDebuffType + " debuff" :
                          ""
                    ) + ".\n";
    }

    private String parseHeal(final AttackResultAndAmount theHealResult,
                             final boolean theHealedIsAdventurer) {
        return theHealResult.getResult() == AttackResult.NO_ACTION ?
               Util.NONE :
               (theHealedIsAdventurer ?
                       myGame.getAdventurerName() :
                       myGame.getMonsterName()
               ) + " healed " + theHealResult.getAmount() +
                       " hp and cleared all debuffs.";
    }
}
