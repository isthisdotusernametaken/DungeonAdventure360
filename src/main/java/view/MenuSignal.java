package view;

/**
 * This class represents the signals screens can send to indicate which should
 * be opened next.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public enum MenuSignal {

    /**
     * Return to the previous screen
     */
    PREVIOUS,
    /**
     * Open the title screen
     */
    TITLE_SCREEN,
    /**
     * Display the game's guide
     */
    PLAY_GUIDE,
    /**
     * Open the screen to create a new game
     */
    NEW_GAME,
    /**
     * Open the screen to load another game
     */
    LOAD_GAME,
    /**
     * Open the screen to save the current game
     */
    SAVE_GAME,
    /**
     * Open the screen to possibly save the current game and then return to the
     * title screen
     */
    SAVE_AND_QUIT_TO_TITLE,
    /**
     * Open the room screen for exploring the dungeon
     */
    EXPLORATION,
    /**
     * Display the map of the dungeon
     */
    MAP,
    /**
     * Open the screen to view and use inventory items
     */
    INVENTORY,
    /**
     * Open the screen to engage in combat with the current room's Monster
     */
    COMBAT,
    /**
     * Open the screen to display the win message and choose to continue or
     * save and quit
     */
    WIN,
    /**
     * Display the lose message and return to the title screen
     */
    LOSE,
    /**
     * Allow the UI loop to exit, closing the game
     */
    EXIT
}
