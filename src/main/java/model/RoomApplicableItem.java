package model;

import java.io.Serial;

public abstract class RoomApplicableItem extends Item {

    @Serial
    private static final long serialVersionUID = -9020731178990496158L;

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

    abstract String applyEffect(Room theTarget);
}
