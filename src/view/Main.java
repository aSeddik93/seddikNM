package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneManager.getInstance().setStage(primaryStage);
        primaryStage.setTitle("Nordic Motor Homes");
        SceneManager.getInstance().loadLoginScene();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        Scene scene = new Scene(root);
        String css = Main.class.getResource("loginStyle.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}


