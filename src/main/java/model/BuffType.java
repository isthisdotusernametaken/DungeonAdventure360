package model;

import java.util.Arrays;

public enum BuffType implements CharRepresentable {

    NONE(false, ' '), // Representation should not be used
    STRENGTH(false, 'S'),
    SPEED(false, '》'),
    WEAKNESS(true, 'W'),
    BROKEN_BONE(true, '<'),
    BURNING(true, 'F'),
    BLEEDING(true, 'B'),
    POISONED(true, '☠');

    private static final BuffType[] POSITIVE_TYPES =
            (BuffType[]) Arrays.stream(values())
            .filter((type) -> !type.isDebuff()).toArray();

    private final boolean myIsDebuff;
    private final char myRepresentation;

    BuffType(final boolean theIsDebuff, final char theRepresentation) {
        myIsDebuff = theIsDebuff;
        myRepresentation = theRepresentation;
    }

    static BuffType randomPositiveBuffType() {
        return POSITIVE_TYPES[Util.randomIntExc(POSITIVE_TYPES.length)];
    }

    @Override
    public char charRepresentation() {
        return myRepresentation;
    }

    boolean isDebuff() {
        return myIsDebuff;
    }
}


