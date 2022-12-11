package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Item implements CharRepresentable, Serializable {

    static final int MAX_STACK_SIZE = 999;

    static final String CANNOT_USE_HERE = "This item cannot be used here.\n";

    @Serial
    private static final long serialVersionUID = -3510320815857469564L;

    private final char myRepresentation;
    private final ItemType myType;
    private final boolean myCanChangeCount;
    private int myCount;

    Item(final char theRepresentation,
         final ItemType theType,
         final boolean theCanChangeCount,
         final int theCount) {
        myRepresentation = theRepresentation;
        myType = theType;
        myCanChangeCount = theCanChangeCount;
        myCount = theCount;
    }

    static String createNameFromType(final Item theItem) {
        return Util.createNameFromEnumName(theItem.myType);
    }

    @Override
    public final String toString() {
        return getName() + ": " + myCount;
    }

    public final char charRepresentation() {
        return myRepresentation;
    }

    final ItemType getType() {
        return myType;
    }

    final int getCount() {
        return myCount;
    }

    final void addToStack(final int theCount) {
        if (myCanChangeCount) {
            myCount = Util.addAndClampInt(
                    0, MAX_STACK_SIZE,
                    myCount, theCount
            );
        }
    }

    final void consume() {
        if (myCanChangeCount) {
            myCount--;
        }
    }

    final boolean isSameType(final Item theOther) {
        return myType == theOther.myType &&
               (
                       myType != ItemType.BUFF_POTION ||
                       ((BuffPotion) this).getBuffType() ==
                               ((BuffPotion) theOther).getBuffType()
               );
    }

    abstract Item copy();

    abstract String getName();
}
