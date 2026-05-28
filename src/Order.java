import java.io.*;
import java.util.*;

public class Order implements Serializable {
    //Data Fields
    private int orderID;
    private ArrayList<Product> orderItems;
    private final double SHIPPING_RATE_PER_KG = 2.5;

    //Constructor
    public Order(int orderID) {
        this.orderID = orderID;
        orderItems = new ArrayList<>();
    }

    //Getters
    public int getOrderID() {
        return orderID;
    }

    //Methods
    public void addProduct(Product p) {
        orderItems.add(p);
    }

    public double calculateSubtotal() {
        double sum = 0.0;
        for (Product p : orderItems)
            sum += p.getFinalPrice();
        return sum;
    }

    public double calculateShipping() {
        double sum = 0.0;
        for (Product p : orderItems)
            sum += p.getWeight();
        return sum * SHIPPING_RATE_PER_KG;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateShipping();
    }

    public void displayOrderSummary() {
        for (Product p : orderItems)
            System.out.println(p.toString());
        System.out.println("Subtotal: " + calculateSubtotal());
        System.out.println("Shipping Cost: " + calculateShipping());
        System.out.println("Grand Cost: " + calculateTotal());
    }

    public void saveReceipt(String filename) {
        try (
                ObjectOutputStream output = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream(filename)))
        ) {
            output.writeObject(orderItems);
        } catch (IOException ex) {
            System.out.println("Error: Could not save data to file");
        }
    }

    public void loadReceipt(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No existing receipt file found.");
            return;
        }

        try (
                ObjectInputStream input = new ObjectInputStream(new BufferedInputStream( new FileInputStream(file)))
        ) {
            orderItems = (ArrayList<Product>) (input.readObject());
            System.out.println("Receipt successfully loaded from " + filename);
        } catch (ClassNotFoundException ex) {
            System.out.println("Missing class definition blueprint during object reconstruction.");
        }catch (StreamCorruptedException ex) {
            System.out.println("File has been manually tampered with or corrupted! Access Blocked.");
        }catch (IOException ex){
            System.out.println("Error reading file. " + ex.getMessage());
        }
    }

    public void sortItemsByPrice() {
        if (orderItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        ArrayList<Product> sortedList = new ArrayList<>(orderItems);
        Collections.sort(sortedList);
        for (Product p : sortedList) {
            System.out.println(p + " | Final Price: $" + p.getFinalPrice());
        }
    }
}
