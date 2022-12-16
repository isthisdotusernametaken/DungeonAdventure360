package model;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.SpecialSkillFactory.*;
import static model.TestingUtil.*;

public class SpecialSkillFactoryTest {

    @Test
    void testCreateSpecialSkillCrushingBlow() {
        createSpecialSkillTestHelper(CrushingBlow.class, "CrushingBlow");
    }

    @Test
    void testCreateSpecialSkillHeal() {
        createSpecialSkillTestHelper(Heal.class, "Heal");
    }

    @Test
    void testCreateSpecialSkillSneakAttack() {
        createSpecialSkillTestHelper(SneakAttack.class, "SneakAttack");
    }

    @Test
    void testCreateSpecialSkillInvalid() {
        invalidSkillExceptionHelper(SpecialSkillFactory::createSpecialSkill);
    }

    @Test
    void testInvalidSkillException() {
        invalidSkillExceptionHelper(str -> {
            throw invalidSkillException(str);
        });
    }

    private void createSpecialSkillTestHelper(final Class<? extends SpecialSkill> theType,
                                              final String theSkill) {
        assertEquals(
                theType,
                createSpecialSkill(theSkill).getClass()
        );
    }

    private void invalidSkillExceptionHelper(final Consumer<String> theMethod) {
        final String skill = "not a skill";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> theMethod.accept(skill),
                INVALID_SKILL + skill
        );
    }
}
