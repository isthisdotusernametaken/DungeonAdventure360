package controller;

import model.AttackResult;
import model.AttackResultAndAmount;
import model.Difficulty;
import model.Direction;
import model.DungeonAdventure;
import model.Util;
import view.ConsoleUI;

/**
 * This class connects all packages and class together so that the
 * program runs the code.
 */
public final class Controller {

    /**
     * Saving the Game automatically. 
     */
    private static final String AUTOSAVE_FILE = "autosave";

    /**
     * Alert message of error the system could not start.
     */
    private static final String COULD_NOT_START =
            "The application could not start.";

    /**
     * application of information for the failure of starting.
     */
    private static final String FAILURE_DETAILS =
            "For more information, view ";

    /**
     * Alert the player can not move character due to obstacles or walls.
     */
    private static final String COULD_NOT_MOVE =
            "Could not move the Adventurer.";

    /**
     * The UI enables user interface.
     */
    private final ConsoleUI myUI;
    
    /**
     * Player acces to play game.
     */
    private DungeonAdventure myGame;
    
    /**
     * Player choose a previous game.
     */
    private String myPreviousSaveName;
    /**
     *  player's game is saved.
     */
    private boolean myIsSaved;

    /**
     * saving game if terminal is closed.
     */
    private Controller() {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> saveGame(AUTOSAVE_FILE))
        ); // Catch console closing and save game if nonnull

        myUI = new ConsoleUI(this);
    }

    /**
     * Main draws from all files allowing the UI to run and play the game.
     */
    public static void main(final String[] theArgs) {
        try {
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
        } catch (Exception e) {
            // Should never be encountered. Last resort to avoid printing
            // exception to user
            if (ProgramFileManager.tryCreateInstance()) {
                ProgramFileManager.getInstance().logException(e, false);
            }
            System.out.println("Unexpected exit.");
        }
    }

    /**
     * Access to saved data from files 
     *  @return  All saved games previous saved are shown by UI.
     */
    public String[] getSaveFiles() {
        return ProgramFileManager.getInstance().getSaveFiles();
    }

    /**
     * Resets all data about an Adventurer and the save file.
     */
    public void reset() {
        myGame = null;
        myPreviousSaveName = null;
        noteUnsaved();
    }

    /**
     * Creating a new game so the player starts at the beginning.
     *
     *  @return Result will be false setting all stats to normal level.
     */
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

    /**
     * load a game that is already has been saved you want to restart to.
     *
     *  @return result is that the file does not exist yet.
     */
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

    /**
     * a way to see old saved files and there names.
     *
     *  @return result is a files with names of previous games pops up.
     */
    public String getPreviousSaveName() {
        return myPreviousSaveName;
    }

    /**
     * if files has names of previous saved games they would pop up.
     *
     *  @return result is false so nothing exist yet.
     */
    public boolean hasPreviousSaveName() {
        return myPreviousSaveName != null;
    }

    /**
     * a file that is already saved..
     *
     *  @return result is false so nothing exist yet or comes up with the files already saved.
     */
    public boolean isSaved() {
        return myIsSaved || myGame == null;
    }

    /**
     * save the cuurent game for future use.
     *
     *  @return result overwrite old file same name and the file name.
     */
    public boolean saveGame() {
        return hasPreviousSaveName() && saveGame(myPreviousSaveName);
    }

    /**
     * a file that is already saved..
     *
     *  @return result is false so nothing exist yet or comes up with the files already saved.
     */
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

    /**
     *
     */
    public boolean canExit() {
        return myGame.canExit();
    }

    /**
     * player is in battle with monster.
     *
     * @return result in combat with mosnter.
     */
    public boolean isInCombat() {
        return myGame.isInCombat();
    }

    /**
     * it is monsters turn.
     *
     * @return result of the monsters attack.
     */
    public boolean isMonsterTurn() {
        return myGame.isMonsterTurn();
    }

    /**
     *  monsters turn to attack.
     *
     * @return result in message  about th monsters turn.
     * @return result in calculating the damage done by the monster.
     * @return result in monster having no turn.
     */
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

    /**
     * the monster is dead aftr attacking it and it has no more health left.
     *
     * @return result in the monsters showing health and killing it.
     * @return result in a message.
     */
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

    /**
     * shows that the player is still alive after taking damage.
     *
     * @return result in that the player is still alive after taking damage.
     */
    public boolean isAlive() {
        return myGame.isAlive();
    }

    /**
     * the attack of when encounter with a monster.
     *
     * @return result in a message about the attack on player turn.
     * @return result in the damaged by the attack to change with the debuff.
     */
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

    /**
     * attacking with a special skill.
     *
     * @return result in a message of skilled used.
     * @return result in the damaged amount caused by the special skill.
     * @return result in how long you have to wait until you can use again.
     */
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

    /**
     * running from battle with monster.
     *
     * @return result in message of fleeing.
     * @return result in message direction.
     */
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

    /**
     * moving the player around the dungeon.
     *
     * @return result in movement of  direction.
     * @return result in message direction.
     */
    public String moveAdventurer(final Direction theDirection) {
        noteUnsaved();

        try {
            return parseMove(myGame.moveAdventurer(theDirection));
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    /**
     *  the room has stairs inside.
     *
     * @return results in showing the stairs.
     */
    public boolean hasStairs(final boolean theIsUp) {
        return myGame.hasStairs(theIsUp);
    }

    /**
     * string moving the player using stairs.
     *
     * @return The result of moving player to a new floor.
     * @return The result is a message of the direction.
     */
    public String useStairs(final boolean theIsUp) {
        noteUnsaved();

        try {
            return parseMove(myGame.useStairs(theIsUp));
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    /**
     * player choosing an available path, and not a wall.
     *
     * @return result in message invalid direction.
     */
    public boolean isValidDirection(final Direction theDirection) {
        return myGame.isValidDirection(theDirection);
    }

    /**
     * setting unsaved games as not saved.
     */
    private void noteUnsaved() {
        myIsSaved = false;
    }

    /**
     * caclulates the the moves of the player.
     *
     * @return the result of moving.
     */
    private String parseMove(final AttackResultAndAmount[] theResults) {
        return theResults == null ?
               COULD_NOT_MOVE :
               parseBuffDamage(theResults[0], true) +
                       parseTrapDamage(theResults[1]);
    }

    /**
     * calculate the amount of damage a player receives with buff damages.
     *
     * @return The result of how much damage the player receives.
     */
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

    /**
     * traps and calculating the damage.
     *
     * @return The result of how much damage the player receives from traps.
     */
    private String parseTrapDamage(final AttackResultAndAmount theTrapActivation) {
        final String trap = "a " + myGame.getTrap();

        return theTrapActivation.getResult() == AttackResult.DODGE ?
                    myGame.getAdventurerName() + " dodged " + trap + "\n" :
               parseDamage(
                       theTrapActivation, myGame.getTrapDebuffType(), trap,
                       true
               );
    }

    /**
     * calculate the amount of damage a player should receive including misses, blocks.
     *
     * @return The result of how much damage is done to the player.
     */
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

    /**
     * calculate the action of player to receive health boost .
     *
     * @return The result of how much healing the player receives.
     */
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
