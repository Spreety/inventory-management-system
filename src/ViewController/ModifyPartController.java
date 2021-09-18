package ViewController;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;
import static ViewController.MainScreenController.*;

public class ModifyPartController implements Initializable {

    public Label AddPartTitle;
    public Label DynamicLabel;
    public Label MinTitle;
    public Label MaxTitle;
    public Label InventoryTitle;
    public Label NameTitle;
    public Label PriceTitle;
    public Label IDTitle;
    public TextField CompanyField;
    public TextField PriceField;
    public TextField MinField;
    public TextField MaxField;
    public TextField InventoryField;
    public TextField NameField;
    public TextField IDField;
    public ToggleGroup partOutOrIn;
    public RadioButton InHouseRadio;
    public RadioButton OutsourcedRadio;

    private boolean partOutsourced;
    private String partExceptionMessage = new String();
    private int partID;
    int partNumber = partToModifyNumber();

    public ModifyPartController() {
    }

    @FXML
    void OutsourcedRadioButton(ActionEvent event) {
        partOutsourced = false;
        DynamicLabel.setText("Company");
        OutsourcedRadio.setSelected(true);
        CompanyField.setPromptText("Company Name");

    }

    @FXML
    void InHouseRadioButton(ActionEvent event) {
        partOutsourced = true;
        DynamicLabel.setText("Machine ID");
        InHouseRadio.setSelected(true);
        CompanyField.setPromptText("Machine ID");
    }

    public void cancelModifyPart(ActionEvent actionEvent) throws IOException {
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

    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        String name = NameField.getText();
        String price = PriceField.getText();
        String stock = InventoryField.getText();
        String max = MaxField.getText();
        String min = MinField.getText();
        String dynamic = CompanyField.getText();

        try {
            partExceptionMessage = Part.partValidation(name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(max), Integer.parseInt(min), partExceptionMessage);
            if (partExceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Unable to add part.");
                alert.setContentText(partExceptionMessage);
                alert.showAndWait();
                partExceptionMessage = "";
            } else {
                if (partOutsourced == true) {
                    InHouse inHousePart = new InHouse();
                    inHousePart.setId(partID);
                    inHousePart.setName(name);
                    inHousePart.setPrice(Double.parseDouble(price));
                    inHousePart.setStock(Integer.parseInt(stock));
                    inHousePart.setMax(Integer.parseInt(max));
                    inHousePart.setMin(Integer.parseInt(min));
                    inHousePart.setMachineId(Integer.parseInt(dynamic));
                    Inventory.updatePart(partNumber, inHousePart); //this updates part instead of adding as a new part
                } else {
                    Outsourced outSourcedPart = new Outsourced();
                    outSourcedPart.setId(partID);
                    outSourcedPart.setName(name);
                    outSourcedPart.setPrice(Double.parseDouble(price));
                    outSourcedPart.setStock(Integer.parseInt(stock));
                    outSourcedPart.setMax(Integer.parseInt(max));
                    outSourcedPart.setMin(Integer.parseInt(min));
                    outSourcedPart.setCompanyName(dynamic);
                    Inventory.updatePart(partNumber, outSourcedPart); //this updates part instead of adding as a new part
                }
                Parent savePart = FXMLLoader.load(getClass().getResource("MainScreen.fxml")); //takes you back to main screen
                Scene scene = new Scene(savePart);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (IOException e) {

        }

    }


    @Override

    public void initialize(URL url, ResourceBundle rb) {

        Part part = getAllParts().get(partNumber);
        partID = getAllParts().get(partNumber).getId();
        IDField.setText("" + partID);
        NameField.setText(part.getName());
        InventoryField.setText(Integer.toString(part.getStock()));
        PriceField.setText(Double.toString(part.getPrice()));
        MinField.setText(Integer.toString(part.getMin()));
        MaxField.setText(Integer.toString(part.getMax()));

        if (part instanceof InHouse) {
            CompanyField.setText(Integer.toString(((InHouse) getAllParts().get(partNumber)).getMachineId()));
            DynamicLabel.setText("Machine ID");
            InHouseRadio.setSelected(true);


        } else {
            CompanyField.setText(((Outsourced) getAllParts().get(partNumber)).getCompanyName());
            DynamicLabel.setText("Company");
            OutsourcedRadio.setSelected(true);
        }

    }
}
