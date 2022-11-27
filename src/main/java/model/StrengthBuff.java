package model;

public class StrengthBuff extends Buff {

    StrengthBuff(final int theDuration) {
        super(BuffType.STRENGTH, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setMaxDamage(120);
        theStats.setMinDamage(60);
        theStats.setDebuffChance(0.2);
        theStats.setHitChance(Util.clampFraction(0.6));
        theStats.setResistance(DamageType.BLUNT, Util.randomDouble());
        theStats.setResistance(DamageType.POISON, Util.randomDouble());
        theStats.setResistance(DamageType.FIRE, Util.randomDouble());
        theStats.setResistance(DamageType.SHARP, Util.randomDouble());

        while (!isCompleted()) {
            advance();
        }

        theStats.resetStats();
        theStats.resetResistances();

    }
}
