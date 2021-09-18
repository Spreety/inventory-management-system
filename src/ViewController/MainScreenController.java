package ViewController;


import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.*;

public class MainScreenController implements Initializable {

    public Label AddPartTitle;
    public TableView<Part> PartsTable;
    public TableColumn<Part, Integer> mainTablePartID;
    public TableColumn<Part, String> mainTablePartName;
    public TableColumn<Part, Integer> mainTablePartInventoryLevel;
    public TableColumn<Part, Double> mainTablePartPrice;
    public TableView<Product> ProductTable;
    public TableColumn<Product, Integer> mainTableProductID;
    public TableColumn<Product, String> mainTableProductName;
    public TableColumn<Product, Integer> mainTableProductInventoryLevel;
    public TableColumn<Product, Double> mainTablePrice;
    public Label PartsLabel;
    public Label PartsLabel1;
    public TextField SearchBox2;
    public TextField SearchBox1;

    private static Part modifyParts;
    private static int modifyPartNumber;
    private static Product modifyProducts;
    private static int modifyProductNumber;
    private static int index = -1;

    public static int partToModifyNumber() {
        return modifyPartNumber;
    }

    public static int productToModifyNumber() {
        return modifyProductNumber;
    }

    public MainScreenController() {
    }

    public void exitSystem(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CONFIRM");
        alert.setHeaderText("Confirm you want to Exit");
        alert.setContentText("Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
        else {
            System.out.println("Exit has been Cancelled");
        }

    }

    public void modifyPartsInventory(ActionEvent actionEvent) throws IOException {
        modifyParts = PartsTable.getSelectionModel().getSelectedItem();
        modifyPartNumber = getAllParts().indexOf(modifyParts);
        Parent ModifyPart = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene scene = new Scene(ModifyPart);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void searchPartsInventory(ActionEvent ActionEvent) {
        String searchForPart = SearchBox2.getText();
        ObservableList foundParts = Inventory.lookupPart(searchForPart);
        if (foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("There was a problem with your search.");
            alert.setContentText("No products were found.");
            alert.showAndWait();
        } else {
            PartsTable.setItems(foundParts);
        }
    }

    public void searchProductsInventory(ActionEvent ActionEvent) {
        String searchForProduct = SearchBox1.getText();
        ObservableList foundProducts = Inventory.lookupProduct(searchForProduct);
        if(foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("There was a problem with your search.");
            alert.setContentText("No products were found.");
            alert.showAndWait();
        }
        else {
            ProductTable.setItems(foundProducts);
        }
    }

    public void addPartsInventory(ActionEvent actionEvent) throws IOException {
        Parent AddPart = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(AddPart);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void deletePartsInventory(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CONFIRMATION NEEDED");
        alert.setHeaderText("Confirm part delete");
        alert.setContentText("Are you sure you want to delete this part?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = PartsTable.getSelectionModel().getSelectedItem();
                //Inventory.deletePart(part.getId());
                deletePart(part);
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
    public void addProductInventory(ActionEvent actionEvent) throws IOException {
        Parent AddProduct = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(AddProduct);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void modifyProductsInventory(ActionEvent event) throws IOException {
        modifyProducts = ProductTable.getSelectionModel().getSelectedItem();
        modifyProductNumber = getAllProducts().indexOf(modifyProducts);
        Parent ModifyProduct = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene scene = new Scene(ModifyProduct);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void deleteProductsInventory(ActionEvent actionEvent) throws IOException{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("CONFIRMATION NEEDED");
            alert.setHeaderText("Confirm part delete");
            alert.setContentText("Are you sure you want to delete this product?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                try {
                    Product product = ProductTable.getSelectionModel().getSelectedItem();
                    deleteProduct(product);
                } catch (NullPointerException e) {
                    Alert nullAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    nullAlert.setTitle("CONFIRM DELETE");
                    nullAlert.setHeaderText("Confirm Part Delete");
                    nullAlert.setContentText("Are you sure you want to delete this product?");
                    nullAlert.showAndWait();
                }
            } else {
                alert.close();
            }
    }

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        //need to fill in anything that needs to run when initialized.

        mainTablePartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        mainTablePartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        mainTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        mainTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        updatePart();

        mainTableProductID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        mainTableProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        mainTableProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        mainTablePrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        updateProduct();

    }

    public void updatePart() {

        PartsTable.setItems(getAllParts());
    }

    public void updateProduct() {
        ProductTable.setItems(getAllProducts());
    }

    public void defaultParts() {
        int partID = Inventory.getPartId();
        Inventory.addPart(new InHouse(partID, "Band", 14.99, 10, 5, 100, 201));
        Inventory.addPart(new Outsourced(getPartId(), "Screw", 0.05, 200, 5, 500, "Screw Emporium"));
        Inventory.addPart(new InHouse(getPartId(), "Hands", 2.99, 12, 5, 100, 301));
    }

    //initializes the test parts
    public void setDefaults() {
        defaultParts();
    }
}
