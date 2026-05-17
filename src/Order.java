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

    public void addProduct(Product p){
        orderItems.add(p);
    }

    public double calculateSubtotal(){
        double sum = 0.0;
        for (Product p: orderItems)
            sum += p.getBasePrice();
        return sum;
    }

    public double calculateShipping(){
        double sum = 0.0;
        for (Product p: orderItems)
            sum += p.getWeight();
        return sum * SHIPPING_RATE_PER_KG;
    }


}
