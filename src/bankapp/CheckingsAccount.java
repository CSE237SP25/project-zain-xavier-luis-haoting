package bankapp;

/**
 * Represents a checking account that allows unlimited withdrawals
 * and does not accrue interest.
 */
public class CheckingsAccount extends BankAccount {

    /**
     * Constructs a new CheckingAccount with a zero balance.
     */
    public CheckingsAccount() {
        super();
    }
    
    /**
     * I am leaving this barebones and only to differentiate as since it is
     * a checkings account, it can act just like the BankAccount.
     * Though, optionally we can add overrides to how deposit works 
     * or add different functionalities like overdrafting.
     * */
}
