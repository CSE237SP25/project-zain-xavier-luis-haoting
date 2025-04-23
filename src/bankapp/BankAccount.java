package bankapp;

import java.util.List;
import java.util.UUID;

/**
 * Represents a bank account with a unique identifier, optional nickname, 
 * a balance, and a transaction log.
 * Provides basic operations like deposit, withdrawal, and balance tracking.
 */
public class BankAccount {

    /** Unique identifier for the bank account. */
    private final UUID id;

    /** Optional nickname to help identify the account. */
    private String nickname;

    /** Current balance of the account. */
    private double balance;

    /** Transaction log for recording deposits and withdrawals. */
    private Log transactionLog;
    
    /** Log to track failed withdrawal attempts */
    private Log failedTransactionLog;

    /**
     * Constructs a new BankAccount with a unique ID, a zero balance,
     * and an empty transaction log.
     */
    public BankAccount() {
        this.id = UUID.randomUUID();
        this.transactionLog = new Log();
        this.failedTransactionLog = new Log();
        this.balance = 0;
    }

    /**
     * Retrieves the unique identifier of the account.
     * 
     * @return The UUID of the account.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retrieves the nickname of the account.
     * If no nickname is set, a default string using the ID is returned.
     * 
     * @return The nickname or a default representation of the account.
     */
    public String getNickname() {
        return nickname != null ? nickname : "Account " + id.toString().substring(0, 8);
    }

    /**
     * Sets a nickname for this account.
     * 
     * @param nickname The desired nickname for the account.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
            failedTransactionLog.addTransaction("Failed Withdrawal", amount); 
            throw new IllegalArgumentException("Insufficient funds.");
        }
        this.balance -= amount;
        transactionLog.addTransaction("Withdrawal", amount);
    }

    /**
     * Retrieves the transaction log for this bank account.
     *
     * @return A list of transactions made on this account.
     */
    public List<Transaction> getTransactionLog() {
        return this.transactionLog.getTransactions();
    }
    
    public List<Transaction> getFailedTransactionLog() {
        return this.failedTransactionLog.getTransactions();
    }


    /**
     * Prints all transactions associated with this account to the console.
     */
    public void printTransactions() {
        transactionLog.printTransactions();
    }
    
    public void printFailedTransactions() {
        failedTransactionLog.printTransactions();
    }
    
    

}
