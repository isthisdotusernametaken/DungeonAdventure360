package model;

public enum BuffType {

    NONE(false),
    STRENGTH(false),
    SPEED(false),
    WEAKNESS(true),
    BROKEN_BONE(true),
    BURNING(true);


    private final boolean myIsDebuff;

    BuffType(final boolean theIsDebuff){
        myIsDebuff = theIsDebuff;
    }

    boolean isDebuff() {
        return myIsDebuff;
    }
}


