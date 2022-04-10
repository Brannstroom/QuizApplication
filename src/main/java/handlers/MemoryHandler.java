package handlers;

import gui.QuizGUI;
import models.Account;

import javax.swing.*;
import java.io.File;

public class MemoryHandler {

    public static QuizGUI quizGUI;

    public static JTextArea[] textAreas;
    public static JLabel[] labels;
    public static JTextArea questionTextArea;

    public static Account account;

    public static File accountsFile = new File("src/main/resources/accounts.json");

}
