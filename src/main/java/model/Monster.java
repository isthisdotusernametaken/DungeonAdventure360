package model;

public class Monster extends DungeonCharacter {

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
        String text = getName() + ":\n"
                + " HP: " + getHP() + "\n"
                + " Minimum Damage:" + getMinDamage() + "\n"
                + " Maximum Damage: " + getMaxDamage() + "\n"
                + " Hit Chance: " + getHP() + "\n"
                + " Speed: " + getSpeed() + "\n"
                + " Heal Chance: " + getHealChance() + "\n"
                + " Debuff Chance: " + getDebuffChance() + "\n"
                + " Debuff Duration: " + getDebuffDuration() + "\n"
                + " Block Chance: " + getBlockChance() + "\n"
                ;
        return text;
    }

    double getHealChance() {
        return myHealChance;
    }
}
