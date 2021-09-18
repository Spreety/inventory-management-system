package Model;

public abstract class Part {

    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;

    //getters and setters
    public int getId() {

        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    //Validation
    public static String partValidation(String name, double price, int stock, int min, int max, String partErrorMessage) {
        if (name == null || name.length() == 0) {
            partErrorMessage = partErrorMessage + "Please enter a name in the name field. ";
        }
        if (price <= 0.00) {
            partErrorMessage = partErrorMessage + "Price must be greater than 0.00. ";
        }
        if (stock < 1) {
            partErrorMessage = partErrorMessage + "Inventory cannot be less than 1. " ;
        }
        if (min < max) {
            partErrorMessage = partErrorMessage + "The minimum must be less than the maximum. ";
        }
        if (stock < max || stock > min) {
            partErrorMessage = partErrorMessage + "Inventory must be between the minimum and maximum values. ";
        }
        return partErrorMessage;
    }

}
