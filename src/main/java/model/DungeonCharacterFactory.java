package model;

import java.sql.SQLException;

public abstract class DungeonCharacterFactory<T extends DungeonCharacter>
        extends DamageDealerFactory<T> {

    final NameGenerator myNameGenerator;

    DungeonCharacterFactory(final DBManager theDBManager,
                            final String theStatsTable,
                            final String theFirstNamesTable,
                            final String theLastNamesTable)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, theStatsTable);

        myNameGenerator = NameGeneratorFactory.create(
                theDBManager,
                theFirstNamesTable, theLastNamesTable
        );
    }

    String generateName() {
        return myNameGenerator.generate();
    }
}
