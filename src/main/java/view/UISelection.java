package view;

public class UISelection {

    public static final int GUI = 0;
    public static final int CONSOLE_UI = 1;
    private static final String SELECT_PROMPT = null;
    private static final String SELECT_GUI = null;
    private static final String SELECT_CONSOLE_UI = null;

    public static int select() {
        return CONSOLE_UI;
    }
}
