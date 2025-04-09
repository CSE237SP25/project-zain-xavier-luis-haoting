package bankapp;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an administrator in the banking system.
 * Admins have access to all accounts and elevated privileges.
 */
public class Admin extends User {

    /**
     * Private constructor to prevent direct creation of Admin objects.
     * Use {@link #createAdmin} to create an admin via authorization.
     *
     * @param username The admin's username.
     * @param password The admin's password.
     * @throws NoSuchAlgorithmException If SHA-512 is not available.
     */
    public Admin(String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
    }

    /**
     * Indicates whether this user is an admin.
     *
     * @return true, as this is an Admin instance.
     */
    @Override
    public boolean isAdmin() {
        return true;
    }

    /**
     * Creates a new admin user, restricted to requests from existing admins.
     * 
     * @param requestingUser The user requesting to create an admin.
     * @param username       The new admin's username.
     * @param password       The new admin's password.
     * @return A new Admin instance if the requester is authorized; otherwise, null.
     * @throws NoSuchAlgorithmException If password hashing fails.
     */
    public static Admin createAdmin(User requestingUser, String username, String password)
            throws NoSuchAlgorithmException {
        boolean isUsernameNull = (username == null);
        boolean isPasswordNull = (password == null);
        boolean isRequestingUserNull = (requestingUser == null);

        if (isUsernameNull || isPasswordNull || isRequestingUserNull) {
            return null;
        }

        boolean isUsernameEmpty = username.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();
        if (isUsernameEmpty || isPasswordEmpty) {
            return null;
        }

        boolean isRequestingUserAnAdmin = requestingUser.isAdmin();

        if (isRequestingUserAnAdmin) {
            return new Admin(username, password);
        }
        return null;
    }

    /**
     * Retrieves a list of all transactions from all users' bank accounts.
     * This method assumes that only admins can call it, and does not recheck privileges.
     * 
     * @param bank The Bank instance containing all registered users.
     * @return A list of all transactions across every user's bank account;
     *         returns an empty list if the bank is null or contains no users.
     */
    public List<Transaction> getAllTransactions(Bank bank) {
        List<Transaction> allTransactions = new ArrayList<>();

        if (bank != null) {
            for (User user : bank) {
                BankAccount account = user.getAccount();
                System.out.println("The current user in getAllTransactions is: " + account.toString());
                if (account != null) {
                	System.out.println("The TransactionLog is: " + account.getTransactionLog());
                    allTransactions.addAll(account.getTransactionLog());
                }
            }
        }

        return allTransactions;
    }
}
