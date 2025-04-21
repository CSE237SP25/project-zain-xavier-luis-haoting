package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount();
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testSimpleWithdraw() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount();
		
		//2. Call the method being tested
		account.deposit(25);
		account.withdraw(10);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 15.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testNegativeWithdraw() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.withdraw(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testInsufficientWithdraw() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.withdraw(25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testUniqueAccountIdGeneration() {
	    // 1. Create two accounts
	    BankAccount account1 = new BankAccount();
	    BankAccount account2 = new BankAccount();

	    // 2. Assert UUIDs are different
	    assertTrue(!account1.getId().equals(account2.getId()));
	}

	/**
	 * Tests that setting and retrieving the nickname of a BankAccount works correctly.
	 */
	@Test
	public void testNicknameAssignment() {
	    BankAccount account = new BankAccount();
	    account.setNickname("My Main Account");

	    assertEquals("My Main Account", account.getNickname());
	}

}
