package model;

public class BuffPotion extends CharacterApplicableItem {

    private static final int MIN_DURATION = 2;
    private static final int MAX_DURATION = 5;

    private final BuffType myBuffType;

    BuffPotion(final int theCount, final BuffType theBuffType) {
        super(
                theBuffType.charRepresentation(),
                ItemType.BUFF_POTION,
                true,
                theCount
        );

        myBuffType = theBuffType;
    }

    BuffType getBuffType() {
        return myBuffType;
    }

    @Override
    String applyEffect(final DungeonCharacter theTarget) {
        int duration = Util.randomInt(MIN_DURATION, MAX_DURATION);
        theTarget.applyBuff(myBuffType, duration);

        return "";
    }

    @Override
    Item copy() {
        return new BuffPotion(getCount(), myBuffType);
    }
}
