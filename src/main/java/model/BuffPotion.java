package model;

import java.util.Arrays;

public class BuffPotion extends CharacterApplicableItem {

    private static final String[] NAMES =
            Arrays.stream(BuffType.getAllPositiveBuffTypes())
            .map(buffType -> buffType.toString() + " Potion")
            .toArray(String[]::new);

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
        final int duration = Util.randomIntInc(MIN_DURATION, MAX_DURATION);
        theTarget.applyBuff(myBuffType, duration);

        return myBuffType + " +" + duration + " turns";
    }

    @Override
    Item copy() {
        return new BuffPotion(getCount(), myBuffType);
    }

    @Override
    String getName() {
        return NAMES[myBuffType.ordinal() - 1];
    }
}
