package model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static model.AttackResultAndAmount.*;

public class AttackResultAndAmountTest {

    @Test
    void testConstructor() {
        final AttackResultAndAmount result = new AttackResultAndAmount(
                AttackResult.KILL, 12
        );

        assertEquals(AttackResult.KILL, result.getResult());
        assertEquals(12, result.getAmount());
    }

    @Test
    void testGetResult() {
        final AttackResultAndAmount result = new AttackResultAndAmount(
                AttackResult.DODGE, 0
        );

        assertEquals(result.myResult, result.getResult());
    }

    @Test
    void testGetAmount() {
        final AttackResultAndAmount result = new AttackResultAndAmount(
                AttackResult.HEAL, 30
        );

        assertEquals(result.myAmount, result.getAmount());
    }

    @Test
    void testGetNoAmountContainsAll() {
        for (AttackResultAndAmount result : NO_AMOUNTS) {
            assertEquals(
                    result,
                    AttackResultAndAmount.getNoAmount(result.getResult())
            );
        }
    }

    @Test
    void testGetNoAmountAllZero() {
        for (AttackResultAndAmount result : NO_AMOUNTS) {
            assertEquals(
                    0,
                    AttackResultAndAmount.getNoAmount(
                            result.getResult()
                    ).getAmount()
            );
        }
    }

    @Test
    void testGetNoAmountContainsAllAttackResult() {
        final List<AttackResult> noAmountsResults =
                Arrays.stream(NO_AMOUNTS)
                      .map(AttackResultAndAmount::getResult).toList();

        assertEquals(AttackResult.values().length, NO_AMOUNTS.length);
        for (AttackResult result : AttackResult.values()) {
            assertDoesNotThrow(
                    () -> AttackResultAndAmount.getNoAmount(result)
            );

            assertTrue(noAmountsResults.contains(result));
        }
    }
}
