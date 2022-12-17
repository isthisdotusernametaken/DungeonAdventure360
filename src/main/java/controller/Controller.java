package controller;

import model.AttackResult;
import model.AttackResultAndAmount;
import model.Difficulty;
import model.Direction;
import model.DungeonAdventure;
import model.Util;
import view.ConsoleUI;

/**
 * This class serves as the entry point for the program and facilitates the
 * interactions between the UI and (through the DungeonAdventure class) the
 * model.
 */
public final class Controller {

    /**
     * The filename of the autosave file
     */
    private static final String AUTOSAVE_FILE = "autosave";

    /**
     * Error message printed when the game cannot find all the resources it
     * needs
     */
    private static final String COULD_NOT_START =
            "The application could not start.";

    /**
     * Prompt for reading the log file for more information
     */
    private static final String FAILURE_DETAILS =
            "For more information, view ";

    /**
     * Prompt that the UI has sent a signal to move the Adventurer at a time
     * when the model's state would not allow this
     */
    private static final String COULD_NOT_MOVE =
            "Could not move the Adventurer.";

    /**
     * The UI for displaying information about the game to the player and
     * accepting the player's input to the game
     */
    private final ConsoleUI myUI;
    
    /**
     * The logic and data of the game
     */
    private DungeonAdventure myGame;
    
    /**
     * The name of the most recently saved/loaded file
     */
    private String myPreviousSaveName;
    /**
     * Whether all changes to the game's state have been saved
     */
    private boolean myIsSaved;

    /**
     * Creates a Controller with a UI and a shutdown hook to attempt to
     * automatically save the game if the application suddenly closes
     */
    private Controller() {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> saveGame(AUTOSAVE_FILE))
        ); // Catch console closing and save game if nonnull

        myUI = new ConsoleUI(this);
    }

    /**
     * Tries to establish the program's directory for saves and the log and
     * tries to read the DB into the game's factories for later object
     * creation.
     * If the game cannot successfully do both of these, the game will print a
     * message indicating that it could not start, attempt to log the error in
     * more detail, and exit without opening the UI.
     * <p>
     * Any completely unexpected exceptions thrown by the view or model at any
     * point will also be caught and logged before forcibly closing the
     * application.
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
     * Retrieves an array of all save files (identified by their extension) in
     * the program's save folder.
     *
     * @return An array of the existing saves.
     */
    public String[] getSaveFiles() {
        return ProgramFileManager.getInstance().getSaveFiles();
    }

    /**
     * Disconnects the current game instance and returns to the state before a
     * game instance was created or loaded.
     */
    public void reset() {
        myGame = null;
        myPreviousSaveName = null;
        noteUnsaved();
    }

    /**
     * Attempts to create a new game with the provided information.
     *
     * @param theAdventurerName The name of the Adventurer in the new game. May
     *                          be empty to indicate a name should be selected
     *                          automatically
     * @param theAdventurerClass The index (in the factory) of the class for
     *                           the new Adventurer
     * @param theDifficulty The difficulty level for the new game. Determines
     *                      dungeon size, spawn rates, and Monster and Trap
     *                      stats
     * @return Whether a new game was successfully created and opened (closing
     *         the previous game)
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
     * Attempts to load a game from the specified file.
     *
     * @param theFile The name of the file to attempt to load
     * @return Whether the file was successfully loaded and the corresponding
     *         game instance was opened
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
     * Retrieves the name of the most recently saved or loaded file.
     *
     * @return The most recent save file's name.
     */
    public String getPreviousSaveName() {
        return myPreviousSaveName;
    }

    /**
     * Indicates whether the current game instance has been saved or loaded by
     * checking whether a most recent save file is known.
     *
     * @return Whether the current game instance has been saved since it was
     *         created.
     */
    public boolean hasPreviousSaveName() {
        return myPreviousSaveName != null;
    }

    /**
     * Indicates whether all the changes to the game's state have been saved.
     *
     * @return Whether all the changes to the game's state have been saved (or
     * no game has been started)
     */
    public boolean isSaved() {
        return myIsSaved || myGame == null;
    }

    /**
     * Attempts to save the current game to the most recent save file.
     *
     * @return Whether the game could be saved.
     */
    public boolean saveGame() {
        return hasPreviousSaveName() && saveGame(myPreviousSaveName);
    }

    /**
     * Attempts to save the current game to the specified save file.
     *
     * @param theFile The file to save the game to
     * @return Whether the game was successfully saved
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

    /**
     * Retrieves a formatted String with information about the player's
     * character.
     *
     * @return the Adventurer's name and other details
     */
    public String getAdventurer() {
        return myGame.getAdventurer();
    }

    /**
     * Retrieves a map displaying the rooms in the dungeon, obscuring rooms
     * the player has not explored yet.
     *
     * @return A formatted String showing the contents of the dungeon
     */
    public String getMap() {
        return myGame.getMap();
    }

    /**
     * Indicates whether unexplored rooms will be hidden on the map.
     *
     * @return Whether unexplored rooms are hidden on the map
     */
    public boolean isUnexploredHidden() {
        return myGame.isUnexploredHidden();
    }

    /**
     * Switches from not displaying unknown rooms on the map to displaying all
     * rooms regardless of whether they have been explored, or vice versa.
     */
    public void toggleIsUnexploredHidden() {
        noteUnsaved();

        myGame.toggleIsUnexploredHidden();
    }

    /**
     * Retrieves a String with details about the room the Adventurer is in
     *
     * @return A String representing the contents of the current room
     */
    public String getRoom() {
        return myGame.getRoom();
    }

    /**
     * Retrieves the items in the player's inventory.
     *
     * @return An array of Strings, each of which represents an item in the
     *         inventory
     */
    public String[] getInventoryItems() {
        return myGame.getInventoryItems();
    }

    /**
     * Adds max stacks of all usable items to the player's inventory
     *
     * @return Any message produce by the model in response to this operation's
     *         success or failure
     */
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

    /**
     * Indicates whether the player can currently see/use the specified item
     *
     * @param theIndex The index in the inventory to check
     * @return Whether the specified item can be seen and/or used
     */
    public boolean canUseInventoryItem(final int theIndex) {
        return myGame.canUseInventoryItem(theIndex);
    }

    /**
     * Uses the specified inventory item on the map, Adventurer, or current
     * room
     *
     * @param theIndex The index in the inventory of the item to use
     * @return The results of using or trying to use the item
     */
    public String useInventoryItem(final int theIndex) {
        noteUnsaved();

        try {
            return myGame.useInventoryItem(theIndex);
        } catch (IllegalStateException e) { // See comment in addMaxItems()
            return e.getMessage();
        }
    }

    /**
     * Indicates whether the current room has any items in it for the player to
     * collect.
     *
     * @return Whether there are items in the current room
     */
    public boolean roomHasItems() {
        return myGame.roomHasItems();
    }

    /**
     * Adds any items in the current room to the player's inventory
     *
     * @return What and how many items were collected
     */
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
     * Indicates whether the player meets all the conditions to win the game,
     * including being at the exit and having all 4 Pillars of OO
     *
     * @return Whether the player can exit the dungeon (win)
     */
    public boolean canExit() {
        return myGame.canExit();
    }

    /**
     * Indicates whether the player is in battle with Monster, which is the
     * case if there is a living Monster in the current room.
     *
     * @return Whether the player is in battle with monster.
     */
    public boolean isInCombat() {
        return myGame.isInCombat();
    }

    /**
     * Indicates whether it is the Monster's turn next in combat.
     *
     * @return Whether the Monster gets to play the next move
     */
    public boolean isMonsterTurn() {
        return myGame.isMonsterTurn();
    }

    /**
     * Plays the Monster's next turn in attacking the Adventurer. This includes
     * the Monster taking damage from any debuffs, possibly healing, and
     * attempting to attack the Adventurer
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
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

    /**
     * Retrieves a formatted String with information about the current room's
     * Monster
     *
     * @return The name and other details of the Monster in the current room
     */
    public String getMonster() {
        return myGame.getMonster();
    }

    /**
     * Immediately kills the Monster and exits combat. Drops still occur
     *
     * @return A message about the Monster's death
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
     * Indicates whether the Adventurer is still alive. This is required for
     * any actions that change the state of the game to occur.
     *
     * @return Whether the Adventurer is alive
     */
    public boolean isAlive() {
        return myGame.isAlive();
    }

    /**
     * Executes the Adventurer's turn by attacking the Monster
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
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

    /**
     * Retrieves a String with details about the Adventurer's combat skill.
     *
     * @return A String representing the Adventurer's skill
     */
    public String getSpecialSkill() {
        return myGame.getSpecialSkill();
    }

    /**
     * Applies the Adventurer's skill to the Adventurer or the Monster,
     * depending on the skill.
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
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
     * Attempts to exit combat by fleeing to another room. May or may not
     * succeed, depending on chance and the Adventurer's and Monster's speeds.
     * Uses the Adventurer's turn.
     *
     * @return The results of what happened to the Monster and Adventurer in
     *         this turn, including health changes and buff details.
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
     * Moves the Adventurer in the specified direction to an adjacent room.
     *
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
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
     * Indicates whether the current room has stairs leading up or down to
     * another floor, depending on the provided boolean.
     *
     * @param theIsUp Whether to check for stairs leading up (false for down)
     * @return Whether valid stairs leading in the specified direction exist
     */
    public boolean hasStairs(final boolean theIsUp) {
        return myGame.hasStairs(theIsUp);
    }

    /**
     * Moves the Adventurer to the room connected to the current room by the
     * specified stairs.
     *
     * @param theIsUp Whether to attempt to use stairs leading up (false for
     *                down)
     * @return The results of what happened to the Adventurer when entering the
     *         room, including Trap activation, health changes, and buff
     *         details.
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
     * Indicates whether the current room has a door leading in the specified
     * direction.
     *
     * @param theDirection The direction to check for a door
     * @return Whether the current room has the specified door
     */
    public boolean isValidDirection(final Direction theDirection) {
        return myGame.isValidDirection(theDirection);
    }

    /**
     * Marks the game as having unsaved changes.
     */
    private void noteUnsaved() {
        myIsSaved = false;
    }

    /**
     * Formats the results of moving the Adventurer into a printable String.
     *
     * @param theResults The unformatted results of moving the Adventurer
     * @return The results of moving the Adventurer as a String.
     */
    private String parseMove(final AttackResultAndAmount[] theResults) {
        return theResults == null ?
               COULD_NOT_MOVE :
               parseBuffDamage(theResults[0], true) +
                       parseTrapDamage(theResults[1]);
    }

    /**
     * Formats the results of advancing the Adventurer's or Monsters buffs into
     * a printable String.
     *
     * @param theBuffDamage The unformatted results of advancing buffs
     * @param theIsOnAdventurer Whether the provided results applied to the
     *                          Adventurer (as opposed to the Monster)
     * @return The results of advancing the Adventurer's or Monster's buffs as
     *         a String
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
     * Formats the results of attempting to activate the room's Trap on the
     * Adventurer into a printable String.
     *
     * @param theTrapActivation The unformatted results of activating the
     *                          room's Trap (if any)
     * @return The results of activating the room's Trap on the Adventurer as a
     *         String.
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
     * Formats the results of an attack on the Adventurer or Monster into a
     * printable String.
     *
     * @param theDamage The unformatted results of an attack
     * @param theDebuffType The type of debuff that may have been applied
     * @param theAttacker The name of the attacker
     * @param theTargetIsAdventurer Whether the Adventurer was the one attacked
     *                              (false for Monster)
     * @return The results of the attack as a String.
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
     * Formats the results of healing the Adventurer or Monster into a
     * printable String.
     *
     * @param theHealResult The unformatted results of healing the Adventurer
     *                      or Monster
     * @param theHealedIsAdventurer Whether the provided results applied to the
     *                              Adventurer (false for Monster)
     * @return The results of healing the Adventurer or Monster as a String
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
