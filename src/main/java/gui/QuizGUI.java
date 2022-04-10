package gui;

import handlers.*;
import interfaces.PageConstants;
import models.Account;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuizGUI extends JFrame {

    private JPanel panel;

    public JTabbedPane tabbedPane;

    private JButton loginRegisterButton;
    private JPanel registerPanel;
    private JTextField registerUsername;
    private JPasswordField registerPassword;
    private JButton registerRegisterButton;
    private JButton registerBackButton;
    private JTextField loginUsername;
    private JPasswordField loginPassword;
    private JPanel loginPanel;
    private JButton loginLoginButton;
    private JPanel mainPanel;
    private JLabel mainWelcomeText;
    private JPanel questionPane;
    private JButton mainStartButton;
    private JLabel mainStatsTotal;
    private JLabel mainStatsCorrect;
    private JLabel mainStatsPercentage;
    private JButton mainLogoutButton;
    private JLabel yourPersonalStatsLabel;
    private JTextArea questionAnswer1;
    private JTextArea questionAnswer2;
    private JTextArea questionAnswer4;
    private JTextArea questionAnswer3;
    private JTextArea questionQuestion;
    private JLabel questionCategory;
    private JLabel questionQuestionCounter;
    private JSpinner mainQuestionsAmount;
    private JComboBox mainCategories;
    private JLabel questionCorrectAmount;
    private JLabel questionWrongAmount;

    AccountHandler accountHandler = new AccountHandler();
    AccountFileHandler accountFileHandler = new AccountFileHandler();

    public QuizGUI(String title) {
        super(title);

        accountFileHandler.pullAccountsFromFile();

        this.setContentPane(tabbedPane);


        MemoryHandler.labels = new JLabel[]{questionCategory, questionQuestionCounter, questionCorrectAmount, questionWrongAmount};
        MemoryHandler.textAreas = new JTextArea[]{questionAnswer1, questionAnswer2, questionAnswer3, questionAnswer4};
        MemoryHandler.questionTextArea = questionQuestion;

        settings();
        listeners();
        UI();
    }

    private void settings() {

        this.setSize(350, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                accountFileHandler.saveAccountsToFile();
                System.exit(0);
            }
        });
    }

    private void UI() {
        /* Hides the tabs part of the GUI */
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
                return 0;
            }
        });

        mainWelcomeText.setFont(new Font("Serif", Font.BOLD, 32));
        yourPersonalStatsLabel.setFont(new Font("Serif", Font.BOLD, 22));
        questionQuestion.setFont(new Font("Serif", Font.BOLD, 18));

        mainQuestionsAmount.setModel(new SpinnerNumberModel(5, 5, 30, 1));

        String[] categories = {"Any category", "General knowledge", "Books", "Film", "Music", "Musicals & Theatres", "Television", "Video games", "Board games", "Science & Nature", "Computers", "Mathematics", "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals"};
        for(String category : categories) {
            mainCategories.addItem(category);
        }


    }

    private void listeners() {

        loginPageListeners();
        registerPageListeners();
        mainPageListeners();

    }

    private void loginPageListeners() {
        loginLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsername.getText();
                String password = loginPassword.getText();

                Account account = accountHandler.getAccount(username, password);
                if(account != null) {
                    accountHandler.login(account);
                    mainWelcomeText.setText("Welcome, " + account.getUsername());
                    setLocationRelativeTo(null);

                    setPage(PageConstants.MAIN_PAGE);
                }
                else {
                    JOptionPane.showMessageDialog(tabbedPane, "Credentials does not match any account. Are you sure you have entered the correct username and password?");
                }
            }
        });

        loginRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedComponent(registerPanel);
            }
        });
    }

    private void registerPageListeners() {
        registerRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accountHandler.register(registerUsername.getText(), registerPassword.getText())) {
                    setPage(PageConstants.LOGIN_PAGE);
                }
            }
        });

        registerBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedComponent(loginPanel);
            }
        });
    }

    private void mainPageListeners() {

        mainStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                QuizHandler quizHandler = new QuizHandler(0, (Integer) mainQuestionsAmount.getValue(), mainCategories.getSelectedItem().toString());
                quizHandler.startQuiz();
                setPage(PageConstants.QUESTION_PAGE);
            }
        });

        mainLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemoryHandler.account = null;
                setPage(PageConstants.LOGIN_PAGE);
                JOptionPane.showMessageDialog(tabbedPane, "You successfully signed out.");
            }
        });
    }

    public void setPage(int pageConstant) {
        switch (pageConstant) {
            case 1:
                loginUsername.setText("");
                loginPassword.setText("");
                tabbedPane.setSelectedComponent(loginPanel);
                this.setSize(350, 200);
                break;
            case 2:
                registerUsername.setText("");
                registerPassword.setText("");
                tabbedPane.setSelectedComponent(registerPanel);
                this.setSize(350, 200);
                break;
            case 3:
                Account account = MemoryHandler.account;
                mainStatsCorrect.setText(String.valueOf(account.getAnsweredCorrect()));
                mainStatsTotal.setText(String.valueOf(account.getAnswered()));

                /* Short-term fix (which we will never look at again) to prevent dividing by zero */
                int answered = account.getAnswered() == 0 ? 1 : account.getAnswered();
                mainStatsPercentage.setText(account.getAnsweredCorrect()*100/answered + "%");

                tabbedPane.setSelectedComponent(mainPanel);
                this.setSize(800, 600);
                break;
            case 4:
                tabbedPane.setSelectedComponent(questionPane);
                this.setSize(800, 600);
                break;
        }
    }
}
