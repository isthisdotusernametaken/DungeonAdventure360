package model;

public class PoisonedDebuff extends Buff {

    PoisonedDebuff(final int theDuration) {
        super(
                BuffType.POISONED,
                "Min Damage, Max Damage, All Resistances",
                0.8,
                0.05,
                theDuration
        );
    }

    @Override
    void adjustStats(AdjustedCharacterStats theStats) {
        theStats.multiplyDamage(getStatMultiplier());
        theStats.multiplyResistances(getStatMultiplier());
    }
}
