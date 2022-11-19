package model;

import java.sql.SQLException;

import static model.MockDBManager.INVALID_RESISTANCES;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.MockDBManager.MockTable;
import static model.TemplateGenerator.*;
import static model.TestingUtil.*;

public class TemplateGeneratorTest {

    @Test
    void testConstructorValidTable() {
        final String tableName = "Monsters";

        assertDoesNotThrow(() -> new TemplateGenerator(tableName));

        final TemplateGenerator generator = new TemplateGenerator(tableName);

        assertEquals(1, generator.myColumn);
        assertNotNull(generator.myTable);
    }

    @Test
    void testConstructorInvalidTable() {
        final String table = "";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> new TemplateGenerator(table),
                INVALID_TABLE + table
        );
    }

    @Test
    void testResistanceDataException() {
        final String field = "badfield";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> resistanceDataException(field),
                INVALID_RESISTANCE_DATA + field
        );
    }

    @Test
    void testExceptionOnMultipleCharsOneChar() {
        assertDoesNotThrow(() -> exceptionOnMultipleChars("a"));
    }

    @Test
    void testExceptionOnMultipleCharsEmpty() {
        final String field = "";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> exceptionOnMultipleChars(field),
                CHAR_TOO_LONG + field
        );
    }

    @Test
    void testExceptionOnMultipleCharsMultipleChars() {
        final String field = "abc";

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                () -> exceptionOnMultipleChars(field),
                CHAR_TOO_LONG + field
        );
    }

    @Test
    void textNextReturnTrue() {
        final TemplateGenerator generator = new TemplateGenerator("Monsters");
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
        final TemplateGenerator generator = new TemplateGenerator("Monsters");
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
        final TemplateGenerator generator = new TemplateGenerator("Adventurers");

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
        final TemplateGenerator generator = new TemplateGenerator("Adventurers");

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getSpecialSkill,
                SpecialSkillFactory.INVALID_SKILL +
                        MockDBManager.ADVENTURERS[0][0]
        );
    }

    @Test
    void testGetResistanceDataValid() {
        final TemplateGenerator generator = new TemplateGenerator("Monsters");

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

    private void getResistanceDataInvalidTestHelper(final int theColumn) {
        final TemplateGenerator generator = new TemplateGenerator("InvalidResistances");
        generator.myColumn = theColumn;

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                generator::getResistanceData,
                INVALID_RESISTANCE_DATA + INVALID_RESISTANCES[0][theColumn - 1]
        );
    }
}
