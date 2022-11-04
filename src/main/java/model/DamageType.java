package model;

public enum DamageType {

    NORMAL(BuffType.WEAKNESS),
    SHARP(BuffType.WEAKNESS),
    BLUNT(BuffType.WEAKNESS),
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

