package controller;

import model.AttackResult;
import model.AttackResultAndAmount;
import model.Difficulty;
import model.Direction;
import model.DungeonAdventure;
import model.Util;
import view.ConsoleUI;

import java.io.File;
import java.util.Arrays;

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

    public boolean roomHasItems() {
        return myGame.roomHasItems();
    }

    public String collectItems() {
        final String[] items = myGame.collectItems();

        if (items.length != 0) {
            final StringBuilder builder = new StringBuilder("Gained items:")
                    .append('\n');

            for (String item : items) {
                builder.append(' ').append(item).append('\n');
            }

            return builder.toString();
        }
        return Util.NONE;
    }

    public boolean canExit() {
        return myGame.canExit();
    }

    public boolean isInCombat() {
        return myGame.isInCombat();
    }

    public boolean isMonsterTurn() {
        return myGame.isMonsterTurn();
    }

    public String tryMonsterTurn() {
        final AttackResultAndAmount[] results = myGame.tryMonsterTurn();

        if (results != null) {
            final String buffDamage = parseBuffDamage(results[0], false);

            return results[0].getResult() == AttackResult.KILL ?
                   buffDamage :
                   buffDamage + parseHeal(results[1], false) +
                           parseDamage(
                                   results[2],
                                   myGame.getMonsterDebuffType(),
                                   myGame.getMonsterName(),
                                   true
                           );
        }
        return Util.NONE;
    }

    public String getMonster() {
        return myGame.getMonster();
    }

    public String killMonster() {
        return parseDamage(
                myGame.killMonster(),
                Util.NONE, // Not used
                myGame.getAdventurerName(),
                false
        );
    }

    public boolean isAlive() {
        return myGame.isAlive();
    }

    public String attack() {
        final AttackResultAndAmount[] results = myGame.attack();

        return parseBuffDamage(results[0], true) +
               parseDamage(
                       results[1],
                       myGame.getAdventurerDebuffType(),
                       myGame.getAdventurerName(),
                       false
               );
    }

    public String getSpecialSkill() {
        return myGame.getSpecialSkill();
    }

    public String useSpecialSkill() {
        if (myGame.canUseSpecialSkill()) {
            final String skillUsed = myGame.getAdventurerName() + " used " +
                    myGame.getSpecialSkill() + ".\n";
            final AttackResultAndAmount[] results = myGame.useSpecialSkill();

            return parseBuffDamage(results[0], true) + skillUsed + (
                        results[1].getResult() == AttackResult.HEAL ?
                                parseHeal(results[1], true) :
                        parseDamage(
                                results[1],
                                myGame.getAdventurerDebuffType(),
                                myGame.getAdventurerName(),
                                false
                        ) + (results[1].getResult() == AttackResult.EXTRA_TURN_NO_DEBUFF ||
                             results[1].getResult() == AttackResult.EXTRA_TURN_DEBUFF ?
                                "Bonus turn!\n" : ""
                        )
                   );
        }
        return "Skill in cooldown.\n";
    }

    public String flee(final Direction theDirection) {
        final AttackResultAndAmount[] results = myGame.flee(theDirection);

        return results[0].getResult() + "\n" + (
                    results[0].getResult() == AttackResult.FLED_SUCCESSFULLY ? (
                            parseMove(new AttackResultAndAmount[]{
                                    results[1], results[2]
                            })
                    ) :
                    parseBuffDamage(results[1], true)
               );
    }

    public String moveAdventurer(final Direction theDirection) {
        return parseMove(myGame.moveAdventurer(theDirection));
    }

    public boolean hasStairs(final boolean theIsUp) {
        return myGame.hasStairs(theIsUp);
    }

    public String useStairs(final boolean theIsUp) {
        return parseMove(myGame.useStairs(theIsUp));
    }

    public boolean isValidDirection(final Direction theDirection) {
        return myGame.isValidDirection(theDirection);
    }

    private String parseMove(final AttackResultAndAmount[] theResults) {
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

        return theTrapActivation.getResult() == AttackResult.DODGE ?
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

        return theDamage.getResult() == AttackResult.NO_ACTION ?
                    Util.NONE :
               theDamage.getResult() == AttackResult.MISS ?
                    theAttacker + " missed.\n" :
               theDamage.getResult() == AttackResult.BLOCK ?
                    target + " blocked an attack from " + theAttacker + ".\n" :
               target + " took " + theDamage.getAmount() + " damage from " +
                    theAttacker + (
                          theDamage.getResult() == AttackResult.KILL ?
                                 " and died" :
                          theDamage.getResult() == AttackResult.HIT_DEBUFF ||
                          theDamage.getResult() == AttackResult.EXTRA_TURN_DEBUFF ?
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
                       " hp and cleared all debuffs.\n";
    }
}
