package tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import bankapp.Menu;
import bankapp.User;
import bankapp.Admin;

/**
 * Unit tests for the {@code Menu} class.
 * Verifies functionality related to user interaction, account operations, 
 * and admin features within the banking application.
 */
public class MenuTests {

    private Menu menu;
    private Admin testAdmin;

    /**
     * Sets up a {@code Menu} instance with a logged-in user before each test.
     */
    @Before
    public void setUp() throws Exception {
        menu = new Menu();

        // Register and log in a test user
        String username = "testuser";
        String password = "password";
        menu.getAccounts().registerUser(username, password);
        User user = menu.getAccounts().login(username, password);
        menu.setCurrentUser(user);
        testAdmin = new Admin("luis", "pass");
        menu.getAccounts().registerUser(testAdmin);
    }

    /**
     * Tests depositing funds using {@code processUserInput()} and checks updated balance.
     */
    @Test
    public void testUserDeposit() {
        menu.processUserInput(1, 25);
        assertEquals(25.0, menu.getCurrentUser().getCurrentAccount().getCurrentBalance(), 0.005);
    }

    /**
     * Tests withdrawing funds after a deposit and verifies the balance is updated correctly.
     */
    @Test
    public void testUserWithdraw() {
        menu.processUserInput(1, 50);
        menu.processUserInput(2, 20);
        assertEquals(30.0, menu.getCurrentUser().getCurrentAccount().getCurrentBalance(), 0.005);
    }

    /**
     * Tests that withdrawing more than the balance does not result in a negative balance.
     */
    @Test
    public void testOverdraftAttempt() {
        menu.processUserInput(2, 100);
        assertEquals(0.0, menu.getCurrentUser().getCurrentAccount().getCurrentBalance(), 0.005);
    }

    /**
     * Tests logging out by verifying {@code isLoggedIn()} returns false after logout.
     */
    @Test
    public void testLogoutFunctionality() {
        boolean result = menu.processUserInput(6, 0); // Logout
        assertFalse(result);
        assertFalse(menu.isLoggedIn());
    }

    /**
     * Tests that {@code isLoggedIn()} returns true when a user is logged in.
     */
    @Test
    public void testIsLoggedInTrue() {
        assertTrue(menu.isLoggedIn());
    }

    /**
     * Tests depositing zero funds and confirms that balance remains unchanged.
     */
    @Test
    public void testZeroDeposit() {
        menu.processUserInput(1, 0);
        assertEquals(0.0, menu.getCurrentUser().getCurrentAccount().getCurrentBalance(), 0.005);
    }

    /**
     * Tests withdrawing a negative amount and verifies that balance is unaffected.
     */
    @Test
    public void testNegativeWithdrawal() {
        menu.processUserInput(2, -50);
        assertEquals(0.0, menu.getCurrentUser().getCurrentAccount().getCurrentBalance(), 0.005);
    }
    
    /**
     * Tests that {@code Owner} admin is automatically initialized in the {@code Menu} constructor.
     */
    @Test
    public void testAdminOwnerInitialization() {
        Admin expected;
		try {
			expected = new Admin("owner", "verysecurePassword43");
			assertEquals(expected.getUsername(), menu.getAccounts().getUser("owner").getUsername());
			assertTrue(menu.getAccounts().getUser("owner") instanceof Admin);
		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
			fail("Should not have failed in creating the admin for initialization...");
		}
    }


    /**
     * Tests registering and logging in as an admin using {@code registerAdmin()} and {@code adminLogin()}.
     */
    @Test
    public void testAdminRegistrationAndLogin() {

    	menu.setCurrentUser(testAdmin);
        menu.registerAdmin("admin1", "adminpass");
        User admin = menu.getAccounts().getUser("admin1");
        assertNotNull(admin);
        assertEquals("admin1", admin.getUsername());
		assertTrue(menu.getAccounts().getUser("admin1") instanceof Admin);
    }

}
