package view;

import controller.Controller;

public class CombatView {
    private final String SELECT_SKILL_PROMPT;
    private final Menu COMBAT_ACTIONS_MENU;


    public CombatView(String theSelectSkillPrompt, Menu theCombatActionsMenu) { //Not in the UML
        SELECT_SKILL_PROMPT = theSelectSkillPrompt;
        COMBAT_ACTIONS_MENU = theCombatActionsMenu;
    }

    MenuSignal open(Controller theController) {
        return null;
    }

    private void listSpecialSkills(Controller theController) {

    }

    private int selectSpecialSkills(int theMaxIndex) {
        return 0;
    }

    private void displayAdventurer(Controller theController) {

    }
}
