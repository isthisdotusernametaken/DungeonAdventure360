package model;

import java.io.Serial;

/**
 * This class represents heal skill of the priest class
 * that will be used by the adventurer.
 */
public class Heal extends SpecialSkill {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -4124759288806986206L;

    /**
     * The integer value representing the cool down duration of
     * the special skill.
     */
    private static final int COOLDOWN = 4;

    /**
     * The double value representing the percentage value that
     * this special skill can heal.
     */
    private static final double HEAL_PERCENT = 0.3;

    /**
     * Constructor to create the healing special skill.
     */
    Heal() {
        super(COOLDOWN);
    }

    /**
     * Executes and applies the effect of the special skill onto the
     * monster.
     *
     * @param theSelf   The dungeon character or the adventurer's character
     *                  to get and apply its unique special skill's effect onto
     *                  the monster.
     * @param theEnemy  The dungeon character or the monster that the special
     *                  skill will be applied on.
     * @return          The string result representing the damage and effect
     *                  process after the skill has been applied or executed.
     */
    @Override
    AttackResultAndAmount apply(final DungeonCharacter theSelf,
                              final DungeonCharacter theEnemy) {
        return new AttackResultAndAmount(
                AttackResult.HEAL,
                theSelf.heal(theSelf.percentOfMaxHP(HEAL_PERCENT))
        );
    }
}
