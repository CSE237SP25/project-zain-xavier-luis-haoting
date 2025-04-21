package bankapp;

/**
 * Represents a savings account that accrues interest over time
 * and limits the number of withdrawals per month.
 */
public class SavingsAccount extends BankAccount {

    private int withdrawalCount;
    private int withdrawalLimit;
    private double interestRate;

    /**
     * Constructs a new SavingsAccount with a zero balance,
     * a default interest rate of 1%, and a withdrawal limit of 3 per month.
     */
    public SavingsAccount() {
        super();
        this.withdrawalCount = 0;
        this.withdrawalLimit = 3;
        this.interestRate = 0.01; // 1% interest
    }
    
    /**
     * Constructs a new SavingsAccount with the a zero balance,
     * a withdrawal limit of 3 per month, and the
     * option to customize the interest rate.
     * @param interestRate
     * */
    public SavingsAccount(double interestRate) {
    	super();
    	this.withdrawalCount = 0;
    	this.withdrawalLimit = 3;
    	this.interestRate = interestRate;
    }

    /**
     * Withdraws a specified amount from the savings account, enforcing
     * a withdrawal limit per month.
     *
     * @param amount The amount to withdraw.
     * @throws IllegalArgumentException if the withdrawal limit is exceeded or insufficient funds.
     */
    @Override
    public void withdraw(double amount) {
        if (withdrawalCount >= withdrawalLimit) {
            throw new IllegalArgumentException("Withdrawal limit reached for this month.");
        }
        super.withdraw(amount);
        withdrawalCount++;
    }

    /**
     * Applies interest to the current balance.
     * Call this method monthly to accrue interest.
     */
    public void accrueInterest() {
        double interest = getCurrentBalance() * interestRate;
        deposit(interest);
    }

    /**
     * Resets the monthly withdrawal counter.
     * Call this method at the start of each month.
     */
    public void resetWithdrawalCount() {
        this.withdrawalCount = 0;
    }

    /**
     * Sets a new monthly withdrawal limit.
     * 
     * @param limit The maximum number of withdrawals allowed per month.
     */
    public void setWithdrawalLimit(int limit) {
        this.withdrawalLimit = limit;
    }

    /**
     * Sets the interest rate for this savings account.
     * 
     * @param rate The new interest rate (e.g., 0.02 for 2%).
     */
    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    /**
     * Gets the number of withdrawals made this month.
     *
     * @return The current withdrawal count.
     */
    public int getWithdrawalCount() {
        return withdrawalCount;
    }

    /**
     * Gets the monthly withdrawal limit.
     *
     * @return The maximum withdrawals allowed per month.
     */
    public int getWithdrawalLimit() {
        return withdrawalLimit;
    }
}
