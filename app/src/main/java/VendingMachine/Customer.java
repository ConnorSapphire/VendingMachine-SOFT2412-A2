package VendingMachine;

public abstract class Customer extends User {
    /**
     * Create a new Customer.
     * @param username Unique name for the new Customer.
     * @param password Password to allow Customer access to their account.
     * @param ui Reference to the UserInterface to allow interaction with terminal
     */
    public Customer(String username, String password, UserInterface ui) {
        super(username, password, "customer", ui);
    }
}