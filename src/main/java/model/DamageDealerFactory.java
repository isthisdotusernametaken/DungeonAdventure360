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
 * This factory produces DamageDealer objects with classes and stats
 * defined by the DB provided when building the factory.
 *
 * @param <T> The kind of DamageDealer the factory creates
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class DamageDealerFactory<T extends DamageDealer> {

    /**
     * The list of templates.
     */
    private final List<List<T>> myTemplates = new ArrayList<>();

    /**
     * Reads its table from the provided DB and creates templates to build
     * Monsters from.
     *
     * @param theDBManager The DB to build the factory from
     * @param theTable The name of the table to read for stats
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    DamageDealerFactory(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        buildModifiedTemplates(getUnmodifiedTemplates(new TemplateGenerator(
                theDBManager, theTable
        )));
    }

    /**
     * Gets the name of each class of this kind of DamageDealer that the
     * factory can create.
     *
     * @return A String array containing the names of allowed classes.
     */
    String[] getClasses() {
        return mapToStrings(DamageDealer::getClassName);
    }

    /**
     * High-order function helps to format the results of a class method
     * to string array.
     *
     * @param theMappingFunc Maps each class to a String.
     * @return An array of Strings, corresponding to the allowed classes.
     */
    String[] mapToStrings(final Function<T, String> theMappingFunc) {
        return myTemplates.get(0).stream()
                .map(theMappingFunc).toArray(String[]::new);
    }

    /**
     * Checks if the index refers to a valid template
     *
     * @param theIndex  The index of a template
     * @return Whether a template with that index exists
     */
    boolean isValidIndex(final int theIndex) {
        return Util.isValidIndex(theIndex, myTemplates.get(0).size());
    }

    /**
     * Creates a DamageDealer based on a randomly selected template
     *
     * @param theDifficulty The difficulty to choose a template for
     * @return A new DamageDealer built for the provided Difficulty
     */
    T createRandom(final Difficulty theDifficulty) {
        return create(
                Util.randomIntExc(
                        myTemplates.get(theDifficulty.ordinal()).size()
                ),
                theDifficulty
        );
    }

    /**
     * Creates a DamageDealer based on the specified template.
     *
     * @param theDifficulty The difficulty to choose a template for
     * @return A new DamageDealer built for the provided Difficulty
     */
    T create(final int theClassIndex, final Difficulty theDifficulty) {
        return createFromTemplate(
                myTemplates.get(theDifficulty.ordinal())
                           .get(theClassIndex)
        );
    }

    /**
     * Constructs a template DamageDealer from a row in a DB table.
     *
     * @param theTable The input processor between the DB and the factory.
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    abstract T buildTemplate(TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException;

    /**
     * Creates a template with its stats adjusted according to the difficulty
     * level
     *
     * @param theTemplate The template DamageDealer to use
     * @param theDifficulty The difficulty level to create a template for
     */
    abstract T buildModifiedTemplate(T theTemplate, Difficulty theDifficulty);

    /**
     * Creates a DamageDealer from the provided template.
     *
     * @param theTemplate The template DamageDealer.
     */
    abstract T createFromTemplate(T theTemplate);

    /**
     * Creates a template for each row of the provided DB table
     *
     * @param theTable The input processor between the DB and the factory.
     * @return A List of DamageDealer templates built from the DB table
     */
    private List<T> getUnmodifiedTemplates(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException {
        final List<T> unmodifiedTemplates = new ArrayList<>();
        while (theTable.next()) {
            unmodifiedTemplates.add(buildTemplate(theTable));
        }

        return unmodifiedTemplates;
    }

    /**
     * Builds the list of templates for each Difficulty level, adjusting the
     * base templates according to the subclass' implementation of
     * buildModifiedTemplate
     *
     * @param theUnmodifiedTemplates The templates generated from the DB
     */
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
