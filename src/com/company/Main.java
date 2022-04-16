package com.company;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class Main extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JPanel field = new JPanel();



    public Main() {
        super ("Лабараторная работа №7");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        getContentPane().add(field);

    }



    public static void main(String[] args) {
       Main frame = new Main();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);

    }
}
