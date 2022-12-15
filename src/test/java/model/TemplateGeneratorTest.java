package model;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;

import static model.MockDBManager.*;
import static model.TemplateGenerator.*;
import static model.TestingUtil.*;

public class TemplateGeneratorTest {

    private static final DBManager DB_MANAGER = new MockDBManager();
    private static TemplateGenerator UNUSED_NON_NULL_GENERATOR;

    static {
        try {
            UNUSED_NON_NULL_GENERATOR = new TemplateGenerator(
                    DB_MANAGER, "Adventurers"
            );
        } catch (SQLException | IllegalArgumentException e) {
            // Mock shouldn't throw exception while opening table, so should
            // never be encountered. Even if encountered, successfully avoids
            // incorrect static checking by compiler marking constructorHelper
            // as possibly returning null
            e.printStackTrace();
        }
    }

    @Test
    void testConstructorValidTable() {
        final String tableName = "Monsters";

        assertDoesNotThrow(() -> constructorHelper(tableName));

        final TemplateGenerator generator = constructorHelper("Monsters");

        assertEquals(1, generator.myColumn);
        assertNotNull(generator.myTable);
    }

    @Test
    void testConstructorInvalidTable() {
        assertThrows(
                SQLException.class,
                () -> new TemplateGenerator(DB_MANAGER, "not a table")
        );
    }

    @Test
    void textNextReturnTrue() {
        final TemplateGenerator generator = constructorHelper("Monsters");
        final MockTable table = (MockTable) generator.myTable;

        table.myRow = table.myFields.length - 1;
        try {
            assertTrue(generator.next());
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Mock doesn't throw SQLException
                                           // here. Should never be encountered
        }
    }

    @Test
    void textNextReturnFalse() {
        final TemplateGenerator generator = constructorHelper("Monsters");
        final MockTable table = (MockTable) generator.myTable;

        table.myRow = table.myFields.length;
        try {
            assertFalse(generator.next());
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Should never be encountered
        }
    }

    @Test
    void testGetSpecialSkillValid() {
        final TemplateGenerator generator = constructorHelper("Adventurers");

        generator.myColumn = 12;
        try {
            assertTrue(generator.getSpecialSkill() instanceof CrushingBlow);
        } catch (SQLException | IllegalArgumentException e) {
            unexpectedExceptionFailure(e); // Mock doesn't throw SQLException,
                                           // and mock should have fields in
                                           // the right place, so should never
                                           // be encountered
        }
    }

    @Test
    void testGetSpecialSkillInvalid() {
        final TemplateGenerator generator = constructorHelper("Adventurers");

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getSpecialSkill,
                SpecialSkillFactory.INVALID_SKILL +
                        MockDBManager.ADVENTURERS[0][0] + // Not a skill
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetResistanceDataValid() {
        final TemplateGenerator generator = constructorHelper("Monsters");

        generator.myColumn = 12;
        try {
            assertResistanceDataEqualsArray(
                    new double[]{0.1, 0.0, 0.2, 0.5, 0.1},
                    generator.getResistanceData()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Should never be encountered
        }
    }

    @Test
    void testGetResistanceDataTooFewValues() {
        getResistanceDataInvalidTestHelper(1);
    }

    @Test
    void testGetResistanceDataTooManyValues() {
        getResistanceDataInvalidTestHelper(2);
    }

    @Test
    void testGetResistanceDataInvalidValues() {
        getResistanceDataInvalidTestHelper(3);
    }

    @Test
    void testGetDamageTypeValid() {
        final TemplateGenerator generator = constructorHelper("Traps");

        generator.myColumn = 9; // Trap damage type column
        try {
            assertEquals(
                    DamageType.valueOf(TRAPS[0][8]),
                    generator.getDamageType()
            );
        } catch (IllegalArgumentException | SQLException e) {
            unexpectedExceptionFailure(e); // Should never be encountered
        }
    }

    @Test
    void testGetDamageTypeInvalid() {
        final TemplateGenerator generator = constructorHelper("Traps");

        generator.myColumn = 8; // Other column
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getDamageType,
                INVALID_DAMAGE_TYPE +
                        MockDBManager.TRAPS[0][7] + // Not a damage type
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetStringValid() {
        try {
            assertEquals(
                    MockDBManager.TRAPS[0][0],
                    constructorHelper("Traps").getString()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetStringNull() {
        final TemplateGenerator generator = constructorHelper("Null");

        nullTestHelper(generator, generator::getString);
    }

    @Test
    void testGetCharValid() {
        final TemplateGenerator generator = constructorHelper("Traps");

        generator.myColumn = TRAPS[0].length;
        try {
            assertEquals(
                    MockDBManager.TRAPS[0][TRAPS[0].length - 1].charAt(0),
                    generator.getChar()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetCharEmpty() {
        final TemplateGenerator generator = constructorHelper("Empty");

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getChar,
                INVALID_CHAR_LENGTH +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetCharTooLong() {
        final TemplateGenerator generator = constructorHelper("Adventurers");

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getChar,
                INVALID_CHAR_LENGTH +
                        ADVENTURERS[0][0] +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetCharNull() {
        final TemplateGenerator generator = constructorHelper("Null");

        nullTestHelper(generator, generator::getChar);
    }

    @Test
    void testGetIntMin() {
        final TemplateGenerator generator = constructorHelper("Ints");

        try {
            assertEquals(
                    1,
                    generator.getInt()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetIntMax() {
        final TemplateGenerator generator = constructorHelper("Ints");

        generator.myColumn = 2;
        try {
            assertEquals(
                    Util.MAX_INT,
                    generator.getInt()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetIntInvalid() {
        final TemplateGenerator generator = constructorHelper("Monsters");

        invalidFieldTestHelper(generator, generator::getInt);
    }

    @Test
    void testGetIntNull() {
        final TemplateGenerator generator = constructorHelper("Null");

        nullTestHelper(generator, generator::getInt);
    }

    @Test
    void testGetIntTooSmall() {
        final TemplateGenerator generator = constructorHelper("Ints");

        generator.myColumn = 3;
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getInt,
                INVALID_INT + 0 + getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetIntTooLarge() {
        final TemplateGenerator generator = constructorHelper("Ints");

        generator.myColumn = 4;
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getInt,
                INVALID_INT + (Util.MAX_INT + 1) +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetDoubleMin() {
        final TemplateGenerator generator = constructorHelper("Doubles");

        try {
            assertEquals(
                    0.0,
                    generator.getDouble()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetDoubleMax() {
        final TemplateGenerator generator = constructorHelper("Doubles");

        generator.myColumn = 2;
        try {
            assertEquals(
                    1.0,
                    generator.getDouble()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetDoubleInvalid() {
        final TemplateGenerator generator = constructorHelper("Monsters");

        invalidFieldTestHelper(generator, generator::getDouble);
    }

    @Test
    void testGetDoubleNull() {
        final TemplateGenerator generator = constructorHelper("Null");

        nullTestHelper(generator, generator::getDouble);
    }

    @Test
    void testGetDoubleTooSmall() {
        final TemplateGenerator generator = constructorHelper("Doubles");

        generator.myColumn = 3;
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getDouble,
                INVALID_PROBABILITY + DOUBLES[0][2] +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetDoubleTooLarge() {
        final TemplateGenerator generator = constructorHelper("Doubles");

        generator.myColumn = 4;
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getDouble,
                INVALID_PROBABILITY + DOUBLES[0][3] +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetBooleanValid() {
        final TemplateGenerator generator = constructorHelper("Traps");

        generator.myColumn = 2;
        try {
            assertEquals(
                    Boolean.parseBoolean(MockDBManager.TRAPS[0][1]),
                    generator.getBoolean()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testGetBooleanInvalid() {
        final TemplateGenerator generator = constructorHelper("Traps");

        invalidFieldTestHelper(generator, generator::getBoolean);
    }

    @Test
    void testGetBooleanNull() {
        final TemplateGenerator generator = constructorHelper("Null");

        nullTestHelper(generator, generator::getBoolean);
    }

    @Test
    void testGetFieldLocation() {
        final TemplateGenerator generator = constructorHelper("Adventurers");

        // Simulate increment that always occurs before call to
        // getFieldLocation (since getFieldLocation only called after failed
        // field retrieval)
        generator.myColumn++;
        try {
            assertEquals(
                    " (Adventurers: row 1, column 1)",
                    generator.getFieldLocation()
            );
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }
    }

    @Test
    void testInvalidFieldException() {
        final TemplateGenerator generator = constructorHelper("Adventurers");

        invalidFieldTestHelper(
                generator, () -> {
                    generator.myColumn++;
                    throw generator.invalidFieldException();
                }
        );
    }

    @Test
    void testExceptionOnNullNoException() {
        final TemplateGenerator generator = constructorHelper("Adventurers");
        try {
            generator.getString();
        } catch (SQLException e) {
            unexpectedExceptionFailure(e);
        }

        assertDoesNotThrow(
                generator::exceptionOnNull
        );
    }

    @Test
    void testExceptionOnNullException() {
        final TemplateGenerator generator = constructorHelper("Null");
        ((MockTable) generator.myTable).myNull = true;

        nullTestHelper(generator, () -> {
            generator.myColumn++;
            generator.exceptionOnNull();
        });
    }

    @Test
    void testResistanceDataException() {
        final TemplateGenerator generator = constructorHelper("Adventurers");
        final String field = "bad field";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> {
                    generator.myColumn++;
                    generator.resistanceDataException(field);
                },
                INVALID_RESISTANCE_DATA + field +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testExceptionOnInvalidCharLengthNoException() {
        assertDoesNotThrow(
                () -> constructorHelper("Adventurers")
                        .exceptionOnInvalidCharLength("P")
        );
    }

    @Test
    void testExceptionOnInvalidCharLengthException() {
        // Other failure cases tested in tests of getChar, so not tested again
        // here
        final TemplateGenerator generator = constructorHelper("Adventurers");
        final String field = "PPP";

        generator.myColumn++;
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> {
                    generator.myColumn++;
                    generator.exceptionOnInvalidCharLength(field);
                },
                INVALID_CHAR_LENGTH + field +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testExceptionOnIllegalIntNoException() {
        // Multiple cases tested in tests of getInt, so only
        // exception/no exception behavior tested here
        assertDoesNotThrow(
                () -> constructorHelper("Adventurers")
                        .exceptionOnIllegalInt(1)
        );
    }

    @Test
    void testExceptionOnIllegalIntException() {
        final TemplateGenerator generator = constructorHelper("Adventurers");
        final int field = 1337;

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> {
                    generator.myColumn++;
                    generator.exceptionOnIllegalInt(field);
                },
                INVALID_INT + field +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testExceptionOnIllegalProbabilityNoException() {
        // Multiple cases tested in tests of getInt, so only
        // exception/no exception behavior tested here
        assertDoesNotThrow(
                () -> constructorHelper("Adventurers")
                        .exceptionOnIllegalProbability(0.75)
        );
    }

    @Test
    void testExceptionOnIllegalProbabilityException() {
        final TemplateGenerator generator = constructorHelper("Adventurers");
        final double field = 1.005;

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> {
                    generator.myColumn++;
                    generator.exceptionOnIllegalProbability(field);
                },
                INVALID_PROBABILITY + field +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    private TemplateGenerator constructorHelper(final String theTable) {
        try {
            return new TemplateGenerator(DB_MANAGER, theTable);
        } catch (SQLException | IllegalArgumentException e) {
            unexpectedExceptionFailure(e); // Mock shouldn't throw SQLException
            return UNUSED_NON_NULL_GENERATOR; // and all table names in tests
                                              // should be correct
        }
    }

    private void nullTestHelper(final TemplateGenerator theGenerator,
                                final Executable theMethod) {
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                theMethod,
                NULL_FIELD + getFieldLocationBeforeCallHelper(theGenerator)
        );
    }

    private void invalidFieldTestHelper(final TemplateGenerator theGenerator,
                                        final Executable theMethod) {
        assertThrowsWithMessage(
                SQLException.class,
                theMethod,
                INVALID_FIELD + getFieldLocationBeforeCallHelper(theGenerator)
        );
    }

    private String getFieldLocationBeforeCallHelper(final TemplateGenerator theGenerator) {
        try {
            theGenerator.myColumn++; // Simulate myColumn++ from start of call
            final String fieldLocation = theGenerator.getFieldLocation();
            theGenerator.myColumn--;

            return fieldLocation;
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Mock shouldn't throw SQLException
            return null;
        }
    }

    private void getResistanceDataInvalidTestHelper(final int theColumn) {
        final TemplateGenerator generator = constructorHelper("InvalidResistances");
        generator.myColumn = theColumn;

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getResistanceData,
                INVALID_RESISTANCE_DATA +
                        INVALID_RESISTANCES[0][theColumn - 1] +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }
}
