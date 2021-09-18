package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;

    public Inventory() {
    }


    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void addPart(Part part) {

        allParts.add(part);
    }

    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static int getPartId() {
        partId++;
        return partId;
    }

  public static ObservableList lookupPart(String searchTerm) {
      ObservableList<Part> foundParts = FXCollections.observableArrayList();

      if(searchTerm.length() == 0) {
          foundParts = allParts;
      } else {
          for (int i = 0; i < allParts.size(); i++) {
              if (allParts.get(i).getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                  foundParts.add(allParts.get(i));
              }
          }
      }

      return foundParts;
  }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public static void addProduct(Product product) {

        allProducts.add(product);
    }

    public static void updateProduct(int index, Product product) {

        allProducts.set(index, product);
    }

    public static int getProductId() {
        productId++;
        return productId;
    }

    public static ObservableList lookupProduct(String searchTerm) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if(searchTerm.length() == 0) {
            foundProducts = allProducts;
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    foundProducts.add(allProducts.get(i));
                }
            }
        }

        return foundProducts;
    }
}
