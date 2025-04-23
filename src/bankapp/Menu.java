package bankapp;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Menu} class handles all user interactions in the banking application,
 * including login, registration, user banking options, and admin-specific operations.
 */
public class Menu {

    private final Scanner keyboardInput;
    private final Bank accounts;
    private User currentUser;
    private Admin Owner;

    /**
     * Constructs the main menu and initializes the bank and owner admin account.
     */
    public Menu() {
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new Bank();
        try {
            this.Owner = new Admin("owner", "verysecurePassword43");
            this.accounts.registerUser(this.Owner);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            System.out.println("FAILED TO INITIALIZE MENU, SHUTTING DOWN...");
        }
    }
    
    /**
     * Overload for constructor by passing in references for instances.
     * Easier to use for testing.
     * */
    public Menu(Bank testBank, Scanner testInput, User testUser) {
        this.keyboardInput = testInput;
        this.accounts = testBank;
        this.currentUser = testUser;
    }

    /**
     * Displays login/register options to the user and handles their selection.
     * @return the {@code User} object if login is successful; {@code null} if the user exits.
     */
    public User handleLogin() {
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
                    return null;
                }
                default -> System.out.println("\nInvalid choice. Please try again.\n");
            }
        }
        boolean selected = false;

        while (!selected) {
            System.out.println("\nWelcome, " + currentUser.getUsername() + "!\n");
            System.out.println("Please choose an option:");
            System.out.println("1. Access an existing account");
            System.out.println("2. Create a new account");
            System.out.println("3. Log out");

            System.out.print("Enter choice: ");
            int choice = getUserIntInput();

            switch (choice) {
                case 1 -> {
                    List<BankAccount> accounts = currentUser.getAllAccounts();
                    if (accounts.isEmpty()) {
                        System.out.println("\nYou have no existing accounts.\n");
                        break;
                    }

                    System.out.println("\nYour Accounts:");
                    for (int i = 0; i < accounts.size(); i++) {
                        BankAccount acc = accounts.get(i);
                        System.out.printf("%d. %s (Balance: $%.2f)\n", i + 1, acc.getNickname(), acc.getCurrentBalance());
                    }

                    System.out.print("Select an account by number: ");
                    int accIndex = getUserIntInput() - 1;

                    if (accIndex >= 0 && accIndex < accounts.size()) {
                        BankAccount selectedAcc = accounts.get(accIndex);
                        currentUser.switchToAccount(selectedAcc.getId());
                        System.out.println("\nUsing account: " + selectedAcc.getNickname() + "\n");
                        selected = true;
                    } else {
                        System.out.println("\nInvalid account selection.\n");
                    }
                }
                case 2 -> {
                    System.out.println("\nSelect type of account to create:");
                    System.out.println("1. Checkings");
                    System.out.println("2. Savings");
                    int accType = getUserIntInput();

                    BankAccount newAcc;
                    if (accType == 2) {
                        newAcc = new SavingsAccount(); // assuming this class exists
                    } else {
                        newAcc = new CheckingsAccount(); // default fallback
                    }

                    System.out.print("Enter nickname for new account: ");
                    String nickname = getUserStringInput();
                    if (!nickname.isBlank()) {
                        newAcc.setNickname(nickname);
                    }

                    currentUser.addAccount(newAcc);
                    currentUser.switchToAccount(newAcc.getId());
                    System.out.println("\nNew account created and selected: " + newAcc.getNickname() + "\n");
                    selected = true;
                }
                case 3 -> {
                    System.out.println("\nLogging out...");
                    currentUser = null;
                    return null; // or break out if your logic continues elsewhere
                }
                default -> System.out.println("\nInvalid input. Please try again.");
            }
        }
        return currentUser;
    }



    /**
     * Handles the registration of a new user.
     */
    private void registerUser() {
        System.out.print("\nChoose a username: ");
        String username = keyboardInput.nextLine();
        System.out.print("Choose a password: ");
        String password = keyboardInput.nextLine();

        accounts.registerUser(username, password);
        this.currentUser = accounts.getUser(username);
        System.out.println();
    }

    /**
     * Handles user login.
     */
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

    /**
     * Displays admin-specific options for managing the bank system.
     */
    public void displayAdminOptions() {

        while (currentUser != null && currentUser.isAdmin()) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View all users.");
            System.out.println("2. View total system balance.");
            System.out.println("3. Add a new admin.");
            System.out.println("4. Logout.");
            System.out.print("Selection: ");
            int userInput = (int) this.getUserInput();
            
            this.processAdminInput(userInput);

        }
    }
    /**
     * Processes the selected action from the admin menu.
     * @param selection admin-selected menu option
     */
    public void processAdminInput(int selection) {
        switch (selection) {
            case 1 -> {
                // View all users
                System.out.println("\nRegistered Users:");
                for (User user : accounts.getAllUsersIfAdmin(currentUser)) {
                    System.out.println("- " + user.getUsername());
                }
                System.out.println();
            }
            case 2 -> {
                // View total system balance
                try {
                    double total = accounts.calculateTotalSystemBalanceBasedOnAllUsers(currentUser);
                    System.out.println("\nTotal balance across all accounts: $" + total + "\n");
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                    System.out.println("ERROR: YOU ARE NOT AUTHORIZED TO ACCESS USER BALANCES.");
                    System.out.println("LOGGING OUT!");
                    currentUser = null; // Log out if unauthorized
                }
            }
            case 3 -> {
                // Add a new admin
				System.out.println("\nPlease provide the new admin's username: ");
				String username = getUserStringInput();
				System.out.println("\nPlease provide the new admin's password: ");
				String password = getUserStringInput();
				
				this.registerAdmin(username, password);
            }
            case 4 -> {
                // Logout
                System.out.println("\nLogging out...\n");
                currentUser = null;
            }
            default -> System.out.println("\nInvalid selection. Please choose a valid option.\n");
        }
    }
    
    /**
     * Register a new admin if possible.
     * 
     * @param username the username for the new admin
     * @param password the password for the new admin
     * @returns a boolean indicating if the registration of a new admin was successful
     * */
    public boolean registerAdmin(String username, String password) {
    	boolean wasRegistrationSuccessful = false;
		try {
			Admin potentialNewAdmin = Admin.createAdmin(currentUser, username, password);
			if(potentialNewAdmin == null) {
				System.out.println("\nFailed to create a new admin due to an internal error, please try again.\n");
				return false;
			}

			wasRegistrationSuccessful = accounts.registerUser(potentialNewAdmin);
			if(wasRegistrationSuccessful) {
				System.out.println("\nNew admin added successfully.\n");
				return wasRegistrationSuccessful;
			}
			System.out.println("\nFailed to create a new admin due to an internal error, please try again.\n");
			return wasRegistrationSuccessful;

		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
			System.out.println("\nFailed to create a new admin due to an internal error, please try again.\n");
			return wasRegistrationSuccessful;
		}
    }


    /**
     * Displays options for user account actions like deposits and withdrawals.
     */
    public void displayOptions() {
        System.out.println("Please select an option and an amount to deposit/withdraw:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check current balance");
        System.out.println("4. Review transaction log");
        System.out.println("5. Check failed withdrawal log");
        System.out.println("6. Logout");
    }

    /**
     * Gets numerical input from the user.
     * @return a valid double entered by the user
     */
    public double getUserInput() {
        while (!keyboardInput.hasNextDouble()) {
            System.out.println("\nInvalid input. Please enter a valid number.\n");
            keyboardInput.next();
        }
        double input = keyboardInput.nextDouble();
        keyboardInput.nextLine(); // Clear newline
        return input;
    }

    /**
     * Gets string input from the user.
     * @return a valid string entered by the user
     * */
    public String getUserStringInput() {
    	String input = "";
    	while(!keyboardInput.hasNextLine()) {
            System.out.println("\nInvalid input. Please enter a valid username and/or password.\n");
    	}
   		input = keyboardInput.nextLine().trim();
   		return input;
    }

    /**
     * Processes the selected action from the main user menu.
     * @param selection user-selected menu option
     * @param amount transaction amount (used if relevant to the option)
     * @return {@code true} to stay logged in, {@code false} to log out
     */
    public boolean processUserInput(double selection, double amount) {
        BankAccount theAccount = currentUser.getCurrentAccount();

        switch ((int) selection) {
            case 1 -> {
                if (amount <= 0) {
                    System.out.println("\nDeposit amount must be greater than zero.\n");
                } else {
                    theAccount.deposit(amount);
                    System.out.println("\nDeposited $" + amount);
                    System.out.println("Current Balance: $" + theAccount.getCurrentBalance() + "\n");
                }
            }
            case 2 -> {
                if (amount <= 0) {
                    System.out.println("\nWithdrawal amount must be greater than zero.\n");
                } else if (amount > theAccount.getCurrentBalance()) {
                    System.out.println("\nInsufficient balance.\n");
                    try {
                        theAccount.withdraw(amount); // Will log failure
                    } catch (IllegalArgumentException ignored) {}
                } else {
                    theAccount.withdraw(amount);
                    System.out.println("\nWithdrew $" + amount);
                    System.out.println("Current Balance: $" + theAccount.getCurrentBalance() + "\n");
                }
            }
            case 3 -> System.out.println("\nCurrent Balance: $" + theAccount.getCurrentBalance() + "\n");
            case 4 -> {
                System.out.println();
                theAccount.printTransactions();
                System.out.println();
            }
            case 5 -> {
                System.out.println();
                theAccount.printFailedTransactions();
                System.out.println();
            }
            case 6 -> {
                System.out.println("\nLogging out...\n");
                currentUser = null;
                return false;
            }
            default -> System.out.println("\nInvalid selection. Please choose a valid option.\n");
        }

        return true;
    }

    /**
     * Checks if a user is currently logged in.
     * @return {@code true} if a user is logged in; {@code false} otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Closes the scanner to free up system resources.
     */
    public void close() {
        if (keyboardInput != null) {
            keyboardInput.close();
        }
    }

    /**
     * Returns the bank object used in this menu.
     * @return the {@code Bank} instance
     */
    public Bank getAccounts() {
        return accounts;
    }

    /**
     * Gets the currently logged in user.
     * @return the current {@code User}
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged in user.
     * @param user the {@code User} to be set as current
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Prompts the user for integer input via the console and validates it.
     * If the input is not a valid integer, the method will repeatedly prompt the user 
     * until a valid integer is entered. 
     *
     * @return The valid integer input entered by the user.
     */
    public int getUserIntInput() {
        while (!keyboardInput.hasNextInt()) {
            System.out.println("\nInvalid input. Please enter a number.\n");
            keyboardInput.nextLine(); // flush
        }
        int input = keyboardInput.nextInt();
        keyboardInput.nextLine(); // clear newline
        return input;
    }

}
