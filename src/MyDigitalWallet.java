import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MyDigitalWallet {
    private static final String FILE_NAME = "walletAccounts.db";
    private static HashMap<String, WalletAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        loadAccounts();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Digital Wallet System!");
        printSeparator();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    saveAccounts();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        if (accounts.containsKey(phoneNumber)) {
            WalletAccount account = accounts.get(phoneNumber);
            if (account.authenticate(phoneNumber, password)) {
                System.out.println("Login Successful");
                printSeparator();
                displayMenu(scanner, account);
            } else {
                System.out.println("Invalid Password");
            }
        } else {
            System.out.println("Account not found");
        }
        printSeparator();
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.next();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();
        System.out.print("Enter Wallet ID: ");
        String walletID = scanner.next();

        if (!accounts.containsKey(phoneNumber)) {
            WalletAccount account = new WalletAccount(name, walletID, phoneNumber, password);
            accounts.put(phoneNumber, account);
            System.out.println("Registration Successful");
            saveAccounts();
        } else {
            System.out.println("Account already exists");
        }
        printSeparator();
    }

    private static void displayMenu(Scanner scanner, WalletAccount account) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Funds");
            System.out.println("2. Make Payment");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to add: ");
                    double addAmount = scanner.nextDouble();
                    account.addFunds(addAmount);
                    break;
                case 2:
                    makePayment(scanner, account);
                    break;
                case 3:
                    account.displayBalance();
                    break;
                case 4:
                    account.displayTransactionHistory();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void makePayment(Scanner scanner, WalletAccount account) {
        System.out.print("Enter Recipient's Name: ");
        String recipientName = scanner.next();
        System.out.print("Enter Recipient's Phone Number: ");
        String recipientPhone = scanner.next();
        System.out.print("Enter Recipient's Wallet ID: ");
        String recipientWalletID = scanner.next();
        System.out.print("Enter Amount to Pay: ");
        double amountToPay = scanner.nextDouble();

        System.out.print("Enter your Password to Confirm Payment: ");
        String enteredPassword = scanner.next();

        if (account.authenticate(account.getPhoneNumber(), enteredPassword)) {
            account.makePayment(amountToPay, recipientName, recipientPhone, recipientWalletID);
        } else {
            System.out.println("Incorrect Password. Payment Canceled.");
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
        printSeparator();
    }

    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (HashMap<String, WalletAccount>) ois.readObject();
        } catch (FileNotFoundException e) {
            
        } catch (IOException | ClassNotFoundException e) {
            
        }
        printSeparator();
    }

    private static void printSeparator() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}