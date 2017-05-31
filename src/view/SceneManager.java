package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void loadLoginScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        String css = Main.class.getResource("loginStyle.css").toExternalForm();
        root.getStylesheets().add(css);
    }

    void loadSalesmanScene() throws IOException{
        Parent salesmanRoot = FXMLLoader.load(getClass().getResource("/view/salesman.fxml"));
        Scene salesmanScene = new Scene(salesmanRoot, 850, 675);
        primaryStage.setScene(salesmanScene);
        String css = Main.class.getResource("loginStyle.css").toExternalForm();
        salesmanRoot.getStylesheets().add(css);
    }

    void loadBookkeeperScene() throws IOException{
        Parent bookkeeperRoot = FXMLLoader.load(getClass().getResource("/view/bookkeeper.fxml"));
        Scene bookkeeperScene = new Scene(bookkeeperRoot, 900, 575);
        primaryStage.setScene(bookkeeperScene);
        String css = Main.class.getResource("loginStyle.css").toExternalForm();
        bookkeeperRoot.getStylesheets().add(css);
    }

    void loadMechanicScene() throws IOException{
        Parent mechanicRoot = FXMLLoader.load(getClass().getResource("/view/mechanic.fxml"));
        Scene mechanicScene = new Scene(mechanicRoot, 900, 575);
        primaryStage.setScene(mechanicScene);
        String css = Main.class.getResource("loginStyle.css").toExternalForm();
        mechanicRoot.getStylesheets().add(css);
    }




    void displayConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || result.get() != ButtonType.OK) {
        }
    }
}
