package model;

public class Monster extends DungeonCharacter {

    private final double myHealChance;

    Monster(final String theName,
            final int theMaxHP,
            final int theMinDamage,
            final int theMaxDamage,
            final double theHitChance,
            final double theDebuffChance,
            final int theDebuffDuration,
            final DamageType theDamageType,
            final int theSpeed,
            final double theBlockChance,
            final double theHealChance/*,
            final ResistanceData theResistances*/) { //Need ResistanceData class
        super(
                theName,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theDamageType,
                theSpeed,
                theBlockChance
        );

        myHealChance = theHealChance;
    }

    double getHealChance() {
        return myHealChance;
    }
}
