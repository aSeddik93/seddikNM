package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ADMIN on 16-05-2017.
 */
public class SceneManager {
    private static SceneManager instance;

    private SceneManager(){}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private Stage primaryStage;
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void loadLoginScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
    }

    void loadSalesmanScene() throws IOException{
        Parent salesmanRoot = FXMLLoader.load(getClass().getResource("/view/salesman.fxml"));
        Scene salesmanScene = new Scene(salesmanRoot, 900, 575);
        primaryStage.setScene(salesmanScene);
    }

    void loadAvailableScene() throws IOException{
        Parent availableRoot = FXMLLoader.load(getClass().getResource("/view/available.fxml"));
        Scene availableScene = new Scene(availableRoot, 900, 575);
        primaryStage.setScene(availableScene);
    }

    void loadCarDetailsScene() throws IOException{
        Parent carDetailsRoot = FXMLLoader.load(getClass().getResource("/view/car.fxml"));
        Scene carDetailsScene = new Scene(carDetailsRoot, 900, 575);
        primaryStage.setScene(carDetailsScene);
    }

    void loadCustomerDetailsScene() throws IOException{
        Parent customerDetailsRoot = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        Scene customerDetailsScene = new Scene(customerDetailsRoot, 900, 575);
        primaryStage.setScene(customerDetailsScene);
    }

    void loadBillingDetailsScene() throws IOException{
        Parent billingDetailsRoot = FXMLLoader.load(getClass().getResource("/view/billing.fxml"));
        Scene billingDetailsScene = new Scene(billingDetailsRoot, 900, 575);
        primaryStage.setScene(billingDetailsScene);
    }

    void loadConfirmationDetailsScene() throws IOException{
        Parent confirmationDetailsRoot = FXMLLoader.load(getClass().getResource("/view/confirmation.fxml"));
        Scene confirmationDetailsScene = new Scene(confirmationDetailsRoot, 900, 575);
        primaryStage.setScene(confirmationDetailsScene);
    }

    void loadBookkeeperScene() throws IOException{
        Parent bookkeeperRoot = FXMLLoader.load(getClass().getResource("/view/bookkeeper.fxml"));
        Scene bookkeeperScene = new Scene(bookkeeperRoot, 900, 575);
        primaryStage.setScene(bookkeeperScene);
    }
}
