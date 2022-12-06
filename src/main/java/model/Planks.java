package model;

public class Planks extends RoomApplicableItem {

    private static final String NAME = createNameFromType(new Planks(0));
    private static final char REPRESENTATION = '=';
    private static final String SUCCESS_MSG = "Trap boarded.";

    Planks(int theCount) {
        super(
                REPRESENTATION,
                ItemType.PLANKS,
                true,
                theCount
        );
    }

    @Override
    String applyEffect(final Room theTarget) {
        return theTarget.boardTrap() ?
               SUCCESS_MSG :
               Util.NONE;
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
