package inventoryapp.model;

public abstract class Part{

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private static int idCounter = 0;

    public Part(String name, double price, int stock, int min, int max) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Part(Part part) {
        this.id = part.id;
        this.name = part.name;
        this.price = part.price;
        this.stock = part.stock;
        this.min = part.min;
        this.max = part.max;
    }

    public void setName(String name)    { this.name = name; }
    public void setPrice(double price)  { this.price = price; }
    public void setStock(int stock)     { this.stock = stock; }
    public void setMin(int min)         { this.min = min; }
    public void setMax(int max)         { this.max = max; }

    public int getId()          { return id; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public int getStock()       { return stock; }
    public int getMin()         { return min; }
    public int getMax()         { return max; }
}
