package controller;

import model.AttackResult;
import model.AttackResultAndAmount;
import model.Difficulty;
import model.Direction;
import model.DungeonAdventure;
import model.Util;
import view.ConsoleUI;

public final class Controller {

    private static final String AUTOSAVE_FILE = "autosave";

    private static final String COULD_NOT_START =
            "The application could not start.";
    private static final String FAILURE_DETAILS =
            "For more information, view ";

    private static final String COULD_NOT_MOVE =
            "Could not move the Adventurer.";

    private final ConsoleUI myUI;
    private DungeonAdventure myGame;
    private String myPreviousSaveName;
    private boolean myIsSaved;

    private Controller() {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> saveGame(AUTOSAVE_FILE))
        ); // Catch console closing and save game if nonnull

        myUI = new ConsoleUI(this);
    }

    public static void main(final String[] theArgs) {
        if (ProgramFileManager.tryCreateInstance()) {
            if (DungeonAdventure.buildFactories()) {
                new Controller().myUI.run();
            } else {
                System.out.println(COULD_NOT_START);

                System.out.print(FAILURE_DETAILS);
                System.out.println(
                        ProgramFileManager.getInstance().getLogPath()
                );
            }
        } else {
            System.out.println(COULD_NOT_START);
        }
    }

    public String[] getSaveFiles() {
        return ProgramFileManager.getInstance().getSaveFiles();
    }

    public void reset() {
        myGame = null;
        myPreviousSaveName = null;
        noteUnsaved();
    }

    public boolean createGame(final String theAdventurerName,
                              final int theAdventurerClass,
                              final int theDifficulty) {
        if (DungeonAdventure.isValidAdventurerClass(theAdventurerClass) &&
                DungeonAdventure.isValidDifficulty(theDifficulty)) {
            reset();
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
        final DungeonAdventure loaded =
                ProgramFileManager.getInstance().loadGame(theFile);

        if (loaded != null) {
            myGame = loaded;
            myPreviousSaveName = theFile;
            myIsSaved = true;
            return true;
        }
        return false;
    }

    public String getPreviousSaveName() {
        return myPreviousSaveName;
    }

    public boolean hasPreviousSaveName() {
        return myPreviousSaveName != null;
    }

    public boolean isSaved() {
        return myIsSaved || myGame == null;
    }

    public boolean saveGame() {
        return hasPreviousSaveName() && saveGame(myPreviousSaveName);
    }

    public boolean saveGame(final String theFile) {
        if (myGame != null &&
                ProgramFileManager.getInstance().saveGame(theFile, myGame)) {
            myPreviousSaveName = theFile;
            myIsSaved = true;

            return true;
        }

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
        noteUnsaved();

        myGame.toggleIsUnexploredHidden();
    }

    public String getRoom() {
        return myGame.getRoom();
    }

    public String[] getInventoryItems() {
        return myGame.getInventoryItems();
    }

    public String addMaxItems() {
        noteUnsaved();

        try {
            myGame.addMaxItems();
            return Util.NONE;
        } catch (IllegalStateException e) {
            // View is advised to use isAlive() to detect whether an operation
            // can be performed rather than relying on this return.
            // Already logged, and no illegal operation performed, so no
            // further handling required
            return e.getMessage();
        }
    }

    public boolean canUseInventoryItem(final int theIndex) {
        return myGame.canUseInventoryItem(theIndex);
    }

    public String useInventoryItem(final int theIndex) {
        noteUnsaved();

        try {
            return myGame.useInventoryItem(theIndex);
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    public boolean roomHasItems() {
        return myGame.roomHasItems();
    }

    public String collectItems() {
        noteUnsaved();

        final String[] items;
        try {
            items = myGame.collectItems();
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }

        if (items.length != 0) {
            final StringBuilder builder =
                    new StringBuilder("Gained items:").append('\n');

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
        noteUnsaved();

        final AttackResultAndAmount[] results;
        try {
            results = myGame.tryMonsterTurn();
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }

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
        noteUnsaved();

        try {
            return parseDamage(
                    myGame.killMonster(),
                    Util.NONE, // Not used
                    myGame.getAdventurerName(),
                    false
            );
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    public boolean isAlive() {
        return myGame.isAlive();
    }

    public String attack() {
        noteUnsaved();

        final AttackResultAndAmount[] results;
        try {
            results = myGame.attack();
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }

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
        noteUnsaved();

        if (myGame.canUseSpecialSkill()) {
            final String skillUsed = myGame.getAdventurerName() + " used " +
                    myGame.getSpecialSkill() + ".\n";
            final AttackResultAndAmount[] results;
            try {
                results = myGame.useSpecialSkill();
            } catch (IllegalStateException e) { // See comment in addMaxItems()
                return e.getMessage();
            }

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
        noteUnsaved();

        final AttackResultAndAmount[] results;
        try {
            results = myGame.flee(theDirection);
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }

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
        noteUnsaved();

        try {
            return parseMove(myGame.moveAdventurer(theDirection));
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    public boolean hasStairs(final boolean theIsUp) {
        return myGame.hasStairs(theIsUp);
    }

    public String useStairs(final boolean theIsUp) {
        noteUnsaved();

        try {
            return parseMove(myGame.useStairs(theIsUp));
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    public boolean isValidDirection(final Direction theDirection) {
        return myGame.isValidDirection(theDirection);
    }

    private void noteUnsaved() {
        myIsSaved = false;
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
