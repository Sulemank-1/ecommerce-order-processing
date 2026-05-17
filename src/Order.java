import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Order {
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
        try (PrintWriter output = new PrintWriter(filename)) {
            for (Product p : orderItems) {
                if (p instanceof PhysicalProduct) {
                    PhysicalProduct pp = (PhysicalProduct) p;
                    output.println("PHYSICAL," + pp.getProductID() + "," + pp.getName() + "," + pp.getBasePrice() + "," + pp.getWeight() + "," + pp.getTaxRate());
                } else if (p instanceof DigitalProduct) {
                    DigitalProduct dp = (DigitalProduct) p;
                    output.println("DIGITAL," + dp.getProductID() + "," + dp.getName() + "," + dp.getBasePrice() + "," + dp.getDownloadLink());
                }
            }
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

        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split(",");

                String type = tokens[0];
                int id = Integer.parseInt(tokens[1]);
                String name = tokens[2];
                double price = Double.parseDouble(tokens[3]);

                switch (type) {
                    case "PHYSICAL":
                        double weight = Double.parseDouble(tokens[4]);
                        double taxRate = Double.parseDouble(tokens[5]);
                        addProduct(new PhysicalProduct(id, name, price, weight, taxRate));
                        break;

                    case "DIGITAL":
                        addProduct(new DigitalProduct(id, name, price));
                        break;
                }
            }
            System.out.println("Receipt successfully loaded from " + filename);
        } catch (InvalidProductException e) {
            System.out.println("Loading Error: Encountered corrupted or illegal product parameters. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: Critical failure parsing the receipt data. " + e.getMessage());
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
