package model;

import org.junit.jupiter.api.Test;

import static model.AttackResult.*;
import static model.TestingUtil.*;

public class AttackResultTest {

    @Test
    void testToStringNoAction() {
        assertNotNullAndEquals("No Action", NO_ACTION.toString());
    }

    @Test
    void testToStringBuffDamage() {
        assertNotNullAndEquals("Buff Damage", BUFF_DAMAGE.toString());
    }

    @Test
    void testToStringHeal() {
        assertNotNullAndEquals("Heal", HEAL.toString());
    }

    @Test
    void testToStringKill() {
        assertNotNullAndEquals("Kill", KILL.toString());
    }

    @Test
    void testToStringHitNoDebuff() {
        assertNotNullAndEquals("Hit No Debuff", HIT_NO_DEBUFF.toString());
    }

    @Test
    void testToStringHitDebuff() {
        assertNotNullAndEquals("Hit Debuff", HIT_DEBUFF.toString());
    }

    @Test
    void testToStringExtraTurnNoDebuff() {
        assertNotNullAndEquals(
                "Extra Turn No Debuff", EXTRA_TURN_NO_DEBUFF.toString()
        );
    }

    @Test
    void testToStringExtraTurnDebuff() {
        assertNotNullAndEquals(
                "Extra Turn Debuff", EXTRA_TURN_DEBUFF.toString()
        );
    }

    @Test
    void testToStringFledSuccessfully() {
        assertNotNullAndEquals(
                "Fled Successfully", FLED_SUCCESSFULLY.toString()
        );
    }

    @Test
    void testToStringCouldNotFlee() {
        assertNotNullAndEquals("Could Not Flee", COULD_NOT_FLEE.toString());
    }

    @Test
    void testToStringMiss() {
        assertNotNullAndEquals("Miss", MISS.toString());
    }

    @Test
    void testToStringBlock() {
        assertNotNullAndEquals("Block", BLOCK.toString());
    }

    @Test
    void testToStringDodge() {
        assertNotNullAndEquals("Dodge", DODGE.toString());
    }
}
