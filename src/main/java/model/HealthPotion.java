package model;

public class HealthPotion extends CharacterApplicableItem {

    private static final String NAME = createNameFromType(
            new HealthPotion(0)
    );
    private static final char REPRESENTATION = 'H';

    private static final int MIN_HEAL = 15;
    private static final int MAX_HEAL = 30;

    HealthPotion(final int theCount) {
        super(
                REPRESENTATION,
                ItemType.HEALTH_POTION,
                true,
                theCount
        );
    }

    @Override
    String applyEffect(final DungeonCharacter theTarget) {
        final int amountHealed = theTarget.heal(
                Util.randomIntInc(MIN_HEAL, MAX_HEAL)
        );

        return amountHealed == 0 ? Util.NONE : "" + amountHealed;
    }

    @Override
    Item copy() {
        return new HealthPotion(getCount());
    }

    @Override
    String getName() {
        return NAME;
    }
}
