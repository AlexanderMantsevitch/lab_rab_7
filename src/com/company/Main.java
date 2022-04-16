package com.company;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class Main extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;

    private final JTextArea textAreaIncoming;


    private JPanel field = new JPanel();



    public Main() {
        super ("Лабараторная работа №7");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(
                new Dimension(WIDTH, HEIGHT));
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        getContentPane().add(field);

        textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS, 0);
        textAreaIncoming.setEditable(false);
        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);
        field.add (textAreaIncoming);

    }



    public static void main(String[] args) {
       Main frame = new Main();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);

    }
}
