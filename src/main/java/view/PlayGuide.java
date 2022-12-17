/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package view;

import java.util.List;
import java.util.function.BinaryOperator;

import model.DungeonAdventure;
import model.Util;

/**
 * This class displays information about the game and how to play it.
 */
public class PlayGuide {

    /**
     * Overall description of game. Printed when guide is first opened
     */
    private static final String MAIN_GUIDE = """
        Collect all 4 Pillars of OO (Abstraction, Encapsulation, Inheritance,
        and Polymorphism) and reach the exit to win.
        Beware, Traps and Monsters await you within the dungeon, and if your HP
        drops to 0, the dungeon will consume you...
        
        Select a guide to view""";

    /**
     * Guide for interacting with the UI.
     */
    private static final String UI_GUIDE = """
        The player's interaction with the UI involves menus and prompts.
        
        Menus list their options (e.g., N) and descriptions (e.g., North)
        separated by colons (e.g., N: North), and the user can enter the option
        or the description (both are case-insensitive) to make a choice.
        Most menus include a "Back" option to return to the previous menu.
        
        Prompts print out a description of what the user can enter and then
        wait for a longer form response (e.g., printing, "Enter the name of the
        Adventurer," and waiting for the user to enter a name to assign to the
        Adventurer).
        """;

    /**
     * Guide for player characters.
     */
    private static final String ADVENTURER_GUIDE = """
        The player is represented by a character known as an Adventurer.
        
        An Adventurer has an individual name, a class type (e.g., Thief),
        current and maximum HP, and other stats, which are all printed in the
        Exploration and Combat menus while the Adventurer navigates the
        dungeon.
        
        The player must choose an Adventurer to begin a game, and the player
        will keep that Adventurer for the entirety of that game.
        """;

    /**
     * Guide for how to explore the dungeon
     */
    private static final String EXPLORATION_GUIDE = """
        The main view that the player will see is the Exploration menu.
        
        The Exploration menu displays the Adventurer's current stats and the
        room the Adventurer is in. Within the current room, the Exploration
        menu allows the player to view the items in the room, add the items to
        their inventory, open their inventory to view or use their items, move
        to adjacent rooms through doors or stairs, and view a map of the rooms
        that the Adventurer has explored.
        
        The Exploration menu also allows the player to open the saving and
        loading menus or to quit back to the title screen. If the player
        attempts to load another game or quit to the title screen and the game
        has unsaved changes, the player will be notified of this and will have
        the option to save the game before continuing, continue without saving,
        or return to the Exploration menu without loading another game or
        exiting to the menu.
        
        If the room the Adventurer enters has a trap that has not been broken
        or boarded, the Adventurer will automatically trigger the trap. The
        Adventurer may dodge the trap, in which case no damage or debuffs will
        be dealt, otherwise the Adventurer will take damage from the trap and
        possibly gain a debuff that will last the next few moves (whether
        exploring or in combat).
        Some traps (including Poison Dart Traps and Fire Traps) will break
        after the Adventurer triggers them, while others (Pits) can hurt the
        Adventurer any time they enter the Trap's room. A Pit can be boarded
        with planks from the player's inventory while in the Pit's room.
        
        Any debuffs the Adventurer has will advance one turn each time the
        Adventurer moves to a new Room, but buffs will not advance or expire
        while exploring.
        
        The play guide can be opened from the Exploration menu at any time.
        """;

    /**
     * Guide for how to engage in combat
     */
    private static final String COMBAT_GUIDE = """
        When the Adventurer enters a room that contains a Monster, the Combat
        menu will automatically open.
        
        Combat is turn based, and the number and order of turns given to the
        Adventurer and the Monster is based on their relative speeds. For
        example, if the Adventurer's speed is 7 and the Monster's speed is 3,
        the ratio of the speeds is 2 + 1/3, and the Adventurer will go first and
        have 2 turns with a 1/3 chance to have an extra turn, followed by one
        turn for the Monster before this cycle repeats.
        
        If the relative speeds of the Adventurer and Monster are changed at the
        end of the cycle of turns due to buffs or debuffs, the turn
        distribution will be recalculated to reflect this change for the next
        cycle.
        
        During the player's turn the Combat menu allows the player to choose to
        attack the room's Monster (with damage based on the Adventurer's base
        damage and the Monster's resistance to the Adventurer's damage type),
        use the special skill associated with the Adventurer's class (e.g.,
        Sneak Attack for a Thief, Heal for a Priestess), or attempt to flee
        combat to a nearby room on the same floor.
        The player can also open their inventory and use items such as health
        potions and buff potions during their turn, which does not use the
        Adventurer's turn - be careful not to use all your items too fast,
        though, since you may run out when you need them in combat later.
        
        The Monster and Adventurer can inflict debuffs on each other with their
        attacks, affecting each other's stats and regularly dealing minor
        damage for a certain number of turns. Debuffs can be cleared with
        health potions, the Heal skill, or the healing that is occasionally
        randomly applied to the Monster.
        
        Combat continues until the Adventurer successfully flees or the
        Adventurer or Monster dies. When a Monster dies, they drop a random
        item in the room, and the player can choose to pick up this item when
        they are automatically returned to the Exploration menu.
        
        The play guide can be opened from the Combat menu at any time.
        """;

    /**
     * Guide for how to use the inventory
     */
    private static final String INVENTORY_GUIDE = """
        While exploring or in combat, the player can open their inventory to
        view and use their current items.
        
        The items in the player's inventory will be printed in a menu, and the
        player can enter the name of an item or the number next to it to use
        the item.
        
        Items that cannot be used in a certain context are not shown in the
        inventory (e.g., vision potions cannot be used in combat, so will not
        be shown if the player accesses their inventory during combat), except
        for pillars, which are always and only shown in the Exploration menu
        but cannot be used.
        
        The player can add items to their inventory by collecting them from the
        current room.
        """;
    /**
     * Guide for what the symbols in the game mean. Generated lazily from
     * representations in other classes and in DB at runtime.
     */
    private static String REPRESENTATIONS;
    /**
     * Menu for selecting a guide to view
     */
    private static Menu GUIDE_MENU;

    /**
     * Concatenates a String onto another String on the next line with a
     * 2-space indent
     */
    private static final BinaryOperator<String> CONCATENATOR =
            (str1, str2) -> str1 + "\n  " + str2;

    /**
     * Displays the main guide and allows the player to choose another guide to
     * view.
     *
     * @return The signal to return to the previous menu after the guide is
     * closed.
     */
    static MenuSignal open() {
        final Menu guideMenu = getGuideMenu();

        int choice;
        while (true) {
            choice = guideMenu.select();

            if (Menu.isBack(choice)) {
                return MenuSignal.PREVIOUS;
            }

            System.out.println(switch (choice) {
                case 1 -> REPRESENTATIONS;
                case 2 -> ADVENTURER_GUIDE;
                case 3 -> EXPLORATION_GUIDE;
                case 4 -> COMBAT_GUIDE;
                case 5 -> INVENTORY_GUIDE;
                default -> UI_GUIDE;
            });
            InputReader.waitForEnter();
        }
    }

    /**
     * Returns the menu for selecting a guide, creating the menu if it does not
     * exist yet.
     *
     * @return the menu to select a guide to view.
     */
    private static Menu getGuideMenu() {
        if (GUIDE_MENU == null) {
            buildRepresentationsGuide();

            GUIDE_MENU = new Menu(
                    MAIN_GUIDE,
                    new String[]{
                            "UI",
                            "Symbol Meanings",
                            "Adventurer",
                            "Exploration", "Combat",
                            "Inventory"
                    },
                    true,
                    false,
                    true
            );
        }

        return GUIDE_MENU;
    }

    /**
     * Constructs the guide for what the game's symbols mean.
     */
    private static void buildRepresentationsGuide() {
        final List<List<String>> representations =
                DungeonAdventure.getCharRepresentations();

        REPRESENTATIONS =
                "In Room:\n  " + concatenateFormatted(representations.get(0)) +
                "\nMap:\n  " + concatenateFormatted(representations.get(1)) +
                "\nItems:\n  " + concatenateFormatted(representations.get(2)) +
                "\nTraps:\n  " + concatenateFormatted(representations.get(3)) +
                '\n';
    }

    /**
     * Concatenates the provided list of Strings into a single, multi-line
     * String.
     *
     * @param theStrings The String List to format as a String
     * @return A String with all the provides Strings on their own indented
     *         lines.
     */
    private static String concatenateFormatted(final List<String> theStrings) {
        return theStrings.stream().reduce(CONCATENATOR).orElse(Util.NONE);
    }
}
