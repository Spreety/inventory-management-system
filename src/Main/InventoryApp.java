package Main;

import Model.*;
import ViewController.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class InventoryApp extends Application {

    private Stage mainStage;

    @Override

    public void start(Stage stage) throws IOException {
        this.mainStage = stage;
        this.mainStage.setTitle("Inventory Management System");
        showMainScreen(stage);
    }


    public void showMainScreen(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(InventoryApp.class.getResource("/ViewController/MainScreen.fxml"));
        Parent part_page_parent = (AnchorPane) loader.load();

        MainScreenController controller = loader.getController();  //pulls from MainScreenController
        controller.setDefaults();
        controller.updatePart();
        controller.updateProduct();
        Scene part_page_scene = new Scene(part_page_parent);
        stage.setScene(part_page_scene);
        stage.show();
    }

    public static void main(String[] args) {


        launch(args);
    }
}

