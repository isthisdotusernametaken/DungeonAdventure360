package model;

public abstract class RoomApplicableItem extends Item {

    RoomApplicableItem(final ItemType theType,
                       final boolean theCanChangeCount,
                       final int theCount) {
        super(theType, theCanChangeCount, theCount);
    }

    final int use(/*final Room theTarget*/) {
        consume();
        return applyEffect(/*theTarget*/);
    }

    abstract int applyEffect(/*final Room theTarget*/);
}
