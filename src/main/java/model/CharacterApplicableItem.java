package model;

import java.io.Serial;

public abstract class CharacterApplicableItem extends Item {

    @Serial
    private static final long serialVersionUID = -4722484965977712597L;

    CharacterApplicableItem(final char theRepresentation,
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

    final String use(final DungeonCharacter theTarget)
            throws IllegalArgumentException {
        final String result = applyEffect(theTarget);
        if (!Util.NONE.equals(result)) {
            consume();
            return result;
        }
        return Item.CANNOT_USE_HERE;
    }

    abstract String applyEffect(final DungeonCharacter theTarget);
}
