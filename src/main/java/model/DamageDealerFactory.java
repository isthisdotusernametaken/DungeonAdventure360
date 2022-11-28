package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DamageDealerFactory<T extends DamageDealer> {

    private final List<List<T>> myTemplates = new ArrayList<>();

    DamageDealerFactory(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        buildModifiedTemplates(getUnmodifiedTemplates(new TemplateGenerator(
                theDBManager, theTable
        )));
    }

    T createRandom(final Difficulty theDifficulty) {
        return create(
                Util.randomIntExc(
                        myTemplates.get(theDifficulty.ordinal()).size()
                ),
                theDifficulty
        );
    }

    List<T> createAll(final Difficulty theDifficulty) {
        final List<T> templates = myTemplates.get(theDifficulty.ordinal());
        final List<T> copies = new ArrayList<>(templates.size());
        for (T template : templates) {
            copies.add(createFromTemplate(template));
        }

        return copies;
    }

    T create(final int theClassIndex, final Difficulty theDifficulty) {
        return createFromTemplate(
                myTemplates.get(theDifficulty.ordinal())
                           .get(theClassIndex)
        );
    }

    abstract T buildTemplate(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException;

    abstract T buildModifiedTemplate(final T theTemplate,
                                     final Difficulty theDifficulty);

    abstract T createFromTemplate(final T theTemplate);

    private List<T> getUnmodifiedTemplates(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException {
        final List<T> unmodifiedTemplates = new ArrayList<>();
        while (theTable.next()) {
            unmodifiedTemplates.add(buildTemplate(theTable));
        }

        return unmodifiedTemplates;
    }

    private void buildModifiedTemplates(final List<T> theUnmodifiedTemplates) {
        List<T> modifiedTemplates;
        for (Difficulty difficulty : Difficulty.values()) {
            modifiedTemplates = new ArrayList<>(theUnmodifiedTemplates.size());

            for (T template : theUnmodifiedTemplates) {
                modifiedTemplates.add(buildModifiedTemplate(
                        template, difficulty
                ));
            }

            myTemplates.add(modifiedTemplates);
        }
    }
}
