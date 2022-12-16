package model;

import java.io.Serial;

/**
 * This class represents crushing blow special skill of the warrior class
 * that will be used by the adventurer and will be applied on the monster.
 */
public class CrushingBlow extends SpecialSkill {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = 1960099350768448086L;

    /**
     * The integer value representing the cool down duration of
     * the special skill.
     */
    private static final int COOLDOWN = 5;

    /**
     * The integer value representing the minimum special skill
     * damage.
     */
    private static final int MIN_DAMAGE = 80;

    /**
     * The integer value representing the maximum special skill
     * damage.
     */
    private static final int MAX_DAMAGE = 150;

    /**
     * The integer value representing the hit chance of the
     * special skill.
     */
    private static final double HIT_CHANCE = 0.3;

    /**
     * The integer value representing the debuff chance of the
     * special skill.
     */
    private static final double DEBUFF_CHANCE = 1.0;

    /**
     * The integer value representing the debuff duration of the
     * special skill.
     */
    private static final int DEBUFF_DURATION = 4;

    /**
     * Constructor to create the crushing blow special skill.
     */
    CrushingBlow() {
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
        return Util.probabilityTest(HIT_CHANCE) ?
               theEnemy.applyDamageAndBuff(
                       theSelf.getDamageType(),
                       Util.randomIntInc(MIN_DAMAGE, MAX_DAMAGE),
                       DEBUFF_CHANCE,
                       DEBUFF_DURATION,
                       false
               ) :
               AttackResultAndAmount.getNoAmount(AttackResult.MISS);
    }
}
