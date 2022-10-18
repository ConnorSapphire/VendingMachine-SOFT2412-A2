package VendingMachine;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VendingMachine {
    private FileManager fileManager = new FileManager();
    private UserInterface ui = new UserInterface(fileManager);
    private User user;
    private HashMap<String, User> users;
    private HashMap<String, Product> products;
    private HashMap<String, Change> change;
    private HashMap<String, String> cards;
    private JSONObject json;
    private boolean quit;

    public VendingMachine() {
        UserCreator userCreator = new AnonymousCustomerCreator();
        users = new HashMap<String, User>();
        products = new HashMap<String, Product>();
        change = new HashMap<String, Change>();
        this.cards = fileManager.getCreditCards();
        this.user = userCreator.create("", "", ui, cards);
        this.products = new HashMap<String, Product>();
        HashMap<String, Double[]> drinks = fileManager.lsDrinks();
        int code = 0;
        ProductCreator creator = new DrinkCreator();
        for (String drink : drinks.keySet()) {
            products.put(drink, creator.create(drink, String.valueOf(code), drinks.get(drink)[0], (int) Math.round(drinks.get(drink)[1])));
            code += 1;
        }
        HashMap<String, Double[]> chocolates = fileManager.lsChocolates();
        creator = new ChocolateCreator();
        for (String chocolate : chocolates.keySet()) {
            products.put(chocolate, creator.create(chocolate, String.valueOf(code), chocolates.get(chocolate)[0], (int) Math.round(chocolates.get(chocolate)[1])));
            code += 1;
        }
        HashMap<String, Double[]> candies = fileManager.lsCandies();
        creator = new CandyCreator();
        for (String candy : candies.keySet()) {
            products.put(candy, creator.create(candy, String.valueOf(code), candies.get(candy)[0], (int) Math.round(candies.get(candy)[1])));
            code += 1;
        }
        HashMap<String, Double[]> chips = fileManager.lsChips();
        creator = new ChipCreator();
        for (String chip : chips.keySet()) {
            products.put(chip, creator.create(chip, String.valueOf(code), chips.get(chip)[0], (int) Math.round(chips.get(chip)[1])));
            code += 1;
        }
        this.quit = false;
        ui.displayWelcomeMessage();
    }

    public User getUser() {
        return this.user;
    }

    public HashMap<String, Product> getProducts() {
        return this.products;
    }

    public boolean isQuit() {
        return quit;
    }

    public User login(){
        // Get username and password
        System.out.print("Enter username: ");
        String username = ui.getPlainInput();
        System.out.print("Enter password: ");
        String password = ui.getInputPassword();
    
        // Check username exists in system  
        boolean exists = false;      
        for (String name : users.keySet()){
            if (name.equals(username)) {
                exists = true;
            }
        }
        
        if (!exists){
            ui.displayLoginFailed();
            return user;
        }

        if (password != users.get(username).getPassword()) {
            ui.displayLoginFailed();
            return user;
        }

        user = users.get(username);
        return user;
    }

    public User newRegisteredCustomer() {
        UserCreator customerCreator = new RegisteredCustomerCreator();
        System.out.println("Enter your username");
        String newUsername = ui.getPlainInput();
        if(newUsername.equals("")){
            System.out.println("No user name entered.");
            return user;
        }
        if (users.containsKey(newUsername)) {
            System.out.println("Username already exists.");
            return user;
        }

        String newPassword = ui.getInputPassword();

        User customer = customerCreator.create(newUsername, newPassword, ui, cards);
        users.put(newUsername, customer);
        user = customer;
        return customer;
    }

    public String getInput() {
        return ui.getInput();
    }

    public void handleInput() {
        String input = this.getInput();
        if (input.contains("help")) {
            user.displayHelp();
        }  else if (input.contains("buy")) {
            user.makeTransaction();
        } else if (input.contains("cancel")) {
            user.cancelTransaction();
        } else if (input.contains("login")) {
            user = this.login();
        } else if (input.contains("lougout")) {
            UserCreator creator = new AnonymousCustomerCreator();
            user = creator.create("", "", ui, cards);
        } else if (input.contains("register")) {
            user = this.newRegisteredCustomer();
        } else if (input.contains("display")) {
            user.displayStock();
        } else if (input.contains("quit")) {
            this.quit = true;
            System.exit(0);
        }
    }
}
