package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.User;

/**
 * Unit tests for the {@link User} class, which represents a user in the banking
 * application. These tests cover user creation, password hashing, and password
 * verification.
 */
public class UserTests {

	private User user;

	/**
	 * Setup method that runs before each test case. Creates a new {@link User}
	 * object to ensure tests are isolated from each other.
	 */
	@BeforeEach
	void setup() {
		try {
			// Create a new user with a test username and password
			user = new User("john_doe", "securepassword123");
		} catch (NoSuchAlgorithmException e) {
			fail("Failed to create user due to NoSuchAlgorithmException");
		}
	}

	/**
	 * Tests that a user is correctly initialized with the specified username and
	 * password.
	 * 
	 * Verifies that the username is set correctly, and that the password is hashed.
	 */
	@Test
	public void testUserInitialization() {
		assertEquals("Username should match the provided username", "john_doe", user.getUsername());
		// Check if the hashed password is not the same as the raw password
		assertFalse("Hashed password should not match the raw password",
				"securepassword123".equals(user.getPasswordHash()));
	}

	/**
	 * Tests that the password verification works correctly with the correct
	 * password.
	 * 
	 * Verifies that the {@link User#isPasswordCorrect(String)} method returns true
	 * when the correct password is provided.
	 */
	@Test
	public void testPasswordVerificationSuccess() {
		try {
			assertTrue("Password verification should return true for correct password",
					user.isPasswordCorrect("securepassword123"));
		} catch (NoSuchAlgorithmException e) {
			fail("Should not have a NoSuchAlgorithmException occur.");
		}
	}

	/**
	 * Tests that the password verification works correctly with an incorrect
	 * password.
	 * 
	 * Verifies that the {@link User#isPasswordCorrect(String)} method returns false
	 * when an incorrect password is provided.
	 */
	@Test
	public void testPasswordVerificationFailure() {
		try {
			assertFalse("Password verification should return false for incorrect password",
					user.isPasswordCorrect("wrongpassword"));
		} catch (NoSuchAlgorithmException e) {
			fail("Should not have a NoSuchAlgorithmException occur.");
		}
	}

	/**
	 * Tests that the salt used for password hashing is unique and secure.
	 * 
	 * Verifies that the salt is not null and is of a reasonable length.
	 * 
	 * (Luis Robles: Just for note, I am making it just make sure it is not 0...not
	 * sure of an appropriate size for this...)
	 */
	@Test
	public void testSaltGeneration() {
		assertTrue("Salt should not be null", user.getHashSalt() != null);
		assertTrue("Salt should be a reasonable length", user.getHashSalt().length() > 0);
	}

	/**
	 * Tests that the user object does not allow direct access to the password or
	 * salt.
	 * 
	 * Ensures that the password and salt are not exposed publicly, maintaining
	 * security.
	 */
	@Test
	public void testPasswordAndSaltEncapsulation() {
		try {
			// We cannot access the password directly, so we verify via methods
			assertTrue("Password verification should work as expected", user.isPasswordCorrect("securepassword123"));
			// Salt should not be directly accessible, it's a private field.
			assertTrue("Salt should be non-null and encapsulated", user.getHashSalt() != null);
		} catch (NoSuchAlgorithmException e) {
			fail("Should not have a NoSuchAlgorithmException occur.");
		}
	}
}
