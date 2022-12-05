package model;

public class Adventurer extends DungeonCharacter {

    private final SpecialSkill mySpecialSkill;

    Adventurer(final String theName,
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
               final ResistanceData theResistances,
               final SpecialSkill theSpecialSkill) {
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

          mySpecialSkill = theSpecialSkill;
    }

    @Override
    public final String toString() {
        return new StringBuilder(super.toString())
                .append(" Special Skill: ").append(mySpecialSkill).append('\n')
                .toString();
    }

    final SpecialSkill getSpecialSkill() {
        return mySpecialSkill;
    }

    final String viewSpecialSkill() {
        return mySpecialSkill.toString();
    }

    final AttackResultAndAmount useSpecialSkill(final DungeonCharacter theSelf,
                                                final DungeonCharacter theEnemy) {
        return mySpecialSkill.use(theSelf, theEnemy);
    }
}
