package bankapp;

public class Transaction {
	private String type; // "Deposit" or "Withdrawal"
    private double amount;
    
    public Transaction(String type, double amount) {
    	this.type = type;
        this.amount = amount;
    }
    
	public String toString() {
        return "Transaction{" +
                "type: " + type +
                ", amount: " + amount +
                '}';
    }

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	
}