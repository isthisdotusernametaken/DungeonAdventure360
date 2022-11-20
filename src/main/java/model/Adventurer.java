package model;

public class Adventurer extends DungeonCharacter{

    final SpecialSkill[] mySpecialSkills;

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
               final SpecialSkill[] theSpecialSkills) {
          super(theName,
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

          mySpecialSkills = theSpecialSkills;
    }

    @Override
    public String toString() {
        return "";
    }

    final double getBlockChance() {
        return 0;
    }

    final String[] getSpecialSkillNames() {
        return new String[0];
    }

    final boolean useSpecialSkill(final DungeonCharacter theTarget,
                                  final int theIndex) {
        return false;
    }

    final void move(final int theFloors,
                    final int theWidth,
                    final int theHeight) {

    }
}
