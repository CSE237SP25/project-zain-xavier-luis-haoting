package bankapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Represents a user in the banking application. Each user has a username, a
 * hashed password with a salt, and an associated bank account.
 */
public class User {
	/** The username of the user. */
	private String username;

	/** The hashed password of the user. */
	private String passwordHash;

	/** The salt used for hashing the user's password. */
	private final String hashSalt;

	/** The bank account associated with the user. */
	private BankAccount account;

	/**
	 * Constructs a new user with a username and password. The password is securely
	 * hashed using SHA-512 with a generated salt.
	 * By default it constructs it using a checkings account.
	 *
	 * @param username The username of the user.
	 * @param password The raw password to be hashed.
	 * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
	 */
	public User(String username, String password) throws NoSuchAlgorithmException {
		this.username = username;
		this.hashSalt = generateSalt();
		this.passwordHash = hashPassword(password, hashSalt);
		this.account = new CheckingsAccount();
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
	 * @param isSavingsAccount The flag indicating if user wants to create account with savings account.
	 * @throws NoSuchAlgorithmException If SHA-512 algorithm is not available.
	 * 
	 * */
	public User(String username, String password, Boolean isSavingsAccount) throws NoSuchAlgorithmException {
		this.username = username;
		this.hashSalt = generateSalt();
		this.passwordHash = hashPassword(password, hashSalt);
		if(isSavingsAccount) {
			this.account = new SavingsAccount();
			return;
		}
		this.account = new CheckingsAccount();
	}
	

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
	 * Retrieves the bank account associated with the user.
	 *
	 * @return The user's bank account.
	 */
	public BankAccount getAccount() {
		return this.account;
	}

	/**
	 * Retrieves the user's username
	 * 
	 * @return returns the username as a string
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Retrieves the hashSalt that was made for the user
	 * 
	 * @return The user's hashSalt for their password
	 */
	public String getHashSalt() {
		return this.hashSalt;
	}

	/**
	 * Retrieves the hashed password of the user
	 * 
	 * @return The user's hashed password.
	 */
	public String getPasswordHash() {
		return this.passwordHash;
	}
	
	/**
	 * Verifies if the user is an administrator in the Bank
	 * 
	 * @param none
	 * @return false if the user is not an administrator; for regular users it should return false
	 * 
	 * */
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
