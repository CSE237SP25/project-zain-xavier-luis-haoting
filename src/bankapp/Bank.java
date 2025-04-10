package bankapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.System;
import java.security.NoSuchAlgorithmException;

/**
 * Manages user accounts in the banking application. Provides functionality for
 * user registration and authentication.
 */
public class Bank implements Iterable<User>{
	/**
	 * A map storing registered users, where the key is the username and the value
	 * is the User object.
	 */
	private Map<String, User> users;

	/**
	 * Constructs an Accounts manager with an empty user database.
	 */
	public Bank() {
		this.users = new HashMap<>();
	}

	/**
	 * Registers a new user with a given username and password.
	 *
	 * @param username The username for the new account.
	 * @param password The password for the new account.
	 * @return true if the user was successfully registered, false if the username
	 *         is already taken.
	 */
	public boolean registerUser(String username, String password) {

		if(username == null || username.isEmpty()) {
			System.out.println("Username should be provided");
			return false;
		}
		if (password == null || password.isEmpty()) {
			System.out.println("Password should be provided.");
			return false;
		}
		if (users.containsKey(username)) {
			System.out.println("User: " + username + " already exists in the database.");
			return false;
		}
		try {
			users.put(username, new User(username, password));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("User: " + username + " successfully added.");
		return true;
	}

	/**
	 * Registers a new user with a user object.
	 *
	 * @param user the user object that contains the password and username in string
	 * @return true if the user was successfully registered, false if the username
	 *         is already taken.
	 */
	public boolean registerUser(User user) {
		if(user == null) {
			System.out.println("User provided cannot be null");
			return false;
		}
		
		String username = user.getUsername();
		String password = user.getPasswordHash();

		if(username == null || username.isEmpty()) {
			System.out.println("Username should be provided");
			return false;
		}
		if (password == null || password.isEmpty()) {
			System.out.println("Password should be provided.");
			return false;
		}
		if (users.containsKey(username)) {
			System.out.println("User: " + username + " already exists in the database.");
			return false;
		}
		try {
			users.put(username, new User(username, password));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("User: " + username + " successfully added.");
		return true;
	}

	/**
	 * Attempts to log in a user with the provided username and password.
	 * 
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return The User object if login is successful.
	 * @throws NoSuchAlgorithmException 
	 */
	public User login(String username, String password) throws NoSuchAlgorithmException {
		User user = users.get(username);
		if (user == null) {
			return null;
		}
		if (!user.isPasswordCorrect(password)) {
			return null;
		}
		return user;
	}

	/**
	 * Provides an iterator for the Bank class in which it iterates through each user in the hashmap.
	 * @param none
	 * @return The iterator given by the hashmap's values
	 * */
	@Override
	public Iterator<User> iterator() {
		return this.users.values().iterator();
	}
	
	/**
	 * 
	 * Provides the entire set of users only to administrators
	 * 
	 * @param user the user attempting to gain access to all accounts in the bank
	 * @return a reference to the all the users in the bank; if not an administrator it returns null
	 * 
	 * */
	public List<User> getAllUsersIfAdmin(User user) {
	    if (user.isAdmin()) {
	        return new ArrayList<>(users.values());
	    }
	    return null;
	}

}
