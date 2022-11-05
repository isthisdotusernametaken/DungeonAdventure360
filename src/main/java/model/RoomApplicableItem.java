package model;

public abstract class RoomApplicableItem extends Item {

    RoomApplicableItem(final char theRepresentation,
                       final ItemType theType,
                       final boolean theCanChangeCount,
                       final int theCount) {
        super(
                theRepresentation,
                theType,
                theCanChangeCount,
                theCount
        );
    }

    final String use(final Room theTarget) {
        if (applyEffect(theTarget)) {
            consume();
        }

        return getResult();
    }

    abstract boolean applyEffect(final Room theTarget);

    abstract String getResult();
}
