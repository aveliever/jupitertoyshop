package properties;

public class ToyDetails {
    private final String Item;
    private String Price;
    private final int Quantity;
    private String SubTotal;

    public ToyDetails(String item, int quantity) {
        this.Item = item;
        this.Price = "";
        this.Quantity = quantity;
        this.SubTotal = "";
    }

    public ToyDetails(String item, String price, int quantity, String subTotal) {
        this.Item = item;
        this.Price = price;
        this.Quantity = quantity;
        this.SubTotal = subTotal;
    }

    // Method to calculate SubTotal from price string
    private String CalculateSubTotal(String price, int quantity) {
        double numericPrice = Double.parseDouble(price.replace("$", ""));
        double subTotalValue = numericPrice * quantity;
        return String.format("$%.2f", subTotalValue);
    }

    // Getters
    public String getItem() {
        return Item;
    }

    public String getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    // Setters
    public void setPrice(String price) {
        this.Price = price;
    }

    public void setSubTotal(String price, int quantity) {
        this.SubTotal = CalculateSubTotal(price, quantity);
    }

    // Optionally, override the toString() method to make printing easier
    @Override
    public String toString() {
        return "ToyDetails{" +
                "Item='" + Item + '\'' +
                ", Price='" + Price + '\'' +
                ", Quantity=" + Quantity +
                ", SubTotal=" + SubTotal +
                '}';
    }

    //to check for equality based on the object's data (value equality)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // check for reference equality
        if (o == null || getClass() != o.getClass()) return false; // check for null and ensure exactly the same class

        ToyDetails that = (ToyDetails) o; // cast to ToyDetails

        // Compare all relevant fields
        if (Quantity != that.Quantity) return false;
        if (!Item.equals(that.Item)) return false;
        if (!Price.equals(that.Price)) return false;
        return SubTotal.equals(that.SubTotal);
    }
}

