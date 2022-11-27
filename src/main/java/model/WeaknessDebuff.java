package model;

public class WeaknessDebuff extends Buff {

    WeaknessDebuff(final int theDuration) {
        super(BuffType.WEAKNESS, theDuration);
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.setHitChance(0.3);
        theStats.setDebuffChance(Util.randomDouble());
        theStats.setResistance(DamageType.BLUNT, 0.09);
        theStats.setResistance(DamageType.POISON, 0.09);
        theStats.setResistance(DamageType.FIRE, 0.09);
        theStats.setResistance(DamageType.SHARP, 0.09);

        while (!isCompleted()) {
            advance();
        }

        theStats.resetStats();
        theStats.resetResistances();
    }
}
