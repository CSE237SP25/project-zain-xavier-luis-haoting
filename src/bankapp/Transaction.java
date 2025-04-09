package bankapp;
import java.time.LocalDateTime;

public class Transaction {
	private String type; // "Deposit" or "Withdrawal"
    private double amount;
    private LocalDateTime timestamp;
    
    public Transaction(String type, double amount) {
    	this.type = type;
        this.amount = amount;
    	this.timestamp = LocalDateTime.now();
    }
    
    public String toString() {
    	return String.format("[%s] %s of $%.2f", 
            timestamp != null ? timestamp.toString() : "No Timestamp", 
            type, 
            amount);
    }

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	
}