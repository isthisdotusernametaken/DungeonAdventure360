package model;

public abstract class Item /*implements CharRepresentable*/ {

    private final boolean myIsConsumable;
    private int myCount;

    Item(final boolean theIsConsumable,
         final int theCount) {
        myIsConsumable = theIsConsumable;
        myCount = theCount;
    }

    final int getCount() {
        return myCount;
    }

    final void consume(final int theCount) {
        if (myIsConsumable) {
            myCount -= theCount;
        }
    }

    final Item takeFromStack(final int theCount) {
        consume(theCount);

        return newStack(theCount);
    }

    abstract Item newStack(final int theCount);
}
