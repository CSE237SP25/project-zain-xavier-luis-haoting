package bankapp;

/**
 * The {@code ChaChingApp} class is the entry point of the banking application.
 * It handles user login and routes the user to appropriate menus based on their role (user/admin).
 */
public class ChaChingApp {

    /**
     * Starts the banking application.
     * Handles login, routes to admin or user options based on user role,
     * and repeats until the user chooses to exit.
     */
    public void start() {
        Menu menu = new Menu();

        User user;
        while ((user = menu.handleLogin()) != null) {
            if (user.isAdmin()) {
                menu.displayAdminOptions(); // Route admin flow
            } else {
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
        }

        System.out.println("Thanks for using ChaChing! Goodbye.");
        menu.close();
    }
}
