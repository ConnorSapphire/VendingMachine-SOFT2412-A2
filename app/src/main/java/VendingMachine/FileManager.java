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

    public FileManager(){
        this.users = (JSONObject) JfileReader("users");
        this.stock = (JSONObject) JfileReader("stock");
        this.creditCards = (JSONArray) JfileReader("credit_cards");
    }

    public Object JfileReader(String filename) {
        Object obj = null;
        try {
            obj = new JSONParser()
                    .parse(new FileReader(new File("src/main/java/VendingMachine/" + filename + ".json")));
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
        this.users = (JSONObject) JfileReader("users");
        this.stock = (JSONObject) JfileReader("stock");
        this.creditCards = (JSONArray) JfileReader("credit_cards");
    }

    public HashMap<String[], Double[]> lsDrinks() {
        HashMap<String[], Double[]> output = new HashMap<>();
        JSONArray drinks = (JSONArray) this.stock.get("Drinks");
        for (Object obj : drinks) {
            JSONObject drink = (JSONObject) obj;
            for (Object objKey : drink.keySet()) {
                String key = (String) objKey;
                JSONObject value = (JSONObject) drink.get(key);
                Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
                String[] str = new String[]{key, (String) value.get("code")};
                output.put(str, num);
            }
        }
        return output;
    }

    public HashMap<String[], Double[]> lsChocolates() {
        HashMap<String[], Double[]> output = new HashMap<>();
        JSONArray chocolates = (JSONArray) this.stock.get("Chocolates");
        for (Object obj : chocolates) {
            JSONObject chocolate = (JSONObject) obj;
            for (Object objKey : chocolate.keySet()) {
                String key = (String) objKey;
                JSONObject value = (JSONObject) chocolate.get(key);
                Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
                String[] str = new String[]{key, (String) value.get("code")};
                output.put(str, num);
            }
        }
        return output;
    }

    public HashMap<String[], Double[]> lsChips() {
        HashMap<String[], Double[]> output = new HashMap<>();
        JSONArray chips = (JSONArray) this.stock.get("Chips");
        for (Object obj : chips) {
            JSONObject chip = (JSONObject) obj;
            for (Object objKey : chip.keySet()) {
                String key = (String) objKey;
                JSONObject value = (JSONObject) chip.get(key);
                Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
                String[] str = new String[]{key, (String) value.get("code")};
                output.put(str, num);
            }
        }
        return output;
    }

    public HashMap<String[], Double[]> lsCandies() {
        HashMap<String[], Double[]> output = new HashMap<>();
        JSONArray candies = (JSONArray) this.stock.get("Candies");
        for (Object obj : candies) {
            JSONObject candy = (JSONObject) obj;
            for (Object objKey : candy.keySet()) {
                String key = (String) objKey;
                JSONObject value = (JSONObject) candy.get(key);
                Double[] num = new Double[] { (Double) value.get("price"), (Double) value.get("quantity") };
                String[] str = new String[]{key, (String) value.get("code")};
                output.put(str, num);
            }
        }
        return output;
    }

    public HashMap<String, String> getCreditCards() {
        HashMap<String, String> cards = new HashMap<String, String>();
        Iterator iteratorr = creditCards.iterator();
        while (iteratorr.hasNext()) {
            JSONObject obj = (JSONObject) iteratorr.next(); 
            cards.put(obj.get("name").toString(), obj.get("number").toString()); 
        }
        return cards;
    }

    public ArrayList<String> getLastTransactions(String username){
        ArrayList<String> trans = new ArrayList<>();
        JSONObject value = (JSONObject) users.get(username);
        JSONArray transaction = (JSONArray) value.get("transaction");
        Iterator itr = transaction.iterator();
        while (itr.hasNext()) {
            trans.add((String)itr.next());
        }
        return trans;
    }
}
