package bankapp;

import java.util.ArrayList;

public class Log {
    private ArrayList<Transaction> transactions;

    public Log() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(String type, double amount) {
    	if (amount < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative.");
        }
    	Transaction transaction = new Transaction(type, amount);
    	transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
