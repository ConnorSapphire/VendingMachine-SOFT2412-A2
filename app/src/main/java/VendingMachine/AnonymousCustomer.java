package VendingMachine;

import java.util.HashMap;

public class AnonymousCustomer extends Customer {
    /**
     * Create a new AnonymousCustomer.
     * @param ui Reference to the UserInterface to allow interaction with terminal
     */
    public AnonymousCustomer(UserInterface ui, HashMap<String, String> cards) {
        super("", "", "anonymous", ui, cards);
    }
}
