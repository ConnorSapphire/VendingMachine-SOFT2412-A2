package VendingMachine;

public class Drink extends Product {
    /**
     * 
     * @param name
     * @param code
     * @param price
     * @param quantity
     */
    public Drink(String name, String code, double price, int quantity, int totalSold) {
        super(name, code, "drink", price, quantity, totalSold);
    }
    
}
