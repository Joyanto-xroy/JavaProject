import java.io.Serializable;

public class WalletAccount implements Serializable {
    private String name;
    private String walletID;
    private String phoneNumber;
    private String password;
    private double balance;
    private String[] transactionHistory;
    private int nextTransactionIndex;

    private static final int MAX_TRANSACTIONS = 10;

    public WalletAccount(String name, String walletID, String phoneNumber, String password) {
        this.name = name;
        this.walletID = walletID;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.nextTransactionIndex = 0;
    }

    public String getName() {
        return name;
    }

    public String getWalletID() {
        return walletID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean authenticate(String phoneNumber, String password) {
        return this.phoneNumber.equals(phoneNumber) && this.password.equals(password);
    }

    public void addFunds(double amount) {
        balance += amount;
        addTransaction("Added Funds: " + amount);
        System.out.println("Funds Added: " + amount);
        System.out.println("Current Balance: " + balance);
        printSeparator();
    }

    public void makePayment(double amount, String recipientName, String recipientPhone, String recipientWalletID) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction("Payment to " + recipientName + " (Phone: " + recipientPhone + ", WalletID: " + recipientWalletID + "): " + amount);
            System.out.println("Payment Made to " + recipientName + " (Phone: " + recipientPhone + ", WalletID: " + recipientWalletID + "): " + amount);
            System.out.println("Current Balance: " + balance);
        } else {
            System.out.println("Insufficient Balance");
        }
        printSeparator();
    }

    public void displayBalance() {
        System.out.println("Name: " + name);
        System.out.println("Wallet ID: " + walletID);
        System.out.println("Balance: " + balance);
        printSeparator();
    }

    private void addTransaction(String transaction) {
        if (nextTransactionIndex >= MAX_TRANSACTIONS) {
            nextTransactionIndex = 0;
        }
        transactionHistory[nextTransactionIndex] = transaction;
        nextTransactionIndex++;
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History for Wallet: " + walletID);
        boolean hasTransactions = false;

        for (int i = 0; i < MAX_TRANSACTIONS; i++) {
            if (transactionHistory[i] != null) {
                System.out.println(transactionHistory[i]);
                hasTransactions = true;
            }
        }

        if (!hasTransactions) {
            System.out.println("No transactions available.");
        }
        printSeparator();
    }

    private void printSeparator() {
        for (int i = 0; i < 20; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public double getBalance() {
        return balance;
    }
}