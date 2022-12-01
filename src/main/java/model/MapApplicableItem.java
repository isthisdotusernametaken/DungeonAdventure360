package model;

public abstract class MapApplicableItem extends Item {

    private static final String MAP_UPDATED = "Map updated.";

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

        return Util.NONE;
    }

    abstract boolean applyEffect(final Map theTarget,
                                 final RoomCoordinates theCoords);
}
