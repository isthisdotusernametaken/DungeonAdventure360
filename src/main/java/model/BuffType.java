package model;

import java.util.Arrays;

public enum BuffType implements CharRepresentable {

    NONE(false, ' '), // Representation should not be used
    STRENGTH(false, 'S'),
    SPEED(false, '》'),
    ACCURACY(false, '➹'),
    RESISTANCE(false, '⌺'),
    BROKEN_BONE(true, '<'),
    BURNING(true, '♨'),
    BLEEDING(true, 'B'),
    POISONED(true, '☠');

    private static final BuffType[] POSITIVE_TYPES =
            Arrays.stream(values()).filter(
                    (type) -> type != NONE && !type.isDebuff()
            ).toArray(BuffType[]::new);

    private final boolean myIsDebuff;
    private final char myRepresentation;
    private final String myName;

    BuffType(final boolean theIsDebuff,
             final char theRepresentation) {
        myIsDebuff = theIsDebuff;
        myRepresentation = theRepresentation;
        myName = Util.createNameFromClassName(this);
    }

    static BuffType[] getAllPositiveBuffTypes() {
        return POSITIVE_TYPES.clone();
    }

    @Override
    public String toString() {
        return myName;
    }

    @Override
    public char charRepresentation() {
        return myRepresentation;
    }

    boolean isDebuff() {
        return myIsDebuff;
    }
}


