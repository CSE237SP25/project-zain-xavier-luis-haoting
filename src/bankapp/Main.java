package bankapp;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        while (menu.handleLogin()) {
            double selection;
            do {
                menu.displayOptions();
                selection = menu.getUserInput();

                double amount = 0;

                if (selection == 1 || selection == 2) {
                    System.out.print("Enter amount: ");
                    amount = menu.getUserInput();
                }

                menu.processUserInput(selection, amount);

            } while (menu.isLoggedIn());
        }

        System.out.println("Thanks for using ChaChing! Goodbye.");
        menu.close();
    }
}
