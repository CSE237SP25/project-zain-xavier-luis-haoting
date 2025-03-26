package bankapp;

import java.util.Scanner;

public class Menu {
	
	private BankAccount theAccount; 
	
	public Menu() {
		theAccount = new BankAccount();
	}
	
	//display methods don't need to be tested
	public void displayOptions() {
		System.out.println("Please select an option and an amount to deposit/withdraw:");
		System.out.println("Deposit (1)");
		System.out.println("Withdraw (2)");
		System.out.println("Quit (3)");
	}
	
	//methods that require user input (Scanner) don't need to be tested
	public double getUserInput() {
		Scanner keyboardInput = new Scanner(System.in);
		double userInput = keyboardInput.nextDouble();
		return userInput;
	}
	
	//Can and should test methods the process user input
	public void processUserInput(double selection, double amount) {
		if (selection == 1) {
			userDeposit(amount);
		}
		if (selection == 2) {
			userWithdraw(amount);
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
	
	public static void main(String[] args) {
		Menu m = new Menu();
		m.displayOptions();
		double selection = m.getUserInput();
		while (selection != 3) {
			double amount = m.getUserInput();
			m.processUserInput(selection, amount);
			m.displayOptions();
			selection = m.getUserInput();
		}
	}

}
