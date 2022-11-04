package model;

public enum BuffType implements CharRepresentable {

    NONE(false, ' '), // Representation should not be used
    STRENGTH(false, 'S'),
    SPEED(false, '》'),
    WEAKNESS(true, 'W'),
    BROKEN_BONE(true, '<'),
    BURNING(true, 'F'),
    BLEEDING(true, 'B'),
    POISONED(true, '☠');


    private final boolean myIsDebuff;
    private final char myRepresentation;

    BuffType(final boolean theIsDebuff, final char theRepresentation) {
        myIsDebuff = theIsDebuff;
        myRepresentation = theRepresentation;
    }

    @Override
    public char charRepresentation() {
        return myRepresentation;
    }

    boolean isDebuff() {
        return myIsDebuff;
    }
}


