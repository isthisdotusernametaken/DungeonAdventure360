package model;

import java.io.Serial;

/**
 * This class represents sneak attack special skill of the warrior class
 * that will be used by the adventurer and will be applied on the monster.
 */
public class SneakAttack extends SpecialSkill {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -3094056436398460180L;

    /**
     * The integer value representing the cool down duration of
     * the special skill.
     */
    private static final int COOLDOWN = 5;

    /**
     * The double value representing the success chance of the
     * skill's effect to be performed.
     */
    private static final double SUCCESS_CHANCE = 0.4;

    /**
     * The double value representing the success chance of the
     * skill damage to be performed.
     */
    private static final double NORMAL_ATTACK_CHANCE = 0.4;

    /**
     * The double value representing the success chance of the
     * skill damage to be performed combining the success effect chance
     * the normal attack chance.
     */
    private static final double NORMAL_ATTACK_CUTOFF =
            SUCCESS_CHANCE + NORMAL_ATTACK_CHANCE;

    /**
     * Constructor to create the crushing blow special skill.
     */
    SneakAttack() {
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
        final double test = Util.randomDouble();
        if (test <= NORMAL_ATTACK_CUTOFF) {
            final AttackResultAndAmount result =
                    theSelf.attemptDamage(theEnemy, true);

            return test <= SUCCESS_CHANCE ? (
                        result.getResult() == AttackResult.HIT_NO_DEBUFF ?
                            new AttackResultAndAmount(
                                    AttackResult.EXTRA_TURN_NO_DEBUFF,
                                    result.getAmount()
                            ) :
                        result.getResult() == AttackResult.HIT_DEBUFF ?
                            new AttackResultAndAmount(
                                    AttackResult.EXTRA_TURN_DEBUFF,
                                    result.getAmount()
                            ) :
                        result
                   ) :
                   result;
        }
        return AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
