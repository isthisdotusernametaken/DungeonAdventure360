package model;

public class Planks extends RoomApplicableItem {

    private static final String NAME = createNameFromType(new Planks(0));
    private static final char REPRESENTATION = '=';
    private static final String SUCCESS_MSG = "Trap boarded";
    private static final String NO_TRAP_MSG = "The room does not contain a trap to board";
    private static final String ALREADY_BOARDED_MSG = "Trap already boarded";

    private String myLastMessage;

    Planks(int theCount) {
        super(
                REPRESENTATION,
                ItemType.PLANKS,
                true,
                theCount
        );
    }

    @Override
    boolean applyEffect(final Room theTarget) {
        if (!theTarget.hasTrap()) {
            myLastMessage = NO_TRAP_MSG;
            return false;
        }
        if (theTarget.boardTrap()) {
            myLastMessage = SUCCESS_MSG;
            return true;
        }
        myLastMessage = ALREADY_BOARDED_MSG;
        return false;
    }

    @Override
    String getResult() {
        return myLastMessage;
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
