package bankapp;

import java.util.Scanner;

public class Menu {
	
	private BankAccount theAccount; 
	
	public Menu() {
		theAccount = new BankAccount();
	}
	
	//display methods don't need to be tested
	public void displayOptions() {
		System.out.println("Please enter an amount to be deposited");
	}
	
	//methods that require user input (Scanner) don't need to be tested
	public double getUserInput() {
		Scanner keyboardInput = new Scanner(System.in);
		double userInput = keyboardInput.nextDouble();
		return userInput;
	}
	
	//Can and should test methods the process user input
	public void processUserInput(double amount) {
		theAccount.deposit(amount);
	}
	
	public BankAccount getAccount() {
		return theAccount;
	}

}
