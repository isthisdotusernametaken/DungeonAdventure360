package model;

public class HealthPotion extends CharacterApplicableItem {

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
        int healAmount = Util.randomInt(MIN_HEAL, MAX_HEAL);

        return "" + theTarget.heal(healAmount);
    }

    @Override
    Item copy() {
        return new HealthPotion(getCount());
    }
}
