package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NameGeneratorFactory {

    static NameGenerator create(final DBManager theDBManager,
                                final String theFirstNamesTable,
                                final String theLastNamesTable)
            throws SQLException {
        return new NameGenerator(
                readNames(theDBManager, theFirstNamesTable),
                readNames(theDBManager, theLastNamesTable)
        );
    }

    private static List<String> readNames(final DBManager theDBManager,
                                          final String theTable)
            throws SQLException {
        final TemplateGenerator table = new TemplateGenerator(
                theDBManager, theTable
        );

        final List<String> names = new ArrayList<>();
        while (table.next()) {
            names.add(table.getString());
        }

        return names;
    }
}
