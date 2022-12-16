package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.BuffType.*;
import static model.DamageType.*;
import static model.TestingUtil.*;

public class DamageTypeTest {

    @Test
    void testToStringNormal() {
        assertNotNullAndEquals("Normal", NORMAL.toString());
    }

    @Test
    void testToStringSharp() {
        assertNotNullAndEquals("Sharp", SHARP.toString());
    }

    @Test
    void testToStringBlunt() {
        assertNotNullAndEquals("Blunt", BLUNT.toString());
    }

    @Test
    void testToStringFire() {
        assertNotNullAndEquals("Fire", FIRE.toString());
    }

    @Test
    void testToStringPoison() {
        assertNotNullAndEquals("Poison", POISON.toString());
    }

    @Test
    void testGetDebuffTypeNormal() {
        assertEquals(NONE, NORMAL.getDebuffType());
    }

    @Test
    void testGetDebuffTypeSharp() {
        assertEquals(BLEEDING, SHARP.getDebuffType());
    }

    @Test
    void testGetDebuffTypeBlunt() {
        assertEquals(BROKEN_BONE, BLUNT.getDebuffType());
    }

    @Test
    void testGetDebuffTypeFire() {
        assertEquals(BURNING, FIRE.getDebuffType());
    }

    @Test
    void testGetDebuffTypePoison() {
        assertEquals(POISONED, POISON.getDebuffType());
    }
}
