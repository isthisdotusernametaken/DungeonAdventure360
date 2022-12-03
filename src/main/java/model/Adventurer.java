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
        String text = getName() + ":\n"
                + " HP: " + getHP() + "\n"
                + " Minimum Damage:" + getMinDamage() + "\n"
                + " Maximum Damage: " + getMaxDamage() + "\n"
                + " Hit Chance: " + getHP() + "\n"
                + " Speed: " + getSpeed() + "\n"
                + " Special Skill: " + getSpecialSkill() + "\n"
                + " Debuff Chance: " + getDebuffChance() + "\n"
                + " Debuff Duration: " + getDebuffDuration() + "\n"
                + " Block Chance: " + getBlockChance() + "\n"
                ;
        return text;
    }

    final SpecialSkill getSpecialSkill() {
        return mySpecialSkill;
    }

    final String viewSpecialSkill() {
        return mySpecialSkill.toString();
    }

    final AttackResult useSpecialSkill(final DungeonCharacter theSelf,
                                       final DungeonCharacter theEnemy) {
        return mySpecialSkill.use(theSelf, theEnemy);
    }
}
