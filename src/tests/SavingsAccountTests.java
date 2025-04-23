package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import bankapp.SavingsAccount;

/**
 * Unit tests for the {@link SavingsAccount} class.
 * These tests ensure proper interest accrual, deposit, and withdrawal behavior.
 */
public class SavingsAccountTests {

	SavingsAccount account;
	
	@BeforeEach
	void setup() {
        account = new SavingsAccount();
	}

    /**
     * Tests that a new SavingsAccount has a zero balance by default.
     */
    @Test
    public void testInitialBalanceIsZero() {
        assertEquals(0.0, account.getCurrentBalance(), 0.001);
    }

    /**
     * Tests that depositing a valid amount increases the balance accordingly.
     */
    @Test
    public void testDepositIncreasesBalance() {
    	account.deposit(200.00);
        assertEquals(200.0, account.getCurrentBalance(), 0.001);
    }

    /**
     * Tests that depositing a negative amount throws an IllegalArgumentException.
     */
    @Test
    public void testDepositNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }

    /**
     * Tests that withdrawing a valid amount reduces the balance accordingly.
     */
    @Test
    public void testWithdrawDecreasesBalance() {
        account.deposit(300.0);
        account.withdraw(100.0);
        assertEquals(200.0, account.getCurrentBalance(), 0.001);
    }

    /**
     * Tests that withdrawing more than the balance throws an IllegalArgumentException.
     */
    @Test
    public void testWithdrawMoreThanBalanceThrowsException() {
        account.deposit(100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150.0);
        });
    }

    /**
     * Tests that withdrawing a negative amount throws an IllegalArgumentException.
     */
    @Test
    public void testWithdrawNegativeAmountThrowsException() {
        account.deposit(100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-20.0);
        });
    }

    /**
     * Tests that interest is correctly accrued at the default interest rate of 1%.
     */
    @Test
    public void testAccrueInterestDefaultRate() {
        account.deposit(1000.0);
        account.accrueInterest(); // 1% interest
        assertEquals(1010.00, account.getCurrentBalance(), 0.001);
    }

    /**
     * Tests multiple interest accruals are compounded correctly.
     */
    @Test
    public void testMultipleInterestAccruals() {
        account.deposit(1000.0);
        account.accrueInterest(); // 1010
        account.accrueInterest(); // 1020.10
        assertEquals(1020.10, account.getCurrentBalance(), 0.001);
    }

    /**
     * Tests that setting a custom interest rate affects accrual correctly.
     */
    @Test
    public void testCustomInterestRate() {
    	SavingsAccount savingsAccountCustomInterest = new SavingsAccount(0.02);
        savingsAccountCustomInterest.deposit(200.0);
        savingsAccountCustomInterest.accrueInterest();
        assertEquals(204.0, savingsAccountCustomInterest.getCurrentBalance(), 0.001);
    }

    /**
     * Tests that attempting to accrue interest on a zero balance keeps the balance at zero.
     */
    @Test
    public void testAccrueInterestWithZeroBalance() {
        account.accrueInterest();
        assertEquals(0.0, account.getCurrentBalance(), 0.001);
    }
}
