package bankapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Represents a user in the banking application. 
 * Each user has a username, hashed password with salt, and multiple bank accounts.
 */
public class User {

    /** The username of the user. */
    private String username;

    /** The hashed password of the user. */
    private String passwordHash;

    /** The salt used for hashing the user's password. */
    private final String hashSalt;

    /** A map of bank accounts owned by this user, identified by their UUIDs. */
    private Map<UUID, BankAccount> accounts;

    /** The UUID of the currently active account. */
    private UUID currentAccountId;

    /**
     * Constructs a new user with a username and password.
     * A single checkings account is created by default.
     *
     * @param username The username of the user.
     * @param password The raw password to be hashed.
     * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
     */
    public User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.hashSalt = generateSalt();
        this.passwordHash = hashPassword(password, hashSalt);
        this.accounts = new HashMap<>();

        BankAccount defaultAccount = new CheckingsAccount();
        this.accounts.put(defaultAccount.getId(), defaultAccount);
        this.currentAccountId = defaultAccount.getId();
    }
    
    /**
	 * Constructs a new user with a username and password.
	 * The password is securely hashed using SHA-512 with a 
	 * generated salt.
	 * In addition, if specified savings account, it creates a 
	 * savings account. Otherwise, it creates the account with a
	 * checkings account. 
	 * 
	 * @param username The username of the user.
	 * @param password The raw password to be hashed.
	 * @param savingsAccount indicating if user wants to create account with savings account, which can be passed with a true savings account.
	 * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
	 * 
	 * */
	public User(String username, String password, SavingsAccount savingsAccount) throws NoSuchAlgorithmException {
		this.username = username;
		this.hashSalt = generateSalt();
		this.passwordHash = hashPassword(password, hashSalt);
		this.accounts.put(savingsAccount.getId(), savingsAccount);
		this.currentAccountId = savingsAccount.getId();
	}

    /**
     * Adds a new bank account to the user's account list.
     * 
     * @param account The account to add.
     */
    public void addAccount(BankAccount account) {
        accounts.put(account.getId(), account);
    }

    /**
     * Removes a bank account by its ID. If it is the current account,
     * the current account will be reset to another available one.
     * 
     * @param accountId The UUID of the account to remove.
     */
    public void removeAccount(UUID accountId) {
        if (accounts.containsKey(accountId)) {
            accounts.remove(accountId);
            if (accountId.equals(currentAccountId)) {
                currentAccountId = accounts.keySet().stream().findFirst().orElse(null);
            }
        }
    }

    /**
     * Switches the user's active account to a different one using the UUID.
     * 
     * @param accountId The UUID of the account to switch to.
     * @throws IllegalArgumentException If the account does not exist.
     */
    public void switchToAccount(UUID accountId) throws IllegalArgumentException{
        if (accounts.containsKey(accountId)) {
            currentAccountId = accountId;
            return;
        } 

        throw new IllegalArgumentException("Account ID not found.");
    }
    

    /**
     * Retrieves the currently active bank account.
     *
     * @return The current BankAccount object.
     */
    public BankAccount getCurrentAccount() {
        return accounts.get(currentAccountId);
    }

    /**
     * Retrieves all accounts owned by the user.
     * 
     * @return A list of all BankAccount objects owned by the user.
     */
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * Retrieves the user's username.
     * 
     * @return The username as a string.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the hashSalt that was made for the user.
     * 
     * @return The user's hashSalt for their password.
     */
    public String getHashSalt() {
        return this.hashSalt;
    }

    /**
     * Retrieves the hashed password of the user.
     * 
     * @return The user's hashed password.
     */
    public String getPasswordHash() {
        return this.passwordHash;
    }
    
    /**
     * Retrieves unhashed password of the user only if the admin requested it.
     * 
     * @returns password of current user if the requester is an admin
     * @throws IllegalAccessException
     * */

    /**
     * Verifies if the given password matches the stored hashed password.
     *
     * @param inputPassword The password entered for verification.
     * @return true if the input password is correct, false otherwise.
     * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
     */
    public boolean isPasswordCorrect(String inputPassword) throws NoSuchAlgorithmException {
        return this.passwordHash.equals(hashPassword(inputPassword, this.hashSalt));
    }

    /**
     * Verifies if the user is an administrator in the Bank.
     * 
     * @return false if the user is not an administrator; for regular users it should return false.
     */
    public boolean isAdmin() {
        return false;
    }

    /**
     * Hashes a given password using SHA-512 and a provided salt.
     *
     * @param password The password to be hashed.
     * @param salt     The salt used for hashing.
     * @return The Base64-encoded hashed password.
     * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
     */
    private static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    /**
     * Generates a secure random salt for password hashing.
     *
     * @return A Base64-encoded random salt.
     */
    private static String generateSalt() {
        byte[] saltBytes = new byte[16]; // Can be extracted to an enum or constant
        SecureRandom random = new SecureRandom();
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
