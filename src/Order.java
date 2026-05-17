import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

}
