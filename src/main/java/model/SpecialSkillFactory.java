package model;

import java.io.Serializable;

public class SpecialSkillFactory implements Serializable {

    private static final String INVALID_SKILL = "Invalid skill: ";

    static SpecialSkill createSpecialSkill(final String theSkillName)
            throws IllegalArgumentException {
        return switch (theSkillName.trim()) {
            case "CrushingBlow" -> new CrushingBlow();
            case "SneakAttack" -> new SneakAttack();
            case "Heal" -> new Heal();
            default -> throw invalidSkillException(theSkillName);
        };
    }

    private static IllegalArgumentException invalidSkillException(final String theSkillName) {
        return new IllegalArgumentException(
                INVALID_SKILL + theSkillName
        );
    }
}
