/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.io.Serial;

/**
 * This class assigns the character representing of buff type for the item
 * that has buff effect.
 */
public abstract class CharacterApplicableItem extends Item {


    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -4722484965977712597L;

    /**
     * Constructor to construct the item that has buff effect.
     *
     * @param theRepresentation The character representation of buff type
     *                          for the item.
     * @param theType           The type of the item.
     * @param theCanChangeCount The boolean true or false if the item
     *                          can change count.
     * @param theCount          The count of the item.
     */
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

    /**
     * Executes and applies the item with its effect.
     *
     * @param theTarget                 The dungeon character that the
     *                                  item will be applied on.
     * @return                          The result of the effect process
     *                                  after the item has been applied.
     *
     * @throws IllegalArgumentException Thrown if theTarget is null.
     */
    final String use(final DungeonCharacter theTarget)
            throws IllegalArgumentException {
        final String result = applyEffect(theTarget);
        if (!Util.NONE.equals(result)) {
            consume();
            return result;
        }
        return Item.CANNOT_USE_HERE;
    }

    /**
     * Template method applyEffect for the items to
     * apply its effect on dungeon character,
     * the effects is varies from each item.
     *
     * @param theTarget The dungeon character that the buff item
     *                  will be applied on.
     */
    abstract String applyEffect(DungeonCharacter theTarget);
}
