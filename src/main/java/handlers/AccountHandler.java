package handlers;

import models.Account;

import javax.swing.*;

import static models.AccountType.User;

public class AccountHandler {

    AccountFileHandler accountFileHandler = new AccountFileHandler();

    public boolean register(String username, String password) {

        JTabbedPane tabbedPane = MemoryHandler.quizGUI.tabbedPane;

        String dialog = "";

        boolean r = false;

        if(!username.matches("^(?=[a-zA-Z0-9._]{6,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) {
            dialog = "Illegal username. Username must be 6-20 characters long and can only contain A-Z and 0-9.";
        }
        else if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,24}$")) {
            dialog = "Illegal password. Password must be 8-24 characters long and contain at least one letter and one number.";
        }
        else if(accountFileHandler.usernameExists(username)) {
            dialog = "Username already taken, chose another username.";
        }
        else {
            dialog = "Successfully registered. You can now login.";
            r = true;

            Account account = new Account(username, password, User, 0, 0);
            accountFileHandler.accounts.add(account);
        }

        JOptionPane.showMessageDialog(tabbedPane, dialog);

        return r;
    }

    public void login(Account account) {
        MemoryHandler.account = account;
    }

    public Account getAccount(String username, String password) {
        return accountFileHandler.getAccount(username, password);
    }

}
