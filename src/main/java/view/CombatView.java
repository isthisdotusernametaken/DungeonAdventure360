package view;

import controller.Controller;

public class CombatView {

    private static final String MONSTER_TURN = "Monster's turn:\n";

    // "Skill" should always be replaced by skill name
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
    private static final Menu SECRET_MENU = new Menu(
            "Choose a secret option",
            new String[]{
                    "Kill monster"
            },
            true,
            false,
            true
    );

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

    private static void printCombatants(final Controller theController) {
        System.out.println(theController.getAdventurer());
        System.out.println(theController.getMonster());
    }

    private static void openSecretMenu(final Controller theController) {
        if (SECRET_MENU.select() == 0) {
            System.out.println(theController.killMonster());
        }
    }
}
