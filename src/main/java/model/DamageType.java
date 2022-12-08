package model;

public enum DamageType {

    NORMAL(BuffType.NONE),
    SHARP(BuffType.BLEEDING),
    BLUNT(BuffType.BROKEN_BONE),
    FIRE(BuffType.BURNING),
    POISON(BuffType.POISONED);


    private final BuffType myDebuffType;
    private String myName;

    DamageType(final BuffType theDebuffType) {
       myDebuffType = theDebuffType;
    }

    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }

    BuffType getDebuffType() {
        return myDebuffType;
    }
}

