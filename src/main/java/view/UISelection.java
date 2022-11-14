package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UISelection extends JFrame implements ActionListener {

    public static final int GUI = 0;
    public static final int CONSOLE_UI = 1;
    private static final String SELECT_PROMPT = null;
    private static final String SELECT_GUI = null;
    private static final String SELECT_CONSOLE_UI = null;

    public static int select() {
        return CONSOLE_UI;
    }

    static JFrame f;
    static JDialog d, d1, a;

    public static void frame(String[] args) {

        f  = new JFrame("Dungeon Adventure");

        UISelection ob = new UISelection();

        JPanel pan = new JPanel();

        JButton but = new JButton("SELECT_PROMPT");

        but.addActionListener(ob);

        pan.add(but);
        f.add(pan);

        f.setSize(500, 300);
        f.show();
    }
    public void actionPerformed(ActionEvent e) {
        String ob = e.getActionCommand();
        if (ob.equals("SELECT_PROMPT")) {
            d = new JDialog(f, "CONSOLE_UI");

           JLabel a = new JLabel("click this to play with UI");
            JButton b = new JButton("SELECT_CONSOLE_UI");

            b.addActionListener(this);

            JPanel p = new JPanel();
            p.add(b);
            p.add(a);

            d.add(p);
            d.setSize(100,100);
            d.setVisible(true);
        }
        else {
          d1 = new JDialog(d, "GUI");
          JLabel a = new JLabel("click here to play with GUI");
          JButton b = new JButton("SELECT_GUI");

          b.addActionListener(this);
          JPanel p = new JPanel();
          p.add(a);
          p.add(b);

          d1.add(p);

          d1.setSize(100,100);
          d1.setLocation(0,250);
          d1.setVisible(true);
        }
    }


}

