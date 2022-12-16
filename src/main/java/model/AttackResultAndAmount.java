package model;

import java.util.Arrays;

/**
 *
 */
public class AttackResultAndAmount {

    /**
     *
     */
    private static final AttackResultAndAmount[] NO_AMOUNTS =
            Arrays.stream(AttackResult.values())
                  .map(result -> new AttackResultAndAmount(result, 0))
                  .toArray(AttackResultAndAmount[]::new);


    private final AttackResult myResult;
    private final int myAmount;

    AttackResultAndAmount(final AttackResult theResult,
                          final int theAmount) {
        myResult = theResult;
        myAmount = theAmount;
    }

    static AttackResultAndAmount getNoAmount(final AttackResult theResult) {
        return NO_AMOUNTS[theResult.ordinal()];
    }

    public AttackResult getResult() {
        return myResult;
    }

    public int getAmount() {
        return myAmount;
    }
}
