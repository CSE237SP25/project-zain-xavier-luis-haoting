package bankapp;

public class Main {

public static void main(String[] args) {
		Menu m = new Menu();
		m.displayOptions();
		double selection = m.getUserInput();
		while (selection != 5) {
			double amount = 0;
            if (selection == 1 || selection == 2) {System.out.println("Enter amount: ");
			amount = m.getUserInput(); }
			m.processUserInput(selection, amount);
			m.displayOptions();
			selection = m.getUserInput();
		}
        System.out.println("Exiting...");
        m.close();  
    }
}