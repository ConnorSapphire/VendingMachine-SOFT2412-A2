package VendingMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public abstract class User {
    private String username;
    private String password;
    private String accessLevel;
    private UserInterface ui;
    private MakeTransaction currentTransaction;
    private Transaction transaction;
    private boolean storedCard;
    private String cardName;
    private String cardNumber;
    private HashMap<String, Product> products;
    private HashMap<String, Product> shortProducts;
    private LinkedHashMap<String, Change> change;
    private boolean cancelTransaction;
    private HashMap<String, User> users;
    private HashMap<String, String> cards;

    /**
     * Create a new User.
     * @param username Unique name for the new User.
     * @param password Password to allow User access to their account.
     * @param accessLevel String representation of the User's access level.
     * @param ui Reference to the UserInterface to allow interaction with terminal
     */
    public User(String username, String password, String accessLevel, UserInterface ui, HashMap<String, String> cards) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.ui = ui;
        this.cards = cards;
        this.storedCard = false;
        this.cancelTransaction = false;
        this.cardName = "";
        this.cardNumber = "";
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public HashMap<String, User> getUsers() {
        return this.users;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public HashMap<String, Product> getProducts() {
        return this.products;
    }

    public void createShortProducts(HashMap<String, Product> products) {
        HashMap<String, Product> shortProductsTemp = new HashMap<String, Product>();
        for (String productName : products.keySet()) {
            Product product = products.get(productName);
            shortProductsTemp.put(product.getCode(), product);
        }
        this.shortProducts = shortProductsTemp;
    }

    public HashMap<String, Product> getShortProducts() {
        return this.shortProducts;
    }

    public void setChange(HashMap<String, Change> change2) {
        this.change = (LinkedHashMap<String, Change>) change2;
    }

    public LinkedHashMap<String, Change> getChange() {
        return this.change;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     */
    public String getAccessLevel() {
        return this.accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * 
     * @return
     */
    public UserInterface getUI() {
        return this.ui;
    }

    public void setCurrentTransaction(MakeTransaction mt) {
        this.currentTransaction = mt;
    }

    public MakeTransaction getCurrentTransaction() {
        return currentTransaction;
    }

    public boolean isCardStored() {
        return this.storedCard;
    }

    public String getCardName() {
        return this.cardName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void storeCard(String cardName, String cardNumber) {
        this.storedCard = true;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
    }

    public HashMap<String, String> getCards() {
        return this.cards;
    }

    public boolean isTransactionCancelled() {
        return this.cancelTransaction;
    }

    /**
     * 
     * @param product
     * @param paymentMethod
     * @return Whether the transaction was successful.
     */
    public boolean makeTransaction() {
        this.currentTransaction = new MakeTransaction(this);
        Thread t = new Thread(currentTransaction); // myRunnable does your calculations

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 120 * 1000;

        t.start(); // Kick off calculations

        while (System.currentTimeMillis() < endTime && !currentTransaction.isCancelled()) {
            // Still within time theshold, wait a little longer
            if (currentTransaction.isCancelled()) {
                break;
            }
            try {
                Thread.sleep(500L);  // Sleep 1/2 second
            } catch (InterruptedException e) {
                // Someone woke us up during sleep, that's OK
            }
        }

        if (System.currentTimeMillis() > endTime) {
            ui.displayErrorString("\nTransaction Timed Out (Press Enter)");
            currentTransaction.cancel("Transaction timed out.");
        }
        currentTransaction.finish();
        t.interrupt();  // Tell the thread to stop
        try {
            t.join();       // Wait for the thread to cleanup and finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void completeTransaction() {
        if (transaction.getPaymentMethod().contains("cash")) {
            PaymentContext context = new PaymentContext(new CashStrategy(this));
            context.pay();
        } else if (transaction.getPaymentMethod().contains("card")) {
            PaymentContext context = new PaymentContext(new CardStrategy(this));
            context.pay();
        } else {
            currentTransaction.cancel("Invalid user input.");
        }
    }

    public void cancelTransaction(String reason) {
        currentTransaction.cancel(reason);
        ui.displayErrorString("Transaction has been cancelled.");
    }

    public void cancelTransaction() {
        cancelTransaction("Cancelled by user.");
    }

    /**
     * 
     * @return The selected Product.
     */
    public Product selectProduct() {
        ui.displaySelectProduct();
        String name = ui.getPlainInput();
        Product product = null;
        if (name.toLowerCase().equalsIgnoreCase("cancel")) {
            cancelTransaction();
        } else if (products.containsKey(name)) {
            product = products.get(name);
        } else if (shortProducts.containsKey(name.toUpperCase())) {
            product = shortProducts.get(name.toUpperCase());
        } 
        return product;
    }
    
    public int selectProductQuantity(Product product) {
        ui.displayQuestionString("Enter quantity: ");
        int quantity = 0;
        try {
            String quanString = ui.getPlainInput();
            if (quanString.equals("")) {
                quantity = 1;
            } else {
                quantity = Integer.parseInt(quanString);
            }
        } catch (NumberFormatException e) {
            ui.displayErrorString("Quantity must be an integer.");
            return 0;
        }
        return quantity;
    }

    /**
     * 
     * @return The selected payment method. Either "cash" or "card".
     */
    public String selectPaymentMethod() {
        ui.displaySelectPaymentMethod();
        String paymentMethod = ui.getPlainInput();
        if (paymentMethod.toLowerCase().equalsIgnoreCase("cancel")) {
            cancelTransaction();
        } else if (paymentMethod.contains("card")) {
            return "card";
        } else if (paymentMethod.contains("cash")) {
            return "cash";
        }
        return "invalid";
    }

    /**
     * Display all the current stock in the vending machine.
     */
    public boolean displayStock() {
        ui.displayStock();
        return true;
    }

    /**
     * Display all the current stock in the vending machine. Including detailed product information
     * such as product code, category, price, and quantity.
     */
    public boolean displayDetailedStock() {
        ui.displayUnauthorisedAccess("display stock");
        return true;
    }

    /**
     * Display all stock, with details about how much was sold of each. "product code; product name; quantity sold".
     */
    public boolean displayStockSales() {
        ui.displayUnauthorisedAccess("display sales");
        return true;
    }

    /**
     * Display all previous transactions. Including transaction time, product name, amount paid, change given, and
     * payment method.
     */
    public boolean displayTransactionHistory() {
        ui.displayUnauthorisedAccess("display transaction history");
        return true;
    }

    /**
     * 
     * @param product
     * @param quantity
     * @return
     */
    public boolean fillProduct(Product product, int quantity) {
        ui.displayUnauthorisedAccess("fill product");
        return false;
    }

    /**
     * 
     * @param product
     * @param newName
     * @return
     */
    public boolean modifyProductName(Product product, String name) {
        ui.displayUnauthorisedAccess("modify product name");
        return false;
    }

    /**
     * 
     * @param product
     * @param code
     * @return
     */
    public boolean modifyProductCode(Product product, String code) {
        ui.displayUnauthorisedAccess("modify product code");
        return false;
    }

    /**
     * 
     * @param product
     * @param price
     * @return
     */
    public boolean modifyProductPrice(Product product, double price) {
        ui.displayUnauthorisedAccess("modify product price");
        return false;
    }

    /**
     * 
     * @param product
     * @param category
     * @return
     */
    public boolean modifyProductCategory(Product product, String category) {
        ui.displayUnauthorisedAccess("modify product category");
        return false;
    }

    /**
     * 
     * @param name
     * @param code
     * @param category
     * @param quantity
     * @param price
     * @return
     */
    public boolean addProduct(String name, String code, String category, int quantity, double price) {
        ui.displayUnauthorisedAccess("add product");
        return false;
    }

    public boolean removeProduct(Product product) {
        ui.displayUnauthorisedAccess("remove product");
        return false;
    }

    /**
     * 
     * @param change
     * @param quantity
     * @return
     */
    public boolean fillChange(Change change, int quantity) {
        ui.displayUnauthorisedAccess("fill change");
        return false;
    }

    /**
     * 
     * @param change
     * @param quantity
     * @return
     */
    public boolean removeChange(Change change) {
        ui.displayUnauthorisedAccess("remove change");
        return false;
    }

    /**
     * 
     * @param change
     * @param quantity
     * @param value
     * @return
     */
    public boolean addChange(String name, int quantity, double value, String type) {
        ui.displayUnauthorisedAccess("add change");
        return false;
    }

    /**
     * 
     */
    public boolean displayChange() {
        ui.displayUnauthorisedAccess("display change");
        return true;
    }

    public boolean displayChangeTable() {
        ui.displayUnauthorisedAccess("");
        return true;
    }

    /**
     * 
     * @param username
     * @param password
     * @param accessLevel
     * @param ui
     * @return
     */
    public boolean addUser(String username, String password, String accessLevel, UserInterface ui) {
        ui.displayUnauthorisedAccess("add user");
        return false;
    }

    public boolean removeUser(User user) {
        ui.displayUnauthorisedAccess("remove user");
        return false;
    }

    /**
     * 
     * @param user
     * @param accessLevel
     * @return
     */
    public boolean modifyUserAccess(User user, String accessLevel) {
        ui.displayUnauthorisedAccess("modify user access");
        return false;
    }

    /**
     * 
     * @param user
     * @param username
     * @return
     */
    public boolean modifyUserUsername(User user, String username) {
        ui.displayUnauthorisedAccess("modify user username");
        return false;
    }

    /**
     * 
     * @param user
     * @param password
     * @return
     */
    public boolean modifyUserPassword(User user, String password) {
        ui.displayUnauthorisedAccess("modify user password");
        return false;
    }

    /**
     * 
     */
    public boolean displayUsers() {
        ui.displayUnauthorisedAccess("display users");
        return true;
    }

    public boolean displayUsersTable() {
        ui.displayUnauthorisedAccess("");
        return true;
    }

    /**
     * 
     */
    public boolean displayCancelledTransactions() {
        ui.displayUnauthorisedAccess("display cancelled transactions");
        return true;
    }

    public boolean displayHelp() {
        ui.displayCustomerHelp();
        return true;
    }

    public void sortChangeHashMap() {
        LinkedHashMap<String, Change> change = getChange();
        LinkedHashMap<Double, String> changeByValue = new LinkedHashMap<Double, String>();
        for (Change item : change.values()) {
            changeByValue.put(item.getValue(), item.getName());
        }
        TreeMap<Double, String> sortedChangeByValue = new TreeMap<Double, String>(changeByValue);
        for (Double value : sortedChangeByValue.descendingKeySet()) {
            String key = sortedChangeByValue.get(value);
            Change item = change.get(key);
            change.remove(key);
            change.put(key, item);
        }
    }
}
