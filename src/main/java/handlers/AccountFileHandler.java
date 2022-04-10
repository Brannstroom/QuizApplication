package handlers;

import com.google.gson.Gson;
import models.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountFileHandler {

    public static List<Account> accounts = new ArrayList<>();

    public boolean usernameExists(String username) {
        for(Account account : accounts) {
            if(account.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public Account getAccount(String username, String password) {
        for(Account account : accounts) {
            if(account.getUsername().equalsIgnoreCase(username) && account.getPassword().equals(password)) return account;
        }
        return null;
    }

    public void saveAccountsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(MemoryHandler.accountsFile)))) {
            Gson gson = new Gson();
            for (Account account : accounts) {
                bufferedWriter.write(gson.toJson(account));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pullAccountsFromFile() {
        ArrayList<Account> tempAccounts = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MemoryHandler.accountsFile))) {
            String line;
            Gson gson = new Gson();
            while((line = bufferedReader.readLine()) != null) {
                Account account = gson.fromJson(line, Account.class);
                tempAccounts.add(account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        accounts = tempAccounts;
    }

}
