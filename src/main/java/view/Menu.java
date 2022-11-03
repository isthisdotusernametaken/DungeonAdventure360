package view;

public class Menu {
    private final String myTitle;
    private final String[] myMenuDescriptions;
    private final String[] myMenuOptions;

    Menu(String theTitle, String[] theMenuDescriptions, String[] theMenuOptions) {
        myTitle = theTitle;
        myMenuDescriptions = theMenuDescriptions;
        myMenuOptions = theMenuOptions;
    }
    int select() {
        return 0;
    }

    int select(int[] theExcludedOptions) {
        return 0;
    }
}
