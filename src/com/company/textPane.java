package com.company;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class textPane  {

    public JTextPane textEditor = null;
    // Стили редактора
    private Style italic    = null;
    private  final  String      STYLE_italic = "heading",
            STYLE_normal  = "normal" ,
            FONT_style    = "Times New Roman";

    public textPane()
    {
        textEditor = new JTextPane();
        createStyles();



    }
    public void changeText (String text)
    {

        changeDocumentStyle();


    }
    private void createStyles()
    {
        // Создание стилей
        italic = textEditor.addStyle(STYLE_italic, null);
        StyleConstants.setFontFamily(italic, FONT_style);
        StyleConstants.setFontSize(italic, 14);
        // Наследуем свойстdо FontFamily
    }
    public void loadText(String text) {

        Style style = italic;
        insertText(textEditor, text, style);
        StyledDocument doc = textEditor.getStyledDocument();
        textEditor.setCaretPosition(doc.getLength());

    }

    private void changeDocumentStyle()
    {
        // Изменение стиля части текста
        SimpleAttributeSet blue = new SimpleAttributeSet();
        StyleConstants.setForeground(blue, Color.BLUE);
        StyledDocument doc = textEditor.getStyledDocument();
        doc.setCharacterAttributes(10, 9, blue, false);
    }

    private void insertText(JTextPane editor, String string,
                            Style style)
    {
        try {
            Document doc = editor.getDocument();
            doc.insertString(doc.getLength(), string, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
