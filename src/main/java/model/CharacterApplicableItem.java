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

    final int use(/*final DungeonCharacter theTarget*/) {
        consume();
        return applyEffect(/*theTarget*/);
    }

    abstract int applyEffect(/*final DungeonCharacter theTarget*/);
}
