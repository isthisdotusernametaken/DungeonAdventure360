package model;

public class Planks extends RoomApplicableItem {

    private static final char REPRESENTATION = '=';

    Planks(int theCount) {
        super(
                REPRESENTATION,
                ItemType.PLANKS,
                true,
                theCount
        );
    }

    @Override
    boolean applyEffect(/*final Room theTarget*/) {
        return true/*theTarget.coverPit()*/;
    }

    @Override
    Item copy() {
        return new VisionPotion(getCount());
    }
}
