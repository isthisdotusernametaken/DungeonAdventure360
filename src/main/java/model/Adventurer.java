package model;

public class Adventurer extends DungeonCharacter {

    private final SpecialSkill mySpecialSkill;

    Adventurer(final String theName,
               final int theMaxHP,
               final int theMinDamage,
               final int theMaxDamage,
               final double theHitChance,
               final double theDebuffChance,
               final int theDebuffDuration,
               final DamageType theDamageType,
               final int theSpeed,
               final double theBlockChance,
               final ResistanceData theResistances,
               final SpecialSkill theSpecialSkill) {
          super(theName,
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

          mySpecialSkill = theSpecialSkill;
    }

    @Override
    public String toString() {
        return "";
    }

    final SpecialSkill getSpecialSkill() {
        return mySpecialSkill;
    }

    final AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        return mySpecialSkill.use(theTarget);
    }
}
