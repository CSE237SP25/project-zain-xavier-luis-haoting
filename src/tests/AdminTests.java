package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.Admin;
import bankapp.User;

/**
 * Unit tests for verifying the behavior of the {@link Admin} class, which represents
 * a privileged user type with elevated access within the banking application.
 * 
 * These tests ensure that admin accounts can be correctly distinguished from regular users
 * and that privilege-checking methods behave as expected.
 */
public class AdminTests {
	
	private User normalUser;
	private Admin admin;
	
	/**
	 * Setup method that runs before each test case.
	 * Creates a new {@link User} and {@link Admin} object to ensure tests are isolated from each other.
	 */
	@BeforeEach
	void setup() {
		try {
			// Create a new user with a test username and password
			normalUser = new User("john_doe", "securepassword123");
			// Create a new admin with a test username and password
			admin = new Admin("admin", "adminSecure234");
		} catch (NoSuchAlgorithmException e) {
			fail("Failed to create user due to NoSuchAlgorithmException");
		}
	}

	/**
	 * Tests that the {@link Admin#isAdmin()} method returns true for an Admin instance
	 * and false for a regular {@link User}.
	 * 
	 * This ensures that the system can distinguish admin users from standard users.
	 */
	@Test
	public void testAdminPrivileges() throws NoSuchAlgorithmException {
		assertTrue(admin.isAdmin());
		assertFalse(normalUser.isAdmin());
	}
	
	/**
	 * Tests that only an existing admin can create another admin.
	 * 
	 * This verifies that a regular user cannot create admin accounts, ensuring that only
	 * admins can register other admins.
	 */
	@Test
	public void testRestrictedAdminCreation() throws NoSuchAlgorithmException {
		// Admin user attempts to create another admin
		Admin newAdmin = Admin.createAdmin(admin, "new_admin", "newAdminPass!");
		assertTrue("Admin should be able to create another admin", newAdmin != null && newAdmin.isAdmin());

		// Regular user attempts to create an admin (should fail)
		Admin unauthorizedAttempt = Admin.createAdmin(normalUser, "hacker_admin", "badPass!");
		assertFalse("Non-admin should not be able to create an admin", unauthorizedAttempt != null);
	}

    /**
     * Tests that a null requestingUser cannot create an admin.
     * 
     * This verifies that the factory method handles null users gracefully
     * and does not allow admin creation by a non-existing user.
     */
    @Test
    public void testNullRequestingUser() throws NoSuchAlgorithmException {
        Admin newAdmin = Admin.createAdmin(null, "new_admin", "newAdminPass!");
        assertFalse("Null requesting user should not be able to create an admin", newAdmin != null);
    }

    /**
     * Tests the creation of admins with invalid usernames or passwords.
     * 
     * This ensures that invalid data (e.g., empty or null username/password) 
     * does not allow an admin to be created.
     */
    @Test
    public void testAdminCreationWithInvalidData() throws NoSuchAlgorithmException {
        // Admin user attempts to create an admin with empty username
        Admin newAdmin = Admin.createAdmin(admin, "", "newAdminPass!");
        assertFalse("Admin should not be created with an empty username", newAdmin != null);

        // Admin user attempts to create an admin with null username
        newAdmin = Admin.createAdmin(admin, null, "newAdminPass!");
        assertFalse("Admin should not be created with a null username", newAdmin != null);

        // Admin user attempts to create an admin with empty password
        newAdmin = Admin.createAdmin(admin, "new_admin", "");
        assertFalse("Admin should not be created with an empty password", newAdmin != null);

        // Admin user attempts to create an admin with null password
        newAdmin = Admin.createAdmin(admin, "new_admin", null);
        assertFalse("Admin should not be created with a null password", newAdmin != null);
    }
}
