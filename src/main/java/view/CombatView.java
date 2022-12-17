/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import controller.Controller;

/**
 * This class represents the game when the Adventurer is in a room with a
 * Monster and accepts and executes the player's choices in this context.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class CombatView {

    /**
     * String displaying the monster's turn.
     */
    private static final String MONSTER_TURN = "Monster's turn:\n";

    // "Skill" should always be replaced by skill name
    /**
     * Setting up a Combat Menu panel for in combat-use only.
     * The Combat Menu includes the menu descriptions
     * and the menu key options associated with that descriptions.
     */
    private static final Menu COMBAT_MENU = new Menu(
            "Choose an action",
            new String[]{
                    "Attack", "Open Inventory",
                    "Flee",
                    "Open Play Guide",
                    "Skill"
            },
            new String[]{
                    "A", "I",
                    "F",
                    "P",
                    "S"
            },
            false,
            true,
            true
    );

    /**
     * Sets up Secret Menu panel for combat-use.
     * The menu will serve as the cheat option for player.
     */
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Kill monster"
            },
            true,
            false,
            true
    );

    /**
     * Displays the Combat Menu, gets and performs action for the
     * selected menu option chosen by the player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     * @return The menu signal in the combat menu chosen by the player.
     */
    static MenuSignal open(final Controller theController) {
        MenuSignal internalSignal = MenuSignal.PREVIOUS;

        while (internalSignal == MenuSignal.PREVIOUS) {
            internalSignal = playMonsterTurns(theController);
            if (internalSignal == MenuSignal.PREVIOUS) {
                printCombatants(theController);

                switch (COMBAT_MENU.select(
                            theController.getSpecialSkill(), true
                        )) {
                    case 0 -> System.out.println(theController.attack());
                    case 1 -> internalSignal = MenuSignal.INVENTORY;
                    case 2 -> internalSignal = MoveInternalView.open(
                            theController, theController::flee, true
                    );
                    case 3 -> internalSignal = MenuSignal.PLAY_GUIDE;
                    case 4 -> System.out.println(
                            theController.useSpecialSkill()
                    );
                    case Menu.SECRET -> openSecretMenu(theController);
                }

                if (internalSignal == MenuSignal.PREVIOUS) {
                    internalSignal = Util.nextMenuFromCombatOrExploration(
                            theController, true
                    );
                }
            }
        }

        return internalSignal;
    }

    /**
     * Plays the monster turn after the player's turn has already finished
     * and displays the statistics result after the monster performed its
     * attack.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates, and to
     *                       return the result of interacting with the game
     *                       to the UI in a format the UI can print.
     * @return The menu signal after the monster performs its attack.
     */
    private static MenuSignal playMonsterTurns(final Controller theController) {
        while (theController.isMonsterTurn()) {
            printCombatants(theController);
            System.out.println(MONSTER_TURN);

            if (InputReader.waitForEnter()) {
                openSecretMenu(theController);

                if (!theController.isInCombat()) {
                    break;
                }
            }

            System.out.println(theController.tryMonsterTurn());
            if (!theController.isAlive()) {
                return MenuSignal.LOSE;
            }
            if (!theController.isInCombat()) {
                return MenuSignal.EXPLORATION;
            }
        }

        return MenuSignal.PREVIOUS;
    }

    /**
     * Displays all the current stats of the combatants during battle.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     */
    private static void printCombatants(final Controller theController) {
        System.out.println(theController.getAdventurer());
        System.out.println(theController.getMonster());
    }

    /**
     * Accesses the secret menu and perform cheat action in combat mode
     * when selected by the player.
     *
     * @param theController  The game controller to call public methods of the
     *                       model in response so the game updates,
     *                       and to return the result of interacting with the
     *                       game to the UI in a format the UI can print.
     */
    private static void openSecretMenu(final Controller theController) {
        if (SECRET_MENU.select() == 0) {
            System.out.println(theController.killMonster());
        }
    }
}
