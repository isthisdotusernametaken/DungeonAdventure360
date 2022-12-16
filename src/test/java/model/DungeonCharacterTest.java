package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DungeonCharacterTest {
    
    private final DungeonCharacter myCharacter = new Adventurer(
            "Dark LORD",
            "Warrior",
            200,
            25,
            40,
            0.7,
            0.2,
            2,
            DamageType.BLUNT,
            4,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2}),
            new CrushingBlow());

    @Test
    void testToString() {
        String expected = """
                Dark LORD
                 Warrior, 200/200 HP
                 Base Damage: 25-40, Blunt, 70% Accuracy
                 20% Chance to Debuff for 2 Turns
                 Speed: 4, 30% Block Chance
                 Resistances - Normal: 10%, Sharp: 10%, Blunt: 0%, Fire: 20%, Poison: 20%
                 No buffs
                 Special Skill: Crushing Blow
                """;

        assertEquals(expected, myCharacter.toString());
    }

    @Test
    void testGetName() {
        String expected = "Dark LORD";

        assertEquals(expected, myCharacter.getName());
    }

    @Test
    void testGetMaxHP() {
        final int expected = 200;

        assertEquals(expected, myCharacter.getMaxHP());
    }

    @Test
    void testGetHP() {
        final int expected = 200;

        assertEquals(expected, myCharacter.getHP());
    }

    @Test
    void testGetBlockChance() {
        final double expected = 0.3;

        assertEquals(expected, myCharacter.getBlockChance());
    }

    @Test
    void testGetResistanceData() {
        ResistanceData expected = new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2});

        for (int i = 0; i < 5; i++) {
            assertEquals(
                    expected.getResistance(i),
                    myCharacter.getResistances().getResistance(i));
        }
    }

    @Test
    void testGetAdjustedMinDamage() {
        final int expected = 25;

        assertEquals(expected, myCharacter.getMinDamage());
    }

    @Test
    void testGetAdjustedMaxDamage() {
        final int expected = 40;

        assertEquals(expected, myCharacter.getMaxDamage());
    }

    @Test
    void testGetAdjustedHitChance() {
        final double expected = 0.7;

        assertEquals(expected, myCharacter.getHitChance());
    }

    @Test
    void testGetAdjustedSpeed() {
        final int expected = 4;

        assertEquals(expected, myCharacter.getSpeed());
    }

    @Test
    void testGetAdjustedResistance() {
        ResistanceData expected = new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2});

        for (int i = 0; i < 5; i++) {
            assertEquals(
                    expected.getResistance(i),
                    myCharacter.getResistances().getResistance(i));
        }
    }

    @Test
    void testSetName() {
        String expected = "SwishD";
        myCharacter.setName(expected);

        assertEquals(expected, myCharacter.getName());
    }

    @Test
    void testPercentOfMaxHP() {
        final int expected = 20;

        assertEquals(expected, myCharacter.percentOfMaxHP(0.1));
    }

    @Test
    void testHeal() {
        final int expected = 0;

        assertEquals(expected, myCharacter.heal(1000)); //Should be 0, no damage applied
    }

    @Test
    void testApplyDamageAndBuff() {
        final AttackResult expected = AttackResult.KILL;

        assertEquals(expected, myCharacter.applyDamageAndBuff(
                DamageType.BLUNT,
                220,
                0.0,
                0,
                false)
                .getResult());
    }

    @Test
    void testApplyNewBuffMessage() {
        myCharacter.applyBuff(BuffType.STRENGTH, 10);
        String expected = """
                 Buffs:
                  Strength: Min Damage, Max Damage x 1.5 (10 turns)
                """;

        assertEquals(expected, myCharacter.getBuffsAsString());
    }

    @Test
    void testApplyNewBuffStatAdjustment() {
        myCharacter.applyBuff(BuffType.STRENGTH, 10);
        final double expected = 60;

        assertEquals(expected, myCharacter.getAdjustedMaxDamage());
    }


    @Test
    void testApplyExistingBuff() {
        myCharacter.applyBuff(BuffType.STRENGTH, 2);
        myCharacter.applyBuff(BuffType.SPEED, 1);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        String expected = """
                 Buffs:
                  Strength: Min Damage, Max Damage x 1.5 (999 turns)
                  Speed: Speed x 2.0 (1 turns)
                """;

        assertEquals(expected, myCharacter.getBuffsAsString());
    }

    @Test
    void testAdvanceBuffsAndDebuffs_1() {
        myCharacter.advanceBuffsAndDebuffs();

        String expected = """
                 No buffs
                """;

        assertEquals(expected, myCharacter.getBuffsAsString());

    }

    @Test
    void testAdvanceBuffsAndDebuffs_2() {
        myCharacter.applyBuff(BuffType.SPEED, 1);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        myCharacter.advanceBuffsAndDebuffs();

        String stringExpected = """
                 Buffs:
                  Strength: Min Damage, Max Damage x 1.5 (998 turns)
                """;
        final AttackResult expected = AttackResult.NO_ACTION;

        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceBuffsAndDebuffs().getResult());
    }

    @Test
    void testAdvanceBuffsAndDebuffs_3() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        myCharacter.advanceBuffsAndDebuffs();

        String stringExpected = """
                 Buffs:
                  Bleeding: All Resistances x 0.7 (13 turns)
                  Strength: Min Damage, Max Damage x 1.5 (998 turns)
                """;
        final AttackResult expected = AttackResult.BUFF_DAMAGE;

        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceBuffsAndDebuffs().getResult());
    }

    @Test
    void testAdvanceDebuffs_1() {
        myCharacter.advanceBuffsAndDebuffs();

        String expected = """
                 No buffs
                """;

        assertEquals(expected, myCharacter.getBuffsAsString());

    }

    @Test
    void testAdvanceDebuffs_2() {
        myCharacter.applyBuff(BuffType.BLEEDING,1);
        myCharacter.applyBuff(BuffType.BROKEN_BONE,1);
        myCharacter.advanceDebuffs();

        String stringExpected = """
                 No buffs
                """;
        final AttackResult expected = AttackResult.NO_ACTION;

        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceDebuffs().getResult());
    }

    @Test
    void testAdvanceDebuffs_3() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        myCharacter.advanceDebuffs();

        String stringExpected = """
                 Buffs:
                  Bleeding: All Resistances x 0.7 (13 turns)
                  Strength: Min Damage, Max Damage x 1.5 (999 turns)
                """;
        final AttackResult expected = AttackResult.BUFF_DAMAGE;

        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceDebuffs().getResult());
    }

    @Test
    void testAdvanceBuffs_1() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        myCharacter.advanceBuffs(true);

        String stringExpected = """
                 Buffs:
                  Bleeding: All Resistances x 0.7 (13 turns)
                  Strength: Min Damage, Max Damage x 1.5 (998 turns)
                """;
        final AttackResult expected = AttackResult.BUFF_DAMAGE;
        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceDebuffs().getResult());
    }

    @Test
    void testAdvanceBuffs_2() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        myCharacter.advanceBuffs(false);

        String stringExpected = """
                 Buffs:
                  Bleeding: All Resistances x 0.7 (13 turns)
                  Strength: Min Damage, Max Damage x 1.5 (999 turns)
                """;
        final AttackResult expected = AttackResult.BUFF_DAMAGE;
        assertEquals(stringExpected, myCharacter.getBuffsAsString());
        assertEquals(
                expected,
                myCharacter.advanceDebuffs().getResult());
    }

    @Test
    void testReApplyBuffs() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);

        String stringExpected = """
                 Buffs:
                  Bleeding: All Resistances x 0.7 (14 turns)
                  Strength: Min Damage, Max Damage x 1.5 (999 turns)
                """;
        assertEquals(stringExpected, myCharacter.getBuffsAsString());
    }

    @Test
    void testGetBuffs() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);
        Buff expected = new StrengthBuff(999);

        assertEquals(
                expected.getType(),
                myCharacter.getBuff(BuffType.STRENGTH).getType());
    }

    @Test
    void testClearDebuffs() {
        myCharacter.applyBuff(BuffType.BLEEDING,14);
        myCharacter.applyBuff(BuffType.BURNING,14);
        myCharacter.applyBuff(BuffType.BROKEN_BONE,14);
        myCharacter.applyBuff(BuffType.STRENGTH, 999);

        myCharacter.clearDebuffs();

        String expected = """
                 Buffs:
                  Strength: Min Damage, Max Damage x 1.5 (999 turns)
                """;

        assertEquals(expected, myCharacter.getBuffsAsString());
    }

    @Test
    void testApplyDamage_1() {
        final boolean expected = true;

        assertEquals(expected, myCharacter.applyDamage(200));
    }

    @Test
    void testApplyDamage_2() {
        final boolean expected = false;

        assertEquals(expected, myCharacter.applyDamage(20));
    }

    @Test
    void testIsDead() {
        final boolean expected = false;

        assertEquals(expected, myCharacter.isDead());
    }

    @Test
    void testApplyDamageFromBuff() {
        Buff myBuff = new StrengthBuff(2);
        final boolean expected = false;

        assertEquals(
                expected,
                myCharacter.applyDamageFromBuff(myBuff));
    }

    @Test
    void testInverseAdjustedResistance() {
        final double expected = 0.8;

        assertEquals(
                expected,
                myCharacter.inverseAdjustedResistance(DamageType.FIRE));
    }

    @Test
    void testApplyAdjustedDamage() {
        final int expected = (int) (myCharacter.getAdjustedMinDamage() * 0.8);

        assertEquals(
                expected,
                myCharacter.applyAdjustedDamage(25, DamageType.FIRE));
    }

    @Test
    void testAdjustedDebuffChance() {
        final double expected = myCharacter.getDebuffChance() * 0.8;

        assertEquals(
                expected,
                myCharacter.adjustedDebuffChance(0.2, DamageType.FIRE));
    }
}
