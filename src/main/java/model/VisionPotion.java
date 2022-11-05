package model;

public class VisionPotion extends MapApplicableItem {

    private static final char REPRESENTATION = 'V';

    VisionPotion(int theCount) {
        super(
                REPRESENTATION,
                ItemType.VISION_POTION,
                true,
                theCount
        );
    }

    @Override
    void applyEffect(final Map theTarget, final RoomCoordinates theCoords) {
        for (int i = theCoords.getX() - 1; i <= theCoords.getX() + 1; i++) {
            for (int j = theCoords.getY() - 1; j <= theCoords.getY() + 1; j++) {
                theTarget.explore(theCoords.getFloor(), i, j);
            }
        }
    }

    @Override
    Item copy() {
        return new VisionPotion(getCount());
    }
}
