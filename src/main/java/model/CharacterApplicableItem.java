package model;

public abstract class CharacterApplicableItem extends Item {

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

    final String use(final DungeonCharacter theTarget) {
        final String result = applyEffect(theTarget);
        if (!Util.NONE.equals(result)) {
            consume();
        }

        return result;
    }

    abstract String applyEffect(final DungeonCharacter theTarget);
}
