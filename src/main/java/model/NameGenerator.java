package model;

import java.util.List;

/**
 * This class helps to generate random name for the dungeon characters.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class NameGenerator {

    /**
     * The string array containing the first names.
     */
    private final String[] myFirstNames;

    /**
     * The string array containing the last names.
     */
    private final String[] myLastNames;

    /**
     * Creates a new NameGenerator with the provided Lists of Strings as the
     * possible first and last names.
     *
     * @param theFirstNames The string list containing first names.
     * @param theLastNames  The string list containing last names.
     */
    NameGenerator(final List<String> theFirstNames,
                  final List<String> theLastNames) {
        myFirstNames = theFirstNames.toArray(new String[0]);
        myLastNames = theLastNames.toArray(new String[0]);
    }

    /**
     * Generates a name with a random first name and last name.
     *
     * @return The string representing random full name.
     */
    String generate() {
        final String lastName = myLastNames[Util.randomIntExc(myLastNames.length)];
        final String firstName = myFirstNames[Util.randomIntExc(myFirstNames.length)];

        return "".equals(lastName) ?
               firstName :
               firstName + ' ' + lastName;
    }
}
