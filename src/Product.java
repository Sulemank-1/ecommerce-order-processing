public abstract class Product {
    //Data Fields
    private int productID;
    private String name;
    private double basePrice;
    private double weight;

    //Constructor
    public Product(int productID, String name, double basePrice, double weight) {
        this.productID = productID;
        this.name = name;
        this.basePrice = basePrice;
        this.weight = weight;
    }

    //Getters
    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getWeight() {
        return weight;
    }

    //Setters
    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    //Methods
    public abstract double getFinalPrice();


    @Override
    public String toString() {
        return "ID: " + productID + "| Name: " + name + "| Price: " + basePrice + "| Weight: " + weight;
    }
}
