package model;

import java.util.Arrays;
import java.util.function.Function;

public class Adventurer extends DungeonCharacter {

    private static final Function<SpecialSkill, String> TO_STRINGS =
            Object::toString;

    private final SpecialSkill[] mySpecialSkills;

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
               final SpecialSkill ... theSpecialSkills) {
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

          mySpecialSkills = theSpecialSkills.clone();
    }

    @Override
    public String toString() {
        return "";
    }

    final String[] getSpecialSkillNames() {
        return (String[]) Arrays.stream(mySpecialSkills)
                                .map(TO_STRINGS)
                                .toArray();
    }

    final AttackResult useSpecialSkill(final DungeonCharacter theTarget,
                                       final int theIndex) {
        return mySpecialSkills[theIndex].use(theTarget);
    }
}
