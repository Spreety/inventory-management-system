package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;


    //constructor
    public Product(int id, String name, double price, int stock, int min, int max) {

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    public Product() {

    }
    //getters and setters

    public int getId(){

        return this.id;
    }
    public void setId(int id){

        this.id = id;
    }
    public String getName(){

        return this.name;
    }
    public void  setName(String name){

        this.name = name;
    }
    public double getPrice(){

        return this.price;
    }
    public void setPrice(double price){

        this.price = price;
    }
    public int getStock(){

        return this.stock;
    }
    public void setStock(int stock){

        this.stock = stock;
    }
    public int getMin(){

        return this.min;
    }
    public void setMin(int min){

        this.min = min;
    }
    public int getMax(){
        return this.max;
    }
    public void setMax(int max){

        this.max = max;
    }

    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
         this.associatedParts = associatedParts;
     }
    public ObservableList getAllAssociatedParts() {
        return associatedParts;
    }

    //Validation Method
    public static String productValidation(String name, double price, int stock, int min, int max, String productErrorMessage) {
        if (name == null || name.length() == 0) {
            productErrorMessage = productErrorMessage + "Please enter a name in the name field. ";
        }
        if (price < 0.01) {
            productErrorMessage = productErrorMessage + "Price must be greater than 0.00. ";
        }
        if (stock < 1) {
            productErrorMessage = productErrorMessage + "Inventory cannot be less than 1. " ;
        }
        if (min > max) {
            productErrorMessage = productErrorMessage + "The minimum must be less than the maximum. ";
        }
        if (stock < min || stock > max) {
            productErrorMessage = productErrorMessage + "Inventory must be between the minimum and maximum values. ";
        }
        return productErrorMessage;
    }
}
