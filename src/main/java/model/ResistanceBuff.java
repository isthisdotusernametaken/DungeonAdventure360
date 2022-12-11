package model;

import java.io.Serial;

public class ResistanceBuff extends Buff {

    @Serial
    private static final long serialVersionUID = -7682020618949096957L;

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
    void adjustStats(final AdjustedCharacterStats theStats) {
        theStats.multiplyResistances(getStatMultiplier());
    }
}
