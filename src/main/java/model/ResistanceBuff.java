package model;

public class ResistanceBuff extends Buff {

    ResistanceBuff(final int theDuration) {
        super(
                BuffType.RESISTANCE,
                "All Resistances",
                2.5,
                0.0,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
