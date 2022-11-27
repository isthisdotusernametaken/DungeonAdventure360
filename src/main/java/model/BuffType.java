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

    BuffType(final boolean theIsDebuff, final char theRepresentation) {
        myIsDebuff = theIsDebuff;
        myRepresentation = theRepresentation;
    }

    static int positiveTypeCount() {
        return POSITIVE_TYPES.length;
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


