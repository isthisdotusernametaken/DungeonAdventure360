package model;

public class Pillar extends Item {

    final static Pillar ABSTRACTION = new Pillar(ItemType.ABSTRACTION, 'A');
    final static Pillar ENCAPSULATION = new Pillar(ItemType.ENCAPSULATION, 'E');
    final static Pillar INHERITANCE = new Pillar(ItemType.INHERITANCE, 'I');
    final static Pillar POLYMORPHISM = new Pillar(ItemType.POLYMORPHISM, 'P');

    private final char myCharRepresentation;

    private Pillar(final ItemType theType,
                   final char theCharRepresentation) {
        super(theType, false, 1);

        myCharRepresentation = theCharRepresentation;
    }

    public char charRepresentation() {
        return myCharRepresentation;
    }

    @Override
    Item copy() {
        return this;
    }
}
