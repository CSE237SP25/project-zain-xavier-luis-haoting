package bankapp;

import java.util.List;

/**
 * Represents a bank account with a balance and a transaction log.
 * Provides basic operations like deposit, withdrawal, and balance tracking.
 */
public class BankAccount {

    private double balance;
    private Log transactionLog;

    /**
     * Constructs a new BankAccount with a zero balance
     * and an empty transaction log.
     */
    public BankAccount() {
        this.balance = 0;
        this.transactionLog = new Log();
    }

    /**
     * Deposits a specified amount into the bank account.
     *
     * @param amount The amount to deposit. Must be non-negative.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        transactionLog.addTransaction("Deposit", amount);
    }

    /**
     * Withdraws a specified amount from the bank account.
     *
     * @param amount The amount to withdraw. Must be non-negative and less than or equal to the current balance.
     * @throws IllegalArgumentException if the amount is negative or exceeds the current balance.
     */
    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance < amount) {
            System.out.println("Insufficient funds.");
            throw new IllegalArgumentException("Insufficient funds.");
        }
        this.balance -= amount;
        transactionLog.addTransaction("Withdrawal", amount);
    }

    /**
     * Returns the current balance of the account.
     *
     * @return The current balance.
     */
    public double getCurrentBalance() {
        return this.balance;
    }

    /**
     * Sets the current balance of the account.
     * Use cautiously — this method should typically only be used in administrative or initialization contexts.
     *
     * @param balance The new balance to set.
     */
    public void setCurrentBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the transaction log for this bank account.
     *
     * @return A list of transactions made on this account.
     */
    public List<Transaction> getTransactionLog() {
        return this.transactionLog.getTransactions();
    }

    /**
     * Prints all transactions associated with this account to the console.
     */
    public void printTransactions() {
        transactionLog.printTransactions();
    }
}
