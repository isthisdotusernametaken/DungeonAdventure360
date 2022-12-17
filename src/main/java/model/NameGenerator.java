package model;

import java.util.List;

public class NameGenerator {

    final String[] myFirstNames;
    final String[] myLastNames;

    NameGenerator(final List<String> theFirstNames,
                  final List<String> theLastNames) {
        myFirstNames = theFirstNames.toArray(new String[0]);
        myLastNames = theLastNames.toArray(new String[0]);
    }

    String generate() {
        final String lastName = myLastNames[Util.randomIntExc(myLastNames.length)];
        final String firstName = myFirstNames[Util.randomIntExc(myFirstNames.length)];

        return "".equals(lastName) ?
               firstName :
               firstName + ' ' + lastName;
    }
}
