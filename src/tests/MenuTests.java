package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bankapp.Menu;
import bankapp.User;
import bankapp.BankAccount;

public class MenuTests {

    private Menu m;

    @Before
    public void setUp() throws Exception {
        m = new Menu();

        // Simulate registering and logging in a test user
        String username = "testuser";
        String password = "password";
        m.getAccounts().registerUser(username, password);
        User testUser = m.getAccounts().login(username, password);
        m.setCurrentUser(testUser);
    }

    @Test
    public void testUserDeposit() {
        m.processUserInput(1, 25);
        BankAccount account = m.getCurrentUser().getAccount();
        assertEquals(25.0, account.getCurrentBalance(), 0.005);
    }

    @Test
    public void testUserWithdraw() {
        m.processUserInput(1, 25); // deposit first
        m.processUserInput(2, 15); // withdraw
        BankAccount account = m.getCurrentUser().getAccount();
        assertEquals(10.0, account.getCurrentBalance(), 0.005);
    }
}
