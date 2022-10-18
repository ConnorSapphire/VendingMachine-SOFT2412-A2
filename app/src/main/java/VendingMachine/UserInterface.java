package VendingMachine;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private FileManager fm;
    private Scanner scanner;

    public UserInterface(FileManager fm){
        this.fm = fm;
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println(ANSI_CYAN + "Welcome to " + ANSI_BLUE + "ATLANTIS" + ANSI_CYAN + " vending machine." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "For a list of all commands you have access to, type 'help'." + ANSI_RESET);
    }
    
    /**
     * Get input from the terminal.
     * @return String representation of input into the terminal.
     */
    public String getInput() {
        System.out.print("> ");
        String input = "";
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        return input;
    }

    public String getPlainInput() {
        String input = "";
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        return input;
    }

    public String getInputPassword() {
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.next());
        }
        return input.toString();
    }

    /**
     * Display text through terminal prompting user to select a product from the vending machine.
     */
    public void displaySelectProduct() {
        System.out.println("Please select a product: ");
    }

    /**
     * Display text through terminal prompting user to select a method of payment. Either card or cash.
     */
    public void displaySelectPaymentMethod() {
        System.out.println("Please enter your payment method, either " + ANSI_YELLOW + ANSI_WHITE_BACKGROUND + "'cash'" + ANSI_RESET + " or " + ANSI_YELLOW + ANSI_WHITE_BACKGROUND + "'card'" + ANSI_RESET + ".");
    }

    /**
     * Display text through terminal stating the login attempt failed.
     */
    public void displayLoginFailed() {
        System.out.println(ANSI_RED + "Incorrect credentials provided. Login failed." + ANSI_RESET);
    }

    /**
     * Display text through terminal stating the login attempt was successful.
     * @param user The User account that was logged into.
     */
    public void displayLoginSuccess(User user) {
        System.out.println(ANSI_CYAN + "Login success! Welcome " + ANSI_BLUE + user.getUsername() + ANSI_CYAN + "!" + ANSI_RESET);
    }


    /**
     * Display text through terminal listing all current stock in the vending machine.
     */
    public void displayStock() {
        displayProductTable();
    }

    /**
     * Display text through terminal with a detailed list of all current stock in the vending machine.
     * Includes product name, product code, category, price, and quantity.
     */
    public void displayDetailedStock() {

    }

    /**
     * Display text through terminal with a list of each product and the total sold.
     */
    public void displayStockSales() {

    }

    /**
     * Display text through terminal with a list of all previous successful transactions.
     */
    public void displayTransactionHistory() {

    }

    /**
     * Display text through terminal with a list of all previous unsuccessful transactions.
     */
    public void displayCancelledTransactions() {

    }

    /**
     * Display text through terminal with a list of all the change currently in the vending machine.
     */
    public void displayChange() {

    }

    /**
     * Display text through terminal with a list of all registered users. Includes sellers, cashiers and owners,
     * and displays their access level.
     */
    public void displayUsers() {

    }

    /**
     * Display text through terminal with a list of all available commands.
     */
    public void displayCustomerHelp() {
        System.out.println(ANSI_CYAN + "Your current access level is " + ANSI_BLUE + "customer" + ANSI_CYAN + ". You have access to the following commands:" + ANSI_RESET);
        System.out.println("> login" + ANSI_YELLOW + "\n\tLogin to a registered account." + ANSI_RESET);
        System.out.println("> register" + ANSI_YELLOW + "\n\tRegister a new account." + ANSI_RESET);
        System.out.println("> logout" + ANSI_YELLOW + "\n\tLogout of the current account." + ANSI_RESET);
        System.out.println("> display" + ANSI_YELLOW + "\n\tDisplay all stock in the vending machine." + ANSI_RESET);
        System.out.println("> buy" + ANSI_YELLOW + "\n\tSelect products to purchase and make payment." + ANSI_RESET);
        System.out.println("> help" + ANSI_YELLOW + "\n\tDisplay all available commands." + ANSI_RESET);
        System.out.println("> quit" + ANSI_YELLOW + "\n\tExit the application." + ANSI_RESET);
    }

    public void displayCashierHelp() {
        // INCOMPLETE
        System.out.println(ANSI_CYAN + "Your current access level is " + ANSI_BLUE + "cashier" + ANSI_CYAN + ". You have access to the following commands:" + ANSI_RESET);
        System.out.println("> login" + ANSI_YELLOW + "\n\tLogin to a registered account." + ANSI_RESET);
        System.out.println("> register" + ANSI_YELLOW + "\n\tRegister a new account." + ANSI_RESET);
        System.out.println("> logout" + ANSI_YELLOW + "\n\tLogout of the current account." + ANSI_RESET);
        System.out.println("> display" + ANSI_YELLOW + "\n\tDisplay all stock in the vending machine." + ANSI_RESET);
        System.out.println("> buy" + ANSI_YELLOW + "\n\tSelect products to purchase and make payment." + ANSI_RESET);
        System.out.println("> help" + ANSI_YELLOW + "\n\tDisplay all available commands." + ANSI_RESET);
        System.out.println("> quit" + ANSI_YELLOW + "\n\tExit the application." + ANSI_RESET);
    }

    public void displaySellerHelp() {
        System.out.println(ANSI_CYAN + "Your current access level is " + ANSI_BLUE + "seller" + ANSI_CYAN + ". You have access to the following commands:" + ANSI_RESET);
        System.out.println("> login" + ANSI_YELLOW + "\n\tLogin to a registered account." + ANSI_RESET);
        System.out.println("> register" + ANSI_YELLOW + "\n\tRegister a new account." + ANSI_RESET);
        System.out.println("> logout" + ANSI_YELLOW + "\n\tLogout of the current account." + ANSI_RESET);
        System.out.println("> display" + ANSI_YELLOW + "\n\tDisplay all stock in the vending machine." + ANSI_RESET);
        System.out.println("> buy" + ANSI_YELLOW + "\n\tSelect products to purchase and make payment." + ANSI_RESET);
        System.out.println("> help" + ANSI_YELLOW + "\n\tDisplay all available commands." + ANSI_RESET);
        System.out.println("> quit" + ANSI_YELLOW + "\n\tExit the application." + ANSI_RESET);
    }

    public void displayOwnerHelp() {
        System.out.println(ANSI_CYAN + "Your current access level is " + ANSI_BLUE + "owner" + ANSI_CYAN + ". You have access to the following commands:" + ANSI_RESET);
        System.out.println("> login" + ANSI_YELLOW + "\n\tLogin to a registered account." + ANSI_RESET);
        System.out.println("> register" + ANSI_YELLOW + "\n\tRegister a new account." + ANSI_RESET);
        System.out.println("> logout" + ANSI_YELLOW + "\n\tLogout of the current account." + ANSI_RESET);
        System.out.println("> display" + ANSI_YELLOW + "\n\tDisplay all stock in the vending machine." + ANSI_RESET);
        System.out.println("> buy" + ANSI_YELLOW + "\n\tSelect products to purchase and make payment." + ANSI_RESET);
        System.out.println("> help" + ANSI_YELLOW + "\n\tDisplay all available commands." + ANSI_RESET);
        System.out.println("> quit" + ANSI_YELLOW + "\n\tExit the application." + ANSI_RESET);
    }

    /**
     * Display text through terminal with the outcome of a given command.
     * @param commandName The name of the command.
     * @param outcome The outcome of the command.
     */
    public void displayCommandOutcome(String commandName, boolean outcome) {
        if (outcome) {
            System.out.println(ANSI_GREEN + "The command " + ANSI_WHITE_BACKGROUND + "'" + commandName + "'" + ANSI_RESET + ANSI_GREEN + "was successful!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "The command " + ANSI_WHITE_BACKGROUND + "'" + commandName + "'" + ANSI_RESET + ANSI_RED + "was unsuccessful!" + ANSI_RESET);
        }
    }

    /**
     * Display text through terminal with the given error message.
     * @param error Error message to display.
     */
    public void displayErrorString(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public void displayUnauthorisedAccess(String commandName) {
        System.out.println(ANSI_RED + "You do not have a high enough access level to access this feature." + ANSI_RESET);
    }

    public void displayProductTable(){
        CommandLineTable ct = new CommandLineTable();
        ct.setHeaders("Category", "Name", "Price", "Quantity");
        HashMap<String, Double[]> Drinks = fm.lsDrinks();
        HashMap<String, Double[]> Chocolates = fm.lsChocolates();
        HashMap<String, Double[]> Chips = fm.lsChips();
        HashMap<String, Double[]> Candies = fm.lsCandies();
        for(String d : Drinks.keySet()){
            ct.addRow(d, "Drinks", Double.toString(Drinks.get(d)[0]), Double.toString(Drinks.get(d)[1]));
        }
        for(String d : Chocolates.keySet()){
            ct.addRow(d, "Chocolates", Double.toString(Chocolates.get(d)[0]), Double.toString(Chocolates.get(d)[1]));
        }
        for(String d : Chips.keySet()){
            ct.addRow(d, "Chips", Double.toString(Chips.get(d)[0]), Double.toString(Chips.get(d)[1]));
        }
        for(String d : Candies.keySet()){
            ct.addRow(d, "Candies", Double.toString(Candies.get(d)[0]), Double.toString(Candies.get(d)[1]));
        }
        ct.print();
    }
    
}
