package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import bankapp.Log;

public class LogTests {
	

	@Test
    public void testAddTransaction() {
	Log log = new Log();
        log.addTransaction("Deposit", 100.0);
        assertEquals(1, log.getTransactions().size());
        assertEquals("Deposit", log.getTransactions().get(0).getType());
        assertEquals(100, log.getTransactions().get(0).getAmount(), 0.005);
    }

	@Test
	public void testAddMultipleTransactions() {
	    Log log = new Log();
	    
	    log.addTransaction("Deposit", 100.0);
	    log.addTransaction("Withdrawal", 50.0);
	    
	    assertEquals(2, log.getTransactions().size());
	    assertEquals("Deposit", log.getTransactions().get(0).getType());
	    assertEquals(100.0, log.getTransactions().get(0).getAmount(), 0.005);
	    
	    assertEquals("Withdrawal", log.getTransactions().get(1).getType());
	    assertEquals(50.0, log.getTransactions().get(1).getAmount(), 0.005);
	}
	
	 @Test
	    public void testAddTransactionWithNegativeAmount() {
	        Log log = new Log();
	        
	        try {
	            log.addTransaction("Deposit", -100.0);  
	            fail();
	        } catch (IllegalArgumentException e) {
	        	assertTrue(e != null);
	        }
	    }
	
	@Test
	public void testTransactionLogEmpty() {
	    Log log = new Log();
	    
	    assertEquals(0, log.getTransactions().size());
	}
}