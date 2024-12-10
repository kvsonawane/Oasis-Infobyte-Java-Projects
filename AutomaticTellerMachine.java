import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//ATM class
class ATM {
    private HashMap<String, User> users;
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        users = new HashMap<>();
        scanner = new Scanner(System.in);
        // Initialize with some sample users
        users.put("Melvin23", new User("Melvin23", "1234", new Account("123456", 1000.0)));
        users.put("789012", new User("789012", "5678", new Account("789012", 2000.0)));
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                authenticate();
            } else {
                showMenu();
            }
        }
    }

    private void authenticate() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();


        User user = users.get(userId);
        if (user != null && user.authenticate(pin)) {
            currentUser = user;
            System.out.println("Authentication successful. Welcome, " + userId + "!");
        } else {
            System.out.println("Invalid User ID or PIN. Please try again.");
        }
    }

    private void showMenu() {
        System.out.println("\n==== ATM Menu ====");
        System.out.println("1. View Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                showTransactionHistory();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                deposit();
                break;
            case 4:
                transfer();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void showTransactionHistory() {
        System.out.println("\n==== Transaction History ====");
        for (Transaction transaction : currentUser.getAccount().getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (currentUser.getAccount().withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: Rs" + currentUser.getAccount().getBalance());
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (currentUser.getAccount().deposit(amount)) {
            System.out.println("Deposit successful. New balance: Rs" + currentUser.getAccount().getBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private void transfer() {
        System.out.print("Enter recipient's User ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        User recipient = users.get(recipientId);
        if (recipient != null) {
            if (currentUser.getAccount().transfer(recipient.getAccount(), amount)) {
                System.out.println("Transfer successful. New balance: Rs" + currentUser.getAccount().getBalance());
            } else {
                System.out.println("Insufficient funds or invalid amount.");
            }
        } else {
            System.out.println("Recipient not found.");
        }
    }

    private void logout() {
        System.out.println("Thank you for using our ATM. Goodbye!");
        currentUser = null;
    }
}

//User class
class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, Account account) {
        this.userId = userId;
        this.pin = pin;
        this.account = account;
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public Account getAccount() {
        return account;
    }
}

//Account class
class Account {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", -amount));
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            return true;
        }
        return false;
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getAccountNumber(), -amount));
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

//Transaction class
class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s - %s: Rs%.2f", timestamp.format(formatter), type, amount);
    }
}

//Main class
public class AutomaticTellerMachine {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
