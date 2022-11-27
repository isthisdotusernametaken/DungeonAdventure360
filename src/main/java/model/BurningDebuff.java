package model;

public class BurningDebuff extends Buff {

    BurningDebuff(final int theDuration) {
        super(
                BuffType.BURNING,
                "All Resistances",
                0.8,
                0.06,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
