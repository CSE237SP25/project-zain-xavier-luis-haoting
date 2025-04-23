package bankapp;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Menu {

    private final Scanner keyboardInput;
    private final Bank accounts;
    private User currentUser;

    public Menu() {
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new Bank();
    }

    public boolean handleLogin() {
        System.out.println("\nWelcome to ChaChing! Please log in or register.\n");

        while (currentUser == null) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Selection: ");
            String choice = keyboardInput.nextLine();

            switch (choice) {
                case "1" -> registerUser();
                case "2" -> loginUser();
                case "3" -> {
                    return false;
                }
                default -> System.out.println("\nInvalid choice. Please try again.\n");
            }
        }

        System.out.println("\nWelcome, " + currentUser.getUsername() + "!\n");
        return true;
    }

    private void registerUser() {
        System.out.print("\nChoose a username: ");
        String username = keyboardInput.nextLine();
        System.out.print("Choose a password: ");
        String password = keyboardInput.nextLine();

        accounts.registerUser(username, password);
        this.currentUser = accounts.getUser(username);
        System.out.println();
    }

    private void loginUser() {
        System.out.print("\nUsername: ");
        String username = keyboardInput.nextLine();
        System.out.print("Password: ");
        String password = keyboardInput.nextLine();

        try {
            currentUser = accounts.login(username, password);
            if (currentUser == null) {
                System.out.println("\nLogin failed. Try again.\n");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("\nError: Unable to process login.\n");
        }
    }

    public void displayOptions() {
        System.out.println("Please select an option and an amount to deposit/withdraw:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check current balance");
        System.out.println("4. Review transaction log");
        System.out.println("5. Logout");
    }

    public double getUserInput() {
        while (!keyboardInput.hasNextDouble()) {
            System.out.println("\nInvalid input. Please enter a valid number.\n");
            keyboardInput.next();
        }
        double input = keyboardInput.nextDouble();
        keyboardInput.nextLine(); // 👈 Clear the newline left behind
        return input;
    }

    public boolean processUserInput(double selection, double amount) {
        BankAccount theAccount = currentUser.getCurrentAccount();

        if (selection == 1) {
            if (amount <= 0) {
                System.out.println("\nDeposit amount must be greater than zero.\n");
            } else {
                theAccount.deposit(amount);
                System.out.println("\nDeposited $" + amount);
                System.out.println("Current Balance: $" + theAccount.getCurrentBalance() + "\n");
            }
        } else if (selection == 2) {
            if (amount <= 0) {
                System.out.println("\nWithdrawal amount must be greater than zero.\n");
            } else if (amount > theAccount.getCurrentBalance()) {
                System.out.println("\nInsufficient balance.\n");
            } else {
                theAccount.withdraw(amount);
                System.out.println("\nWithdrew $" + amount);
                System.out.println("Current Balance: $" + theAccount.getCurrentBalance() + "\n");
            }
        } else if (selection == 3) {
            System.out.println("\nCurrent Balance: $" + theAccount.getCurrentBalance() + "\n");
        } else if (selection == 4) {
            System.out.println();
            theAccount.printTransactions();
            System.out.println();
        } else if (selection == 5) {
            System.out.println("\nLogging out...\n");
            currentUser = null; // trigger re-login
            return false;
        } else {
            System.out.println("\nInvalid selection. Please choose a valid option.\n");
        }

        return true;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void close() {
        if (keyboardInput != null) {
            keyboardInput.close();
        }
    }
    
    public Bank getAccounts() {
        return accounts;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}

    
