package model;

public abstract class MapApplicableItem extends Item {

    MapApplicableItem(final ItemType theType,
                      final boolean theCanChangeCount,
                      final int theCount) {
        super(theType, theCanChangeCount, theCount);
    }

    final int use(/*final Map theTarget*/) {
        consume();
        return applyEffect(/*theTarget*/);
    }

    abstract int applyEffect(/*final Map theTarget*/);
}
