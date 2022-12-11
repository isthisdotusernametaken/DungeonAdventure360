package model;

import java.io.Serial;
import java.util.Arrays;

public final class Pillar extends Item {

    @Serial
    private static final long serialVersionUID = -1853421696253825055L;

    private static final String[] NAMES =
            Arrays.stream(createPillars())
            .map(Item::createNameFromType)
            .toArray(String[]::new);

    private static final char ABSTRACTION_REPRESENTATION = 'A';
    private static final char ENCAPSULATION_REPRESENTATION = 'E';
    private static final char INHERITANCE_REPRESENTATION = 'I';
    private static final char POLYMORPHISM_REPRESENTATION = 'P';

    private Pillar(final ItemType theType,
                   final char theCharRepresentation) {
        super(
                theCharRepresentation,
                theType,
                false,
                1
        );
    }

    static Pillar[] createPillars() {
        return new Pillar[]{
                new Pillar(ItemType.ABSTRACTION, ABSTRACTION_REPRESENTATION),
                new Pillar(ItemType.ENCAPSULATION, ENCAPSULATION_REPRESENTATION),
                new Pillar(ItemType.INHERITANCE, INHERITANCE_REPRESENTATION),
                new Pillar(ItemType.POLYMORPHISM, POLYMORPHISM_REPRESENTATION)
        };
    }

    @Override
    Item copy() {
        return this;
    }

    @Override
    String getName() {
        return NAMES[getType().ordinal()];
    }
}
