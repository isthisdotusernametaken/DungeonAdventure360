package view;

public class Menu {

    private final String myTitle;
    private final String[] myMenuDescriptions;
    private final String[] myMenuOptions;

    Menu(final String theTitle,
         final String[] theMenuDescriptions,
         final String[] theMenuOptions) {
        myTitle = theTitle;
        myMenuDescriptions = theMenuDescriptions;
        myMenuOptions = theMenuOptions;
    }
    int select() {
        return 0;
    }

    int select(final int[] theExcludedOptions) {
        return 0;
    }
}
