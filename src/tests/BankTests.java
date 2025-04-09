package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.User;

/**
 * Unit tests for the {@link Bank} class, which handles user registration and login.
 * 
 * These tests cover the functionality of registering users, logging in with correct and incorrect credentials,
 * and handling edge cases like empty usernames or passwords.
 */
public class BankTests {

    private Bank accounts;

    /**
     * Setup method that runs before each test case.
     * Creates a new {@link Bank} object to ensure tests are isolated from each other.
     */
    @BeforeEach
    void setup() {
        // Create a new Accounts object before each test to avoid state carry-over
        accounts = new Bank();
    }

    /**
     * Tests that a user can successfully register with a unique username and valid password.
     * 
     * Verifies that the {@link Bank#registerUser(String, String)} method correctly registers a new user.
     */
    @Test
    public void testRegisterUserSuccess() {
        // Test user registration with a new username
        boolean result = accounts.registerUser("john_doe", "securepassword123");
        assertTrue("User should be successfully registered", result);
    }

    /**
     * Tests that a user cannot register if the username already exists.
     * 
     * Verifies that the {@link Bank#registerUser(String, String)} method prevents duplicate registrations.
     */
    @Test
    public void testRegisterUserFailureUsernameExists() {
        // Register a user
        accounts.registerUser("john_doe", "securepassword123");

        // Try to register the same user again
        boolean result = accounts.registerUser("john_doe", "anotherpassword");
        assertEquals("User with the same username should not be allowed", false, result);
    }

    /**
     * Tests that a user can log in successfully with the correct username and password.
     * 
     * Verifies that the {@link Bank#login(String, String)} method allows a user to log in with correct credentials.
     */
    @Test
    public void testLoginSuccess() {
        // Register a user
        accounts.registerUser("john_doe", "securepassword123");

        // Test login with correct credentials
        try {
            User user = accounts.login("john_doe", "securepassword123");
            assertEquals("Logged in user should match the registered user", "john_doe", user.getUsername());
        } catch (Exception e) {
            fail("Login should be successful with correct credentials");
        }
    }

    /**
     * Tests that a user cannot log in with an incorrect password.
     * 
     * Verifies that the {@link Bank#login(String, String)} method returns null when the password is incorrect.
     */
    @Test
    public void testLoginFailureIncorrectPassword() {
        accounts.registerUser("john_doe", "securepassword123");

        // Attempt to log in with the wrong password
        User failedPasswordUser;
        try {
            failedPasswordUser = accounts.login("john_doe", "wrongpassword");
            // Assert that the returned user is null (indicating failure)
            assertNull(failedPasswordUser);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            fail("Should not have a NoSuchAlgorithmException occur.");
        }
    }

    /**
     * Tests that a user cannot log in if the username is not found in the system.
     * 
     * Verifies that the {@link Bank#login(String, String)} method returns null when the username is incorrect.
     */
    @Test
    public void testLoginFailureUsernameNotFound() {
        accounts.registerUser("john_doe", "securepassword123");

        User failedUsernameUser;
        try {
            failedUsernameUser = accounts.login("wrong_user", "securepassword123");
            assertNull(failedUsernameUser);
        } catch (Exception e) {
            fail("Should not have a NoSuchAlgorithmException occur.");
        }
    }

    /**
     * Tests that a user cannot register if the username is empty.
     * 
     * Verifies that the {@link Bank#registerUser(String, String)} method prevents registration with an empty username.
     */
    @Test
    public void testRegisterEmptyUsername() {
        // Attempt to register a user with an empty username
        boolean result = accounts.registerUser("", "securepassword123");
        assertFalse("User with an empty username should not be permitted to register.", result);
    }

    /**
     * Tests that a user cannot register if the password is empty.
     * 
     * Verifies that the {@link Bank#registerUser(String, String)} method prevents registration with an empty password.
     */
    @Test
    public void testRegisterEmptyPassword() {
        // Attempt to register a user with an empty password
        boolean result = accounts.registerUser("new_user", "");
        assertFalse("User with an empty password should not be allowed", result);
    }
    
    /**
     * Tests that the {@link Bank} class correctly implements {@link Iterable} and allows iteration over users.
     * 
     * Verifies that all registered users are returned when iterating over the Bank.
     */
    @Test
    public void testBankIterable() {
        // Register multiple users
        accounts.registerUser("alice", "pass1");
        accounts.registerUser("bob", "pass2");
        accounts.registerUser("carol", "pass3");

        // Store usernames from iteration
        Set<String> iteratedUsernames = new HashSet<>();
        for (User user : accounts) {
            iteratedUsernames.add(user.getUsername());
        }

        // Expected usernames
        Set<String> expectedUsernames = Set.of("alice", "bob", "carol");

        assertEquals("All registered users should be returned via iteration", expectedUsernames, iteratedUsernames);
    }

}
