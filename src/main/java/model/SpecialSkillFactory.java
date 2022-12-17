package model;

/**
 * This factory class helps to handle, access, and modify the special skill
 *  and let subclasses to decided which class object to instantiate to prevent
 *  duplication of code.
 */
public class SpecialSkillFactory {

    /**
     * Alerts the special skill is invalid.
     */
    private static final String INVALID_SKILL = "Invalid skill: ";

    /**
     * Creates the special skill objects as options for subclass methods to
     * access and use.
     *
     * @param theSkillName              The string representing the name of the
     *                                  special skill.
     * @return                          The special skill object that the
     *                                  subclass called.
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
     * Validates if the special skill is invalid.
     *
     * @param theSkillName The name of the special skill.
     * @return             The illegal argument exception message.
     */
    private static IllegalArgumentException invalidSkillException(final String theSkillName) {
        return new IllegalArgumentException(
                INVALID_SKILL + theSkillName
        );
    }
}
