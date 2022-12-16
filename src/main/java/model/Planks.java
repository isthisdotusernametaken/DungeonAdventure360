package model;

import java.io.Serial;

public class Planks extends RoomApplicableItem {

    static final String SUCCESS_MSG = "Trap boarded.\n";

    @Serial
    private static final long serialVersionUID = -4102401273626104641L;

    private static final String NAME = createNameFromType(new Planks(0));
    private static final char REPRESENTATION = '=';

    Planks(final int theCount) {
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
        return new Planks(getCount());
    }

    @Override
    String getName() {
        return NAME;
    }
}
