/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This abstract factory class helps to create database table templates
 * and let subclasses use it to prevent duplication of code.
 * @param <T> Generic class that is parameterized over types.
 */
public abstract class DamageDealerFactory<T extends DamageDealer> {

    /**
     * The list of templates.
     */
    private final List<List<T>> myTemplates = new ArrayList<>();

    /**
     * Constructor to construct the damage dealer factory.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and manage the database for adventurer.
     * @param theTable                  The string representing the stats of
     *                                  the dungeon character in the format
     *                                  of the database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
     */
    DamageDealerFactory(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        buildModifiedTemplates(getUnmodifiedTemplates(new TemplateGenerator(
                theDBManager, theTable
        )));
    }

    /**
     * Gets the class type name of the dungeon character or the class type
     * name of the dungeon object, as in string array.
     *
     * @return  The string array representing the class type name of the
     *          dungeon character or the class type name of the dungeon
     *          object, as in string array.
     */
    String[] getClasses() {
        return mapToStrings(DamageDealer::getClassName);
    }

    /**
     * High-order function helps to format the results of a class method
     * to string array.
     *
     * @param theMappingFunc A class method to get its results.
     * @return               The string array containing all the mapped
     *                       results of that function.
     */
    String[] mapToStrings(final Function<T, String> theMappingFunc) {
        return myTemplates.get(0).stream()
                .map(theMappingFunc).toArray(String[]::new);
    }

    /**
     * Checks if the index is valid.
     *
     * @param theIndex  The integer value representing the index value.
     * @return          The boolean true or false if the index is valid.
     */
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

    /**
     * Template method for buildTemplate method.
     *
     * @param theTable The template generator table.
     * @return         The type.
     */
    abstract T buildTemplate(TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException;

    /**
     * Template method for buildModifiedTemplate method.
     *
     * @param theTemplate The type template.
     * @return            The type.
     */
    abstract T buildModifiedTemplate(T theTemplate, Difficulty theDifficulty);

    /**
     * Template method for createFromTemplate method.
     *
     * @param theTemplate The type template.
     * @return            The type.
     */
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
