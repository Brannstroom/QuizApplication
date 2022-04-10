package models;

public class Account {

    private String username;

    private String password;

    private AccountType accountType;

    private int answered;

    private int answeredCorrect;

    public Account() {
    }

    public Account(String username, String password, AccountType accountType, int answered, int answeredCorrect) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.answered = answered;
        this.answeredCorrect = answeredCorrect;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public int getAnsweredCorrect() {
        return answeredCorrect;
    }

    public void setAnsweredCorrect(int answeredCorrect) {
        this.answeredCorrect = answeredCorrect;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public String serialize() {
        return this.getUsername() + "," + this.getPassword() + "," + this.getAccountType() + "," + this.getAnswered() + "," + this.getAnsweredCorrect();
    }

    public static Account deserialize(String s) {
        String[] strings = s.split(",");
        return new Account(strings[0], strings[1], AccountType.valueOf(strings[2]), Integer.valueOf(strings[3]), Integer.valueOf(strings[4]));
    }
}
