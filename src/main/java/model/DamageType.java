package model;

public enum DamageType {

    NORMAL(BuffType.NONE),
    SHARP(BuffType.BLEEDING),
    BLUNT(BuffType.BROKEN_BONE),
    FIRE(BuffType.BURNING),
    POISON(BuffType.WEAKNESS);


    private final BuffType myDebuffType;

    DamageType(final BuffType theDebuffType) {
       myDebuffType = theDebuffType;
    }

    BuffType getDebuffType() {
        return myDebuffType;
    }
}

