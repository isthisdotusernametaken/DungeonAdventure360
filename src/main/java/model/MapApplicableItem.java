package model;

public abstract class MapApplicableItem extends Item {

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

    final void use(/*final Map theTarget, */final RoomCoordinates theCoords) {
        consume();
        applyEffect(/*theTarget, */theCoords);
    }

    abstract void applyEffect(/*final Map theTarget, */final RoomCoordinates theCoords);
}
