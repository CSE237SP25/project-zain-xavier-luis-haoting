package bankapp;

import java.util.Scanner;

public class Menu {
	
	private BankAccount theAccount; 
    private Scanner keyboardInput;  
	
	public Menu() {
		theAccount = new BankAccount();
        keyboardInput = new Scanner(System.in); 
	}
	
	//display methods don't need to be tested
	public void displayOptions() {
		System.out.println("Please select an option and an amount to deposit/withdraw:");
		System.out.println("Deposit (1)");
		System.out.println("Withdraw (2)");
		System.out.println("Check current balance (3)");
		System.out.println("Review transaction log (4)");
		System.out.println("Quit (5)");
	}
	
	//methods that require user input (Scanner) don't need to be tested
	public double getUserInput() {
			return keyboardInput.nextDouble();
		
	}
	
	//Can and should test methods the process user input
	public void processUserInput(double selection, double amount) {
		if (selection == 1) {
			userDeposit(amount);
		}
		else if (selection == 2) {
			userWithdraw(amount);
		}
		else if (selection == 3) {
			System.out.println("Current Balance: $" + theAccount.getCurrentBalance());
		}
		else if (selection == 4) {
            theAccount.printTransactions();
		}
		else if (selection != 5) {
            System.out.println("Invalid selection. Please choose a valid option.");
        }
		
	}
	
	public void userDeposit(double amount) {
		theAccount.deposit(amount);
		System.out.println("Deposited $" + amount);
		System.out.println("Current Balance: $" + theAccount.getCurrentBalance());
	}
	public void userWithdraw(double amount) {
		theAccount.withdraw(amount);
		System.out.println("Withdrew $" + amount);
		System.out.println("Current Balance: $" + theAccount.getCurrentBalance());	
		}
	
	public BankAccount getAccount() {
		return theAccount;
	}

    public void close() {
        if (keyboardInput != null) {
            keyboardInput.close(); 
        }
    }

}
