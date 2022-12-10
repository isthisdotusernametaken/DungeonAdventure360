package model;

public class Monster extends DungeonCharacter {

    private static final double HEAL_PERCENT = 0.1;

    private final double myHealChance;

    Monster(final String theName,
            final String theClass,
            final int theMaxHP,
            final int theMinDamage,
            final int theMaxDamage,
            final double theHitChance,
            final double theDebuffChance,
            final int theDebuffDuration,
            final DamageType theDamageType,
            final int theSpeed,
            final double theBlockChance,
            final double theHealChance,
            final ResistanceData theResistances) {
        super(
                theName,
                theClass,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theDamageType,
                theSpeed,
                theBlockChance,
                theResistances
        );

        myHealChance = theHealChance;
    }

    @Override
    public final String toString() {
        return super.toString() +
               " " + Util.asPercent(myHealChance) + " Heal Chance per Turn" +
               '\n';
    }

    double getHealChance() {
        return myHealChance;
    }

    AttackResultAndAmount attemptHeal() {
        return Util.probabilityTest(myHealChance) ?
               new AttackResultAndAmount(
                       AttackResult.HEAL,
                       heal((int) (HEAL_PERCENT * getMaxHP()))
               ) :
               AttackResultAndAmount.getNoAmount(AttackResult.NO_ACTION);
    }
}
