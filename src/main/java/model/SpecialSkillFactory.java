/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This factory produces SpecialSkill objects from their Class names.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class SpecialSkillFactory {

    /**
     * Error message for nonexistent skill specified.
     */
    private static final String INVALID_SKILL = "Invalid skill: ";

    /**
     * Creates a SpecialSkill with of the specified Class
     *
     * @param theSkillName The name of the class
     * @return A SpecialSkill of the specified class.
     *
     * @throws IllegalArgumentException Indicates an invalid skill name was
     *                                  provided
     */
    static SpecialSkill createSpecialSkill(final String theSkillName)
            throws IllegalArgumentException {
        return switch (theSkillName.trim()) {
            case "CrushingBlow" -> new CrushingBlow();
            case "SneakAttack" -> new SneakAttack();
            case "Heal" -> new Heal();
            default -> throw invalidSkillException(theSkillName);
        };
    }

    /**
     * Exception to indicate the specified skill does not exist.
     *
     * @param theSkillName The name of the nonexistent skill.
     * @return The exception specifying the failure, to be thrown in the caller
     */
    private static IllegalArgumentException invalidSkillException(final String theSkillName) {
        return new IllegalArgumentException(
                INVALID_SKILL + theSkillName
        );
    }
}
