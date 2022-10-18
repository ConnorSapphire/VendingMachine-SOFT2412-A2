package VendingMachine;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class FileManager {

    private JSONObject stock, users;
    private JSONArray creditCards;

    public FileManager() {
        this.stock = (JSONObject) JfileReader("stock");
        this.users = (JSONObject) JfileReader("users");
        this.creditCards = (JSONArray) JfileReader("credit_cards");
    }

    public Object JfileReader(String filename) {
        Object obj = null;
        try {
            obj = new JSONParser()
                    .parse(new FileReader(new File("app/src/main/java/VendingMachine/" + filename + ".json")));
        } catch (FileNotFoundException e) {
            System.err.println(filename + ".json not found!");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void update() {
        this.stock = (JSONObject) JfileReader("stock");
    }

    public HashMap<String, Double[]> lsDrinks() {
        HashMap<String, Double[]> output = new HashMap<>();
        JSONObject drinks = (JSONObject) this.stock.get("Drinks");
        Iterator<Map.Entry> itr = drinks.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            JSONObject value = (JSONObject) pair.getValue();
            Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
            output.put((String) pair.getKey(), num);
        }
        return output;
    }

    public HashMap<String, Double[]> lsChocolates() {
        HashMap<String, Double[]> output = new HashMap<>();
        JSONObject chocolates = (JSONObject) this.stock.get("Chocolates");
        Iterator<Map.Entry> itr = chocolates.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            JSONObject value = (JSONObject) pair.getValue();
            Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
            output.put((String) pair.getKey(), num);
        }
        return output;
    }

    public HashMap<String, Double[]> lsChips() {
        HashMap<String, Double[]> output = new HashMap<>();
        JSONObject chips = (JSONObject) this.stock.get("Chips");
        Iterator<Map.Entry> itr = chips.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            JSONObject value = (JSONObject) pair.getValue();
            Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
            output.put((String) pair.getKey(), num);
        }
        return output;
    }

    public HashMap<String, Double[]> lsCandies() {
        HashMap<String, Double[]> output = new HashMap<>();
        JSONObject candies = (JSONObject) this.stock.get("Candies");
        Iterator<Map.Entry> itr = candies.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            JSONObject value = (JSONObject) pair.getValue();
            Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
            output.put((String) pair.getKey(), num);
        }
        return output;
    }

    public HashMap<String, String> getCreditCards() {
        HashMap<String, String> cards = new HashMap<String, String>();

        return cards;
    }
}
