package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bankapp.BankAccount;
import bankapp.Menu;

public class MenuTests {
	
	@Test
	public void testUserDeposit() {
		// 1. Create object being tested
		Menu m = new Menu();
		
		//2. Call method being tested
		m.processUserInput(1, 25);
		
		//3. Use assertions to verify correctness
		BankAccount account = m.getAccount();
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	public void testUserWithdraw() {
		// 1. Create object being tested
		Menu m = new Menu();
		
		//2. Call method being tested
		m.processUserInput(1, 25);
		m.processUserInput(2, 15);
		
		//3. Use assertions to verify correctness
		BankAccount account = m.getAccount();
		assertEquals(account.getCurrentBalance(), 10.0, 0.005);
	}

}
