package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.*;


public class AddProductController implements Initializable {

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String errorMessage = new String();
    private int partID;
    private int productId;
    Product product = new Product();

    public Label AddPartTitle;
    public Label MinTitle;
    public Label MaxTitle;
    public Label InventoryTitle;
    public Label NameTitle;
    public Label PriceTitle;
    public Label IDTitle;
    public TextField IDField;
    public TextField NameField;
    public TextField PriceField;
    public TextField MinField;
    public TextField MaxField;
    public TextField InventoryField;
    public TableView<Part> addTable;
    public TableColumn<Part, Integer> addPartID;
    public TableColumn<Part, String> addPartName;
    public TableColumn<Part, Integer> addInventoryLevel;
    public TableColumn<Part, Double> addPrice;
    public TableView<Part> deleteTable;
    public TableColumn<Part, Integer> deletePartID;
    public TableColumn<Part, String> deletePartName;
    public TableColumn<Part, Integer> deleteInventoryLevel;
    public TableColumn<Part, Double> deletePrice;
    public TextField SearchBox;


    public void cancelAddProduct(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CONFIRMATION NEEDED");
        alert.setHeaderText("Confirm cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Parent cancelAddPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml")); //takes you back to main screen
            Scene scene = new Scene(cancelAddPart);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            alert.close();
        }
    }

    public void savePartToProduct(ActionEvent actionEvent) {
        String name = NameField.getText();
        String price = PriceField.getText();
        String stock = InventoryField.getText();
        String max = MaxField.getText();
        String min = MinField.getText();

        try {
            errorMessage = Product.productValidation(name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), errorMessage);
            if (errorMessage.length() > 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                System.out.println("Product Name: " + name);

                Product newlyAddedProduct = new Product();

                newlyAddedProduct.setId(productId);
                newlyAddedProduct.setName(name);
                newlyAddedProduct.setPrice(Double.parseDouble(price));
                newlyAddedProduct.setStock(Integer.parseInt(stock));
                newlyAddedProduct.setMax(Integer.parseInt(max));
                newlyAddedProduct.setMin(Integer.parseInt(min));
                newlyAddedProduct.setAssociatedParts(product.getAllAssociatedParts());
                Inventory.addProduct(newlyAddedProduct);

                //this takes you back to the main screen when you hit save
                Parent saveProduct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(saveProduct);

                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (IOException e) {
        }
    }

    public void searchInventory(ActionEvent actionEvent) {
        String searchForPart = SearchBox.getText();
        ObservableList foundParts = Inventory.lookupPart(searchForPart);
        if (foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("There was a problem with your search.");
            alert.setContentText("No products were found.");
            alert.showAndWait();
        } else {
            addTable.setItems(foundParts);
        }
    }

    public void addPartToProduct(ActionEvent actionEvent) {
        Part part = addTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
        addTable.setItems(Inventory.getAllParts()); //this displays all parts
        updateDeleteTable();
    }

    public void deletePartInventory(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CONFIRMATION NEEDED");
        alert.setHeaderText("Confirm part delete");
        alert.setContentText("Are you sure you want to delete this part?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = deleteTable.getSelectionModel().getSelectedItem();
                currentParts.remove(part);
                product.getAllAssociatedParts().remove(part);
                addTable.setItems(product.getAllAssociatedParts());
            } catch (NullPointerException e) {
                Alert nullAlert = new Alert(Alert.AlertType.CONFIRMATION);
                nullAlert.setTitle("CONFIRM DELETE");
                nullAlert.setHeaderText("Confirm Part Delete");
                nullAlert.setContentText("Are you sure you want to delete this part?");
                nullAlert.showAndWait();
            }
        } else {
            alert.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //need to fill in anything that needs to run when initialized.
        addTable.setItems(Inventory.getAllParts()); //this displays all parts

        addPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        deleteTable.setItems(currentParts); //this section adds cell values to delete table

        deletePartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        deletePartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        deleteInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        deletePrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        currentParts = product.getAllAssociatedParts();

        productId = Inventory.getProductId();
        IDField.setText("" + productId); //pulls in auto generated product ID
        updateAddTable();
        updateDeleteTable();

    }
    public void updateAddTable() {
        addTable.setItems(getAllParts());
    }

    public void updateDeleteTable() {
        deleteTable.setItems(currentParts);
    }
}

