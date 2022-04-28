package com.company;

import org.w3c.dom.stylesheets.DocumentStyle;
import org.w3c.dom.stylesheets.StyleSheetList;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Main extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;

    private  JTextPane textAreaIncoming;
    private  JTextArea textAreaincoming =  new JTextArea();

    private  JTextPane textAreaOutgoing;

    private final JTextField textFieldFrom;
    private final JTextField textFieldTo;
    private JPanel field = new JPanel();
    private JScrollPane scrollPaneOutgoing;
    private ArrayList <Integer> kursive_array = new ArrayList<>();
    private ArrayList <Integer> bold_array = new ArrayList<>();
    private ArrayList <Integer> under_array = new ArrayList<>();
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;

    private static final int SERVER_PORT = 4567;



    public Main() {
        super ("Лабараторная работа №7");

        setSize(WIDTH, HEIGHT);
        setMinimumSize(
                new Dimension(WIDTH, HEIGHT));
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);


        textAreaIncoming = new JTextPane();
        textAreaIncoming.setEditable(false);
        textAreaIncoming.setText(" ");
        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);

        textAreaOutgoing = new JTextPane();

       scrollPaneOutgoing =
                new JScrollPane(textAreaOutgoing);

        final JLabel labelFrom = new JLabel("Подпись");
        final JLabel labelTo = new JLabel("Получатель");
        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
        textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);




        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(
                BorderFactory.createTitledBorder("Сообщение"));

        final JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendMessage();
               kursive_array = new ArrayList<Integer>();
                bold_array = new ArrayList<Integer>();
                under_array = new ArrayList<Integer>();

               try {

                   JTextPane aa = new JTextPane(textAreaOutgoing.getStyledDocument());
                   SimpleAttributeSet att1 = new SimpleAttributeSet(); ;
                   StyleConstants.setItalic(att1, false);
                   StyleConstants.setBold(att1, false);


                   aa.select(0, aa.getStyledDocument().getLength());
                   aa.replaceSelection("");
                   aa.getStyledDocument().insertString(0,
                           " ", att1);
                   textAreaOutgoing.setStyledDocument(aa.getStyledDocument());


               }
               catch (BadLocationException exc) {


               }
            }
        });
        final JButton kursive = new JButton("K");
        kursive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (textAreaOutgoing.getSelectionStart() != textAreaOutgoing.getSelectionEnd() )
                {
                try {
                    JTextPane pane = new JTextPane(textAreaOutgoing.getStyledDocument());

                    SimpleAttributeSet att = new SimpleAttributeSet();

                    kursive_array.add(textAreaOutgoing.getSelectionStart());
                    kursive_array.add(textAreaOutgoing.getSelectionEnd());
                    StyleConstants.setItalic(att, true);
                    pane.setCharacterAttributes(att, true);
                    String a = textAreaOutgoing.getText();

                    pane.getStyledDocument().insertString(textAreaOutgoing.getSelectionStart(),
                            textAreaOutgoing.getText(textAreaOutgoing.getSelectionStart(), textAreaOutgoing.getSelectionEnd()
                                    - textAreaOutgoing.getSelectionStart()), att);
                    textAreaOutgoing.replaceSelection("");
                    textAreaOutgoing.setStyledDocument(pane.getStyledDocument());



                }
                catch (BadLocationException exc)
                {


                }
                }

            }
        });


        final JButton bold = new JButton("B");
        bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textAreaOutgoing.getSelectionStart() != textAreaOutgoing.getSelectionEnd() )
                {
                    try {
                        JTextPane pane = new JTextPane(textAreaOutgoing.getStyledDocument());

                        SimpleAttributeSet att = new SimpleAttributeSet();
                        bold_array.add(textAreaOutgoing.getSelectionStart());
                        bold_array.add(textAreaOutgoing.getSelectionEnd());

                        StyleConstants.setBold(att, true);
                        pane.setCharacterAttributes(att, true);
                        String a = textAreaOutgoing.getText();

                        pane.getStyledDocument().insertString(textAreaOutgoing.getSelectionStart(),
                                textAreaOutgoing.getText(textAreaOutgoing.getSelectionStart(), textAreaOutgoing.getSelectionEnd()
                                        - textAreaOutgoing.getSelectionStart()), att);
                        textAreaOutgoing.replaceSelection("");
                        textAreaOutgoing.setStyledDocument(pane.getStyledDocument());



                    }
                    catch (BadLocationException exc)
                    {


                    }
                }

            }
        });

        final JButton under = new JButton("П");
        under.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textAreaOutgoing.getSelectionStart() != textAreaOutgoing.getSelectionEnd() )
                {
                    try {
                        JTextPane pane = new JTextPane(textAreaOutgoing.getStyledDocument());

                        SimpleAttributeSet att = new SimpleAttributeSet();

                        under_array.add(textAreaOutgoing.getSelectionStart());
                        under_array.add(textAreaOutgoing.getSelectionEnd());
                        StyleConstants.setUnderline(att, true);
                        pane.setCharacterAttributes(att, true);
                        String a = textAreaOutgoing.getText();

                        pane.getStyledDocument().insertString(textAreaOutgoing.getSelectionStart(),
                                textAreaOutgoing.getText(textAreaOutgoing.getSelectionStart(), textAreaOutgoing.getSelectionEnd()
                                        - textAreaOutgoing.getSelectionStart()), att);
                        textAreaOutgoing.replaceSelection("");
                        textAreaOutgoing.setStyledDocument(pane.getStyledDocument());



                    }
                    catch (BadLocationException exc)
                    {


                    }
                }

            }
        });

        JPanel buttons = new JPanel();
        buttons.add (kursive);
        buttons.add (bold);
        buttons.add(under);



        final GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP)
                                .addComponent(labelTo)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldTo))
                                .addComponent(buttons)
                        .addComponent(scrollPaneOutgoing)
                        .addComponent(sendButton)
                        )

                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(labelTo)
                        .addComponent(textFieldTo))
                        .addGap(MEDIUM_GAP)
                .addComponent(buttons)
                .addComponent(scrollPaneOutgoing)

                .addGap(MEDIUM_GAP)
                .addComponent(sendButton)


                .addContainerGap());



        final GroupLayout layout1 = new GroupLayout(getContentPane());
        setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout1.createParallelGroup()
                        .addComponent(scrollPaneIncoming)
                        .addComponent(messagePanel))
                .addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneIncoming)
                .addGap(MEDIUM_GAP)
                .addComponent(messagePanel)
                .addContainerGap());

        new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                ArrayList <Integer> kursive_arr = new ArrayList<Integer>();
                ArrayList <Integer> bold_arr = new ArrayList<Integer>();
                ArrayList <Integer> under_arr = new ArrayList<Integer>();
                final ServerSocket serverSocket =
                        new ServerSocket(SERVER_PORT);
                while (!Thread.interrupted()) {

                    final Socket socket = serverSocket.accept();
                    final DataInputStream in = new DataInputStream(
                            socket.getInputStream());
// Читаем имя отправителя
                     String senderName = in.readUTF();
// Читаем сообщение
                     String message = in.readUTF();

                    try
                    { final ObjectInputStream inn = new ObjectInputStream(socket.getInputStream());

                        kursive_arr = (ArrayList <Integer>)inn.readObject();
                        bold_arr = (ArrayList <Integer>)inn.readObject();
                        under_arr = (ArrayList <Integer>)inn.readObject();



                    }
                    catch (ClassNotFoundException e) {
                        System.out.println("Error");

                    };
// Закрываем соединение


                    socket.close();
// Выделяем IP-адрес
                     String address =
                            ((InetSocketAddress) socket
                                    .getRemoteSocketAddress())
                                    .getAddress()
                                    .getHostAddress();
// Выводим сообщение в текстовую область

                    Thread.sleep(25);
                    SimpleAttributeSet att_default = new SimpleAttributeSet();

                    textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getStyledDocument().getLength(),
                              senderName +
                                    " (" + address + "): " , att_default);

                    Integer end = textAreaIncoming.getStyledDocument().getLength();

                    textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getStyledDocument().getLength(),
                             message + "\n" , att_default);



                        SimpleAttributeSet att = new SimpleAttributeSet();
                        StyleConstants.setItalic(att, true);

                    textAreaIncoming.setEditable(true);
                    for (int q = 0; q < kursive_arr.toArray().length - 1; q += 2) {
                        textAreaIncoming.select(end + kursive_arr.get(q), end+kursive_arr.get(q+1) );
                        textAreaIncoming.replaceSelection("");
                        textAreaIncoming.getStyledDocument().insertString(end + kursive_arr.get(q),
                                message.substring(kursive_arr.get(q), kursive_arr.get(q+1) ), att);



                    }
                     att = new SimpleAttributeSet();
                    StyleConstants.setBold(att, true);

                    for (int q = 0; q < bold_arr.toArray().length - 1; q += 2) {

                        textAreaIncoming.select((int)(end + bold_arr.get(q)), (int)(end+bold_arr.get(q+1)) );
                        textAreaIncoming.replaceSelection("");
                        textAreaIncoming.getStyledDocument().insertString((int)(end + bold_arr.get(q)),
                                message.substring(bold_arr.get(q), bold_arr.get(q+1) ), att);



                    }
                    att = new SimpleAttributeSet();
                    StyleConstants.setUnderline(att, true);
                    for (int y = 0; y < under_arr.toArray().length - 1; y += 2) {

                        textAreaIncoming.select((int)(end + under_arr.get(y)), (int)(end+under_arr.get(y+1)) );
                        textAreaIncoming.replaceSelection("");
                        textAreaIncoming.getStyledDocument().insertString((int)(end + under_arr.get(y)),
                                message.substring(under_arr.get(y), under_arr.get(y+1) ), att);



                    }
                    textAreaIncoming.setEditable(false);

                }



            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(Main.this,
                        "Ошибка в работе сервера", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);

            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(Main.this,
                        "Ошибка в работе сервера", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);


            }
            catch (InterruptedException e)
            {


            }
        }
    }).start();
}


    private void sendMessage() {
        try {
// Получаем необходимые параметры

            final String senderName = textFieldFrom.getText();
            final String destinationAddress = textFieldTo.getText();
             String message = textAreaOutgoing.getText();
// Убеждаемся, что поля не пустые
            if (senderName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите имя отправителя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (destinationAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите адрес узла-получателя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите текст сообщения", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
// Создаем сокет для соединения
            final Socket socket =
                    new Socket(destinationAddress, SERVER_PORT);
// Открываем поток вывода данных
            final DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());
// Записываем в поток имя
            out.writeUTF(senderName);
// Записываем в поток сообщение
            out.writeUTF(message);

            final ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());


          oout.writeObject(kursive_array);
          oout.writeObject(bold_array);
          oout.writeObject(under_array);

// Закрываем сокет
            socket.close();

// Помещаем сообщения в текстовую область вывода

            SimpleAttributeSet default0 = new SimpleAttributeSet();

            textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getStyledDocument().getLength(),
                    "\n"+"Я -> " + destinationAddress + ": " , default0);
            Integer end = textAreaIncoming.getStyledDocument().getLength();
            textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getStyledDocument().getLength(),
                    message + "\n"  , default0 );


            SimpleAttributeSet att = new SimpleAttributeSet();
            StyleConstants.setItalic(att, true);
            textAreaIncoming.setEditable(true);
            for (int q = 0; q < kursive_array.toArray().length - 1; q += 2) {
                textAreaIncoming.select(end + kursive_array.get(q), end+kursive_array.get(q+1) );
                textAreaIncoming.replaceSelection("");
                textAreaIncoming.getStyledDocument().insertString(end + kursive_array.get(q),
                        message.substring(kursive_array.get(q), kursive_array.get(q+1) ), att);



            }

            att = new SimpleAttributeSet();
            StyleConstants.setBold(att, true);
            for (int i = 0; i < bold_array.toArray().length - 1; i += 2) {
                textAreaIncoming.select(end + bold_array.get(i), end+bold_array.get(i+1) );
                textAreaIncoming.replaceSelection("");
                textAreaIncoming.getStyledDocument().insertString(end + bold_array.get(i),
                        message.substring(bold_array.get(i), bold_array.get(i+1) ), att);



            }
            att = new SimpleAttributeSet();
            StyleConstants.setUnderline(att, true);
            for (int y = 0; y < under_array.toArray().length - 1; y += 2) {

                textAreaIncoming.select((int)(end + under_array.get(y)), (int)(end+under_array.get(y+1)) );
                textAreaIncoming.replaceSelection("");
                textAreaIncoming.getStyledDocument().insertString((int)(end + under_array.get(y)),
                        message.substring(under_array.get(y), under_array.get(y+1) ), att);



            }

            textAreaIncoming.setEditable(false);




// Очищаем текстовую область ввода сообщения

            /*JTextPane a = new JTextPane();
            SimpleAttributeSet att1 = new SimpleAttributeSet(); ;
            StyleConstants.setItalic(att1, false);
            StyleConstants.setBold(att1, false);

            a.getStyledDocument().insertString(0,
                    "", att1);
            */


        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main.this,
                    "Не удалось отправить сообщение: узел-адресат не найден",

                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main.this,
                    "Не удалось отправить сообщение", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main.this,
                    "Не удалось отправить сообщение", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);

        }

    }

    public static void main(String[] args) {
       Main frame = new Main();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);

    }
}
