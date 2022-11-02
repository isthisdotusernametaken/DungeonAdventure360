package model;

public abstract class CharacterApplicableItem extends Item {

    CharacterApplicableItem(ItemType theType,
                            boolean theCanChangeCount,
                            int theCount) {
        super(theType, theCanChangeCount, theCount);
    }

    final int use(/*final DungeonCharacter theTarget*/) {
        consume();
        return applyEffect(/*theTarget*/);
    }

    abstract int applyEffect(/*final DungeonCharacter theTarget*/);
}
