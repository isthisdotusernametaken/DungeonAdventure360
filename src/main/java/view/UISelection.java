package view;

import javax.swing.JOptionPane;

public class UISelection {

    public static final int GUI = 0;
    public static final int CONSOLE_UI = 1;
    private static final String TITLE = "Select a UI";
    private static final String SELECT_PROMPT =
            "Select a UI to play Dungeon Adventure with, or press Cancel to " +
            "close the application.";
    private static final String SELECT_GUI = "GUI";
    private static final String SELECT_CONSOLE_UI = "Console UI";
    private static final String SELECT_CANCEL = "Cancel";

    public static int select() {
        return JOptionPane.showOptionDialog(
                null,
                SELECT_PROMPT,
                TITLE,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{SELECT_GUI, SELECT_CONSOLE_UI, SELECT_CANCEL},
                null
        );
    }
}

