package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.Admin;
import bankapp.Bank;
import bankapp.BankAccount;
import bankapp.Transaction;
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
    private Bank bank;

    /**
     * Setup method that runs before each test case.
     * Creates a new {@link User} and {@link Admin} object to ensure tests are isolated from each other.
     * Creates a Bank object and adds users with accounts for transaction tests.
     */
    @BeforeEach
    void setup() {
        try {
            // Create a new user with a test username and password
            normalUser = new User("john_doe", "securepassword123");
            // Create a new admin with a test username and password
            admin = new Admin("admin", "adminSecure234");

            // Create a bank and add users
            bank = new Bank();
            bank.registerUser(normalUser);
            bank.registerUser(admin);

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

    // --------------------------------------
    // Transaction-related tests
    // --------------------------------------

    /**
     * Tests that the {@link Admin#getAllTransactions(Bank)} method can retrieve all transactions from all accounts.
     */
    @Test
    public void testAdminCanViewAllTransactions() {
    	User theNormalUser = null;
    	try {
			theNormalUser = bank.login(normalUser.getUsername(), normalUser.getPasswordHash());
		} catch (NoSuchAlgorithmException e) {
			fail("Login failed due to NoSuchAlgorithmException.");
		}
    	if(theNormalUser == null) {
    		fail("Bank Login failed which should have happened...");
    	}

		theNormalUser.getAccount().deposit(100); // Add a deposit transaction
		theNormalUser.getAccount().withdraw(30); // Add a withdrawal transaction

        // Admin tries to get all transactions
        List<Transaction> transactions = admin.getAllTransactions(bank);
        System.out.println(transactions.size());

        // Validate that the number of transactions is as expected
        assertTrue("Admin should see both transactions (deposit and withdrawal)", transactions.size() == 2);

        // Optionally, validate the content of transactions
        assertTrue("First transaction should be a deposit", transactions.get(0).getType().equals("Deposit"));
        assertTrue("Second transaction should be a withdrawal", transactions.get(1).getType().equals("Withdrawal"));
    }

    /**
     * Tests that a null bank passed to {@link Admin#getAllTransactions(Bank)} returns an empty list.
     */
    @Test
    public void testAdminGetTransactionsWithNullBankReturnsEmptyList() {
        List<Transaction> transactions = admin.getAllTransactions(null);
        assertTrue("Admin should get an empty list when the bank is null", transactions.isEmpty());
    }
    
    /**
     * Tests that if a user has no transactions and thus {@link Admin#getAllTransactions(Bank)} returns an empty list.
     * */
    @Test
    public void testAdminCanViewAllTransactions_NoTransactions() {
        List<Transaction> transactions = admin.getAllTransactions(bank);
        assertTrue("Admin should get an empty list when there are no transactions", transactions.isEmpty());
    }

}
