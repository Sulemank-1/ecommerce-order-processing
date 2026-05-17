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
}
