package model;

import java.io.Serializable;

public abstract class Item implements CharRepresentable, Serializable {

    static final int MAX_STACK_SIZE = 999;

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
    public String toString() {
        return new StringBuilder()
               .append(getName())
               .append(": ")
               .append(myCount)
               .toString();
    }

    public final char charRepresentation() {
        return myRepresentation;
    }

    final ItemType getType() {
        return myType;
    }

    final boolean canChangeCount() {
        return myCanChangeCount;
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
                       ((BuffPotion) this).getBuffType() == ((BuffPotion) theOther).getBuffType()
               );
    }

    abstract Item copy();

    abstract String getName();
}
