package model;

import java.util.List;

/**
 * This class helps to generate random name for the dungeon characters and
 * for the dungeon characters in the database table.
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
     * Constructor to construct the name generator using the list
     * of first name and the list of last name.
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
     * Generates and obtains random first name and last name.
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
