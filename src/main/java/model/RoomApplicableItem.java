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
        final String result = applyEffect(theTarget);
        if (!Util.NONE.equals(result)) {
            consume();
            return result;
        }
        return Item.CANNOT_USE_HERE;
    }

    abstract String applyEffect(final Room theTarget);
}
