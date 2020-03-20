package inventoryapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private static int idCounter = 0;

    public Product(String name, double price, int stock, int min, int max) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }

    public Product(Product product) {
        id              = product.id;
        name            = product.name;
        price           = product.price;
        stock           = product.stock;
        min             = product.min;
        max             = product.max;
        associatedParts = FXCollections.observableArrayList(product.associatedParts);
    }

    public void setName(String name)    { this.name = name; }
    public void setPrice(double price)  { this.price = price; }
    public void setStock(int stock)     { this.stock = stock; }
    public void setMin(int min)         { this.min = min; }
    public void setMax(int max)         { this.max = max; }
    public void addAssociatedPart(Part part) { associatedParts.add(part); }
    public void addListOfAssociatedParts(ObservableList<Part> parts) { associatedParts.addAll(parts); }

    public int getId()          { return id; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public int getStock()       { return stock; }
    public int getMin()         { return min; }
    public int getMax()         { return max; }

    public double getPartsPriceTotal() {
        double total = 0;
        for (Part part : associatedParts) {
            total += part.getPrice();
        }
        return total;
    }

    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
    public boolean deleteAssociatedPart(Part selectedPart) { return associatedParts.remove(selectedPart); }
    public boolean deleteAllAssociatedParts() { return associatedParts.removeAll(Inventory.getAllParts()); }

}
