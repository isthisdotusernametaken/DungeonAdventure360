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

    final boolean use(/*final Room theTarget*/) {
        boolean applied = applyEffect(/*theTarget*/);
        if (applied) {
            consume();
        }

        return applied;
    }

    abstract boolean applyEffect(/*final Room theTarget*/);
}
