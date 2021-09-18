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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ViewController.MainScreenController.productToModifyNumber;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;

public class ModifyProductController implements Initializable {

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String errorMessage = new String();
    private int productID;
    private int productNumber = productToModifyNumber();


    public Label ModifyPartTitle;
    public Label CompanyTitle;
    public Label MinTitle;
    public Label MaxTitle;
    public Label InventoryTitle;
    public Label NameTitle;
    public Label PriceTitle;
    public Label IDTitle;
    public TableView<Part> AddTable;
    public TableColumn<Part, Integer> AddPartID;
    public TableColumn<Part, String> AddPartName;
    public TableColumn<Part, Integer> AddInventoryLevel;
    public TableColumn<Part, Double> AddPrice;
    public TableView<Part> DeleteTable;
    public TableColumn<Part, Integer> DeletePartID;
    public TableColumn<Part, String> DeletePartName;
    public TableColumn<Part, Integer> DeleteInventoryLevel;
    public TableColumn<Part, Double> DeletePrice;
    public TextField PriceField;
    public TextField MinField;
    public TextField MaxField;
    public TextField InventoryField;
    public TextField NameField;
    public TextField IDField;
    public TextField SearchBox;


    public void cancelProductModify(ActionEvent actionEvent) throws IOException {
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

    public void saveProductModify(ActionEvent actionEvent) {
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

                newlyAddedProduct.setId(productID);
                newlyAddedProduct.setName(name);
                newlyAddedProduct.setPrice(Double.parseDouble(price));
                newlyAddedProduct.setStock(Integer.parseInt(stock));
                newlyAddedProduct.setMax(Integer.parseInt(max));
                newlyAddedProduct.setMin(Integer.parseInt(min));
                newlyAddedProduct.setAssociatedParts(currentParts);
                Inventory.updateProduct(productNumber, newlyAddedProduct); //this updates product instead of adding as a new product

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

    public void searchProductModify(ActionEvent actionEvent) {
        String searchForPart = SearchBox.getText();
        ObservableList foundParts = Inventory.lookupPart(searchForPart);
        if (foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("There was a problem with your search.");
            alert.setContentText("No products were found.");
            alert.showAndWait();
        } else {
            AddTable.setItems(foundParts);
        }
    }

    public void addProductModify(ActionEvent actionEvent) {
        Part part = AddTable.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateAddPartsTable(); //this displays all parts
    }

    public void deleteProductModify(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CONFIRMATION NEEDED");
        alert.setHeaderText("Confirm part delete");
        alert.setContentText("Are you sure you want to delete this part?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = DeleteTable.getSelectionModel().getSelectedItem();
                currentParts.remove(part);
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

        Product product = getAllProducts().get(productNumber);
        productID = getAllProducts().get(productNumber).getId();
        IDField.setText("" + productID);
        NameField.setText(product.getName());
        InventoryField.setText(Integer.toString(product.getStock()));
        PriceField.setText(Double.toString(product.getPrice()));
        MinField.setText(Integer.toString(product.getMin()));
        MaxField.setText(Integer.toString(product.getMax()));

        currentParts = product.getAllAssociatedParts();

        AddTable.setItems(Inventory.getAllParts());  //this displays all parts
        AddPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        AddPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AddInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        AddPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        DeleteTable.setItems(product.getAllAssociatedParts());  //this section adds cell values to delete table
        DeletePartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        DeletePartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        DeleteInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        DeletePrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        updateAddPartsTable();
        updateDeletePartsTable();

        productID = Inventory.getProductId();
        IDField.setText("" + productID); //pulls in auto generated product ID

    }
    public void updateAddPartsTable() {
        AddTable.setItems(getAllParts());
    }

    public void updateDeletePartsTable() {
        DeleteTable.setItems(currentParts);
    }

}
