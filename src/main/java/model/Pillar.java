package model;

public class Pillar extends Item {

    final static Pillar ABSTRACTION = new Pillar('A');
    final static Pillar ENCAPSULATION = new Pillar('E');
    final static Pillar INHERITANCE = new Pillar('I');
    final static Pillar POLYMORPHISM = new Pillar('P');

    private final char myCharRepresentation;

    private Pillar(final char theCharRepresentation) {
        super(false, 1);

        myCharRepresentation = theCharRepresentation;
    }

    public char charRepresentation() {
        return myCharRepresentation;
    }

    @Override
    Item newStack(int theCount) {
        return this;
    }
}
