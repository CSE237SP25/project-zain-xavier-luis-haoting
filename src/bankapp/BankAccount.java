package bankapp;

public class BankAccount {

	private double balance;
    private Log transactionLog;
	
	public BankAccount() {
		this.balance = 0;
		this.transactionLog = new Log();
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Deposit amount must be positive.");
		}
		this.balance += amount;
		transactionLog.addTransaction("Deposit", amount);
	}
	
	public void withdraw(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Withdrawal amount must be positive.");
		}
		if (this.balance < amount) {
			System.out.println("Insufficient funds.");
			throw new IllegalArgumentException("Insufficient funds.");
		}
		this.balance -= amount;
		transactionLog.addTransaction("Withdrawal", amount);
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}

	public void setCurrentBalance(double balance) {
		this.balance = balance;
	}

	public void printTransactions() {
	        transactionLog.printTransactions();
	}
}
