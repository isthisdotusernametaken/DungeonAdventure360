package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class DamageDealerFactory<T extends DamageDealer> {

    private final List<List<T>> myTemplates = new ArrayList<>();

    DamageDealerFactory(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        buildModifiedTemplates(getUnmodifiedTemplates(new TemplateGenerator(
                theDBManager, theTable
        )));
    }

    String[] getClasses() {
        return mapToStrings(DamageDealer::getClassName);
    }

    String[] mapToStrings(final Function<T, String> theMappingFunc) {
        return myTemplates.get(0).stream()
                .map(theMappingFunc).toArray(String[]::new);
    }

    boolean isValidIndex(final int theIndex) {
        return Util.isValidIndex(theIndex, myTemplates.get(0).size());
    }

    T createRandom(final Difficulty theDifficulty) {
        return create(
                Util.randomIntExc(
                        myTemplates.get(theDifficulty.ordinal()).size()
                ),
                theDifficulty
        );
    }

    T create(final int theClassIndex, final Difficulty theDifficulty) {
        return createFromTemplate(
                myTemplates.get(theDifficulty.ordinal())
                           .get(theClassIndex)
        );
    }

    abstract T buildTemplate(TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException;

    abstract T buildModifiedTemplate(T theTemplate, Difficulty theDifficulty);

    abstract T createFromTemplate(T theTemplate);

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
