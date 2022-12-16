package model;

import java.io.Serial;

public abstract class MapApplicableItem extends Item {

    static final String MAP_UPDATED = "Map updated.";

    @Serial
    private static final long serialVersionUID = 3961450036630628818L;

    MapApplicableItem(final char theRepresentation,
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

    final String use(final Map theTarget,
                     final RoomCoordinates theCoords) {
        if (applyEffect(theTarget, theCoords)) {
            consume();
            return MAP_UPDATED;
        }

        return Item.CANNOT_USE_HERE;
    }

    abstract boolean applyEffect(Map theTarget, RoomCoordinates theCoords);
}
