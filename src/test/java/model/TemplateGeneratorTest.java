package model;

import java.sql.SQLException;

import static model.MockDBManager.INVALID_RESISTANCES;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.MockDBManager.MockTable;
import static model.TemplateGenerator.*;
import static model.TestingUtil.*;

public class TemplateGeneratorTest {

    private static final DBManager DB_MANAGER = new MockDBManager();

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
        final String table = "";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> constructorHelper(table),
                INVALID_TABLE + table
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
            unexpectedExceptionFailure(e); // Mock doesn't throw SQLException.
                                           // Should never be encountered
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

        generator.myColumn = ((MockTable) generator.myTable)
                             .myFields[0].length; // Point to last field

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
                        MockDBManager.ADVENTURERS[0][0] +
                        getFieldLocationBeforeCallHelper(generator)
        );
    }

    @Test
    void testGetResistanceDataValid() {
        final TemplateGenerator generator = constructorHelper("Monsters");

        generator.myColumn = ((MockTable) generator.myTable)
                             .myFields[0].length; // Point to last field

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
        final TemplateGenerator generator = constructorHelper("Nulls");

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getString,
                NULL_FIELD + getFieldLocationBeforeCallHelper(generator)
        );
    }



//    @Test
//    void testResistanceDataException() {
//        final String field = "badfield";
//
//        assertThrowsWithMessage(
//                IllegalArgumentException.class,
//                () -> resistanceDataException(field),
//                INVALID_RESISTANCE_DATA + field
//        );
//    }
//
//    @Test
//    void testExceptionOnMultipleCharsOneChar() {
//        assertDoesNotThrow(() -> exceptionOnMultipleChars("a"));
//    }
//
//    @Test
//    void testExceptionOnMultipleCharsEmpty() {
//        final String field = "";
//
//        assertThrowsWithMessage(
//                IllegalArgumentException.class,
//                () -> exceptionOnMultipleChars(field),
//                CHAR_TOO_LONG + field
//        );
//    }
//
//    @Test
//    void testExceptionOnMultipleCharsMultipleChars() {
//        final String field = "abc";
//
//        assertThrowsWithMessage(
//                IllegalArgumentException.class,
//                () -> exceptionOnMultipleChars(field),
//                CHAR_TOO_LONG + field
//        );
//    }

    private TemplateGenerator constructorHelper(final String theTable) {
        try {
            return new TemplateGenerator(DB_MANAGER, theTable);
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Mock should not throw SQLException
            return null;
        }
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

    private String getFieldLocationAfterCallHelper(final TemplateGenerator theGenerator) {
        try {
            return theGenerator.getFieldLocation();
        } catch (SQLException e) {
            unexpectedExceptionFailure(e); // Mock should not throw SQLException
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
