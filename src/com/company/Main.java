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
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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


                    StyleConstants.setItalic(att, true);
                    pane.setCharacterAttributes(att, true);
                    String a = textAreaOutgoing.getText();

                    pane.getStyledDocument().insertString(textAreaOutgoing.getSelectionStart(),
                            textAreaOutgoing.getText(textAreaOutgoing.getSelectionStart(), textAreaOutgoing.getSelectionEnd()
                                    - textAreaOutgoing.getSelectionStart()), att);
                    textAreaOutgoing.replaceSelection("");
                    textAreaOutgoing.setDocument(pane.getStyledDocument());



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


                        StyleConstants.setBold(att, true);
                        pane.setCharacterAttributes(att, true);
                        String a = textAreaOutgoing.getText();

                        pane.getStyledDocument().insertString(textAreaOutgoing.getSelectionStart(),
                                textAreaOutgoing.getText(textAreaOutgoing.getSelectionStart(), textAreaOutgoing.getSelectionEnd()
                                        - textAreaOutgoing.getSelectionStart()), att);
                        textAreaOutgoing.replaceSelection("");
                        textAreaOutgoing.setDocument(pane.getStyledDocument());



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
                Document object = new Document() {
                    @Override
                    public int getLength() {
                        return 0;
                    }

                    @Override
                    public void addDocumentListener(DocumentListener listener) {

                    }

                    @Override
                    public void removeDocumentListener(DocumentListener listener) {

                    }

                    @Override
                    public void addUndoableEditListener(UndoableEditListener listener) {

                    }

                    @Override
                    public void removeUndoableEditListener(UndoableEditListener listener) {

                    }

                    @Override
                    public Object getProperty(Object key) {
                        return null;
                    }

                    @Override
                    public void putProperty(Object key, Object value) {

                    }

                    @Override
                    public void remove(int offs, int len) throws BadLocationException {

                    }

                    @Override
                    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

                    }

                    @Override
                    public String getText(int offset, int length) throws BadLocationException {
                        return null;
                    }

                    @Override
                    public void getText(int offset, int length, Segment txt) throws BadLocationException {

                    }

                    @Override
                    public Position getStartPosition() {
                        return null;
                    }

                    @Override
                    public Position getEndPosition() {
                        return null;
                    }

                    @Override
                    public Position createPosition(int offs) throws BadLocationException {
                        return null;
                    }

                    @Override
                    public Element[] getRootElements() {
                        return new Element[0];
                    }

                    @Override
                    public Element getDefaultRootElement() {
                        return null;
                    }

                    @Override
                    public void render(Runnable r) {

                    }
                };
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
                      object = (StyledDocument) inn.readObject();}
                    catch (ClassNotFoundException e) {


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


                    textAreaincoming.append(   senderName +
                            " (" + address + "): " +
                            message + "\n");
                    JTextPane pane = new JTextPane();


                   AttributeSet att =  pane.getCharacterAttributes();

                    textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getDocument().getLength(),
                            senderName +
                                    " (" + address + "): " +
                                    message + "\n"  , att );


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
        }
    }).start();
}


    private void sendMessage() {
        try {
// Получаем необходимые параметры

            final String senderName = textFieldFrom.getText();
            final String destinationAddress = textFieldTo.getText();
            final String message = textAreaOutgoing.getText();
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
            oout.writeObject(textAreaOutgoing.getStyledDocument());

// Закрываем сокет
            socket.close();

// Помещаем сообщения в текстовую область вывода
            JTextPane pane = new JTextPane( textAreaOutgoing.getStyledDocument());
            AttributeSet att = pane.getCharacterAttributes();

            textAreaIncoming.getStyledDocument().insertString(textAreaIncoming.getStyledDocument().getLength(),
                    "Я -> " + destinationAddress + ": "
                            + message + "\n" , att );


            textAreaincoming.append("Я -> " + destinationAddress + ": "
                    + message + "\n");


// Очищаем текстовую область ввода сообщения
            textAreaOutgoing.setText("");

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
