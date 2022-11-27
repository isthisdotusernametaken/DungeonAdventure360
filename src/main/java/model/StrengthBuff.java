package model;

public class StrengthBuff extends Buff {

    StrengthBuff(final int theDuration) {
        super(
                BuffType.STRENGTH,
                "Min Damage, Max Damage",
                1.5,
                0.0,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
    }
}
