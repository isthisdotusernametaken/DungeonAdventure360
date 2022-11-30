package model;

import java.io.Serializable;

public abstract class Item implements CharRepresentable, Serializable {

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

    @Override
    public String toString() {
        return new StringBuilder()
               .append(getName())
               .append(": ")
               .append(myCount)
               .toString();
    }

    public char charRepresentation() {
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
            myCount += theCount;
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

    static String createNameFromType(final Item theItem) {
        final StringBuilder builder = new StringBuilder();
        for (String word : theItem.myType.toString().split("_")) {
            builder.append(word.charAt(0))
                   .append(word.substring(1).toLowerCase())
                   .append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
