package model;

public class Pillar extends Item {

    final static Pillar ABSTRACTION = new Pillar(ItemType.ABSTRACTION, 'A');
    final static Pillar ENCAPSULATION = new Pillar(ItemType.ENCAPSULATION, 'E');
    final static Pillar INHERITANCE = new Pillar(ItemType.INHERITANCE, 'I');
    final static Pillar POLYMORPHISM = new Pillar(ItemType.POLYMORPHISM, 'P');

    private Pillar(final ItemType theType,
                   final char theCharRepresentation) {
        super(
                theCharRepresentation,
                theType,
                false,
                1
        );
    }

    @Override
    Item copy() {
        return this;
    }
}
