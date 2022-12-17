package model;

import java.util.Arrays;

/**
 * This class represents the attack result and amount from the actions
 * performed or occurred both during combat mode and exploration mode.
 */
public class AttackResultAndAmount {

    /**
     * The list of attack results with zero attack amount.
     */
    private static final AttackResultAndAmount[] NO_AMOUNTS =
            Arrays.stream(AttackResult.values())
                  .map(result -> new AttackResultAndAmount(result, 0))
                  .toArray(AttackResultAndAmount[]::new);


    /**
     * The type of attack result.
     */
    private final AttackResult myResult;

    /**
     * The amount of attack.
     */
    private final int myAmount;

    /**
     * Constructor to construct the attack result with amount.
     *
     * @param theResult The type of attack result.
     * @param theAmount The amount of attack.
     */
    AttackResultAndAmount(final AttackResult theResult,
                          final int theAmount) {
        myResult = theResult;
        myAmount = theAmount;
    }

    /**
     * Gets the attack result with no amount.
     *
     * @param theResult The type of attack result.
     * @return          The attack result with no amount.
     */
    static AttackResultAndAmount getNoAmount(final AttackResult theResult) {
        return NO_AMOUNTS[theResult.ordinal()];
    }

    /**
     * Gets the attack result.
     *
     * @return The attack result.
     */
    public AttackResult getResult() {
        return myResult;
    }

    /**
     * Gets the amount of attack.
     *
     * @return  The amount of attack.
     */
    public int getAmount() {
        return myAmount;
    }
}
