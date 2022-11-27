package model;

public class BleedingDebuff extends Buff {

    BleedingDebuff(final int theDuration) {
        super(
                BuffType.BLEEDING,
                "All Resistances",
                0.7,
                0.08,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
