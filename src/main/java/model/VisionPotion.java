package model;

public class VisionPotion extends MapApplicableItem {

    private static final String NAME = createNameFromType(new VisionPotion(0));
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
    boolean applyEffect(final Map theTarget, final RoomCoordinates theCoords) {
        boolean anyUnexplored = false;
        for (int i = theCoords.getX() - 1; i <= theCoords.getX() + 1; i++) {
            for (int j = theCoords.getY() - 1; j <= theCoords.getY() + 1; j++) {
                anyUnexplored = anyUnexplored ||
                                theTarget.isExplored(theCoords.getFloor(), i, j);
                theTarget.explore(theCoords.getFloor(), i, j);
            }
        }

        return anyUnexplored;
    }

    @Override
    Item copy() {
        return new VisionPotion(getCount());
    }

    @Override
    String getName() {
        return NAME;
    }
}
