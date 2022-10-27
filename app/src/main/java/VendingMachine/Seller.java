package VendingMachine;

import java.util.HashMap;

public class Seller extends User {
    /**
     * Create a new Seller.
     * @param username Unique name for the new Seller.
     * @param password Password to allow Seller access to their account.
     * @param ui Reference to the UserInterface to allow interaction with terminal
     */
    public Seller(String username, String password, UserInterface ui, HashMap<String, String> cards) {
        super(username, password, "seller", ui, cards);
    }


    /**
     * 
     */
    public void displayDetailedStock() {
        this.getUI().displayDetailedStock();
    }

    /**
     * 
     */
    public void displayStockSales() {
        reportSellingSummary(getProducts());
        this.getUI().displayStockSales();
    }

    /**
     * 
     * @param product
     * @param quantity
     * @return
     */
    public boolean fillProduct(Product product, int quantity) {
        if(quantity < 0){
            this.getUI().displayerrorMessage("Please provide a non-negative number of stock added");
            return false;
        }
        if(product.getQuantity() + quantity <= 15){
            product.setQuantity(product.getQuantity() + quantity);
            if (product.getCategory().equals("drink")) {
                this.getUI().getFileManager().updateDrinks(product);
            } else if (product.getCategory().equals("chocolate")) {
                this.getUI().getFileManager().updateChocolates(product);
            } else if (product.getCategory().equals("chip")) {
                this.getUI().getFileManager().updateChips(product);
            } else if (product.getCategory().equals("candy")) {
                this.getUI().getFileManager().updateCandies(product);
            }
            return true;
        } else {
            this.getUI().displayErrorString("The vending machine does not have enough space for this quantity.");
        }
        return false;
    }

    /**
     * 
     * @param product
     * @param newName
     * @return
     */
    public boolean modifyProductName(Product product, String name, HashMap<String, Product> products) {
        for(String key : products.keySet()){
            if(key.equals(name)){
                if(products.get(key).equals(product)){
                    this.getUI().displayerrorMessage("Can not modify the name. Please give a different name.");
                }else{
                    this.getUI().displayerrorMessage("Name already exists.");
                }
                return false;
            }
        }
        if (this.getProducts().containsKey(product.getName())) {
            this.getProducts().remove(product.getName());
            this.getUI().getFileManager().removeProduct(product);
        }
        product.setName(name);
        this.getProducts().put(name, product);
        if (product.getCategory().equalsIgnoreCase("drink")) {
            this.getUI().getFileManager().updateDrinks(product);
        } else if (product.getCategory().equalsIgnoreCase("chocolate")) {
            this.getUI().getFileManager().updateChocolates(product);
        } else if (product.getCategory().equalsIgnoreCase("candy")) {
            this.getUI().getFileManager().updateCandies(product);
        } else if (product.getCategory().equalsIgnoreCase("chip")) {
            this.getUI().getFileManager().updateChips(product);
        }
        return true;
    }

    /**
     * 
     * @param product
     * @param code
     * @return
     */
    public boolean modifyProductCode(Product product, String code, HashMap<String, Product> products) {
        char[] check = code.toCharArray();
        if(check.length != 3){
            this.getUI().displayerrorMessage("Only 3 digit code accepted.");
            return false;
        }
        for(String str : products.keySet()){
            Product p = products.get(str);
            if(code.equals(p.getCode())){
                if(p.equals(product)){
                    this.getUI().displayerrorMessage("Can not modify the code. Please give a different code.");
                }else{
                    this.getUI().displayerrorMessage("Code already exists.");
                }
                return false;
            }
        }
        String oldCode = product.getCode();
        product.setCode(code.toUpperCase());
        if (product.getCategory().equalsIgnoreCase("drink")) {
            this.getUI().getFileManager().updateDrinks(product);
        } else if (product.getCategory().equalsIgnoreCase("chocolate")) {
            this.getUI().getFileManager().updateChocolates(product);
        } else if (product.getCategory().equalsIgnoreCase("candy")) {
            this.getUI().getFileManager().updateCandies(product);
        } else if (product.getCategory().equalsIgnoreCase("chip")) {
            this.getUI().getFileManager().updateChips(product);
        }
        this.getUI().displaySuccessString("Product code of " + product.getName() + " successfully changed from " + oldCode + " to " + product.getCode() + ".");
        return true;
    }

    /**
     * 
     * @param product
     * @param price
     * @return
     */
    public boolean modifyProductPrice(Product product, double price) {
        if(price <= 0){
            this.getUI().displayerrorMessage("Please give a positive price.");
            return false;
        }
        double oldPrice = product.getPrice();
        product.setPrice(price);
        if (product.getCategory().equalsIgnoreCase("drink")) {
            this.getUI().getFileManager().updateDrinks(product);
        } else if (product.getCategory().equalsIgnoreCase("chocolate")) {
            this.getUI().getFileManager().updateChocolates(product);
        } else if (product.getCategory().equalsIgnoreCase("candy")) {
            this.getUI().getFileManager().updateCandies(product);
        } else if (product.getCategory().equalsIgnoreCase("chip")) {
            this.getUI().getFileManager().updateChips(product);
        }
        this.getUI().displaySuccessString("Product price of " + product.getName() + " successfully changed from $" + oldPrice + " to $" + product.getPrice() + ".");
        return true;
    }

    /**
     * 
     * @param product
     * @param category
     * @return
     */
    public boolean modifyProductCategory(Product product, String category) {
        String[] all  = new String[]{"Drinks", "Chocolates", "Chips", "Candies"};
        String oldCategory = product.getCategory();
        for(String cat : all){
            if(category.toLowerCase().equals(cat.toLowerCase())){
                product.setCategory(cat);
                if (product.getCategory().equalsIgnoreCase("drink")) {
                    this.getUI().getFileManager().updateDrinks(product);
                } else if (product.getCategory().equalsIgnoreCase("chocolate")) {
                    this.getUI().getFileManager().updateChocolates(product);
                } else if (product.getCategory().equalsIgnoreCase("candy")) {
                    this.getUI().getFileManager().updateCandies(product);
                } else if (product.getCategory().equalsIgnoreCase("chip")) {
                    this.getUI().getFileManager().updateChips(product);
                }
                this.getUI().displaySuccessString("Product category of " + product.getName() + " successfully changed from " + oldCategory + " to " + product.getCategory() + ".");
                return true;
            }
        }
        this.getUI().displayerrorMessage("Please give a valid category!");
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
    public boolean addProduct(String name, String code, String category, int quantity, double price, HashMap<String, Product> products) {
        if(price <= 0){
            this.getUI().displayerrorMessage("Price must be positive!");
            return false;
        }
        if(quantity < 0){
            this.getUI().displayerrorMessage("Quantity must be non-negative!");
            return false;
        }
        ProductCreator pc = null;
        String[] all  = new String[]{"Drinks", "Chocolates", "Chips", "Candies"};
        boolean find = false;
        for(String cat : all){
            if(category.toLowerCase().equals(cat.toLowerCase())){
                find = true;
                if(cat.equals(all[0])){
                    pc = new DrinkCreator();
                }else if(cat.equals(all[1])){
                    pc = new ChocolateCreator();
                }else if(cat.equals(all[2])){
                    pc = new ChipCreator();
                }else{
                    pc = new CandyCreator();
                }
            }
        }
        if(!find){
            this.getUI().displayerrorMessage("Category not valid!");
            return false;
        }
        for(String key : products.keySet()){
            Product pro = products.get(key);
            if(key.equals(name)){
                this.getUI().displayerrorMessage("Name exists!");
                return false;
            }
            if(pro.getCode().equals(code)){
                this.getUI().displayerrorMessage("Code exists!");
                return false;
            }
        }
        if (pc != null) {
            pc.create(name, code, price, quantity, 0);
        }
        this.getUI().displaySuccessString("New product successfully created.");
        return true;
    }

    public boolean removeProduct(Product product) {
        if (this.getProducts().containsKey(product.getName())) {
            this.getProducts().remove(product.getName());
            this.getUI().getFileManager().removeProduct(product);
            this.getUI().displaySuccessString("Successfully removed product " + product.getName() + ".");
            return true;
        }
        this.getUI().displayErrorString("Product " + product.getName() + " not found in vending machine. Could not be removed.");
        return false;
    }

    public void displayHelp() {
        this.getUI().displaySellerHelp();
    }

    public void reportCurrentAvailable(HashMap<String, Product> products){
        CommandLineTable st = new CommandLineTable();
        st.setHeaders("Code", "Name", "Category", "Price", "Quantity");
        for(Product p : products.values()){
            if(p.getQuantity() > 0){
                st.addRow(p.getCode(), p.getName(), p.getCategory(), Double.toString(p.getPrice()), Integer.toString(p.getQuantity()));
            }
        }
        st.print();
    }

    public void reportSellingSummary(HashMap<String, Product> products){
        CommandLineTable st = new CommandLineTable();
        st.setHeaders("Code", "Name", "Total Quantity Sold");
        for(Product p : products.values()){
            st.addRow(p.getCode(), p.getName(), Integer.toString(p.getTotalSold()));
        }
        st.print();
    }
}
