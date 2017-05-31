package view;

import databaseConnection.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Employee;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    Button signInButton;
    @FXML
    TextField signInTextField;
    @FXML
    PasswordField signInPasswordField;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //here we are going to make a call to all the singleton Lists
        //the private constructor of each is gonna populate each List from the DB.
        Customers.getInstance();
        Payments.getInstance();
        Bookings.getInstance();
        Fleet.getInstance();


    }

    @FXML
    public void signIn() throws IOException {
        String admin = signInTextField.getText();
        int pass = Integer.parseInt(signInPasswordField.getText());
        Staff staff = new Staff();
        List<Employee> list = staff.getStaff();
        if (list.get(0).identify(admin, pass)) {
            SceneManager.getInstance().loadSalesmanScene();
        } else if(list.get(1).identify(admin, pass)) {
            SceneManager.getInstance().loadBookkeeperScene();
        } else if(list.get(2).identify(admin, pass)) {
            SceneManager.getInstance().loadSalesmanScene();
        } else if(list.get(3).identify(admin, pass)) {
            SceneManager.getInstance().loadMechanicScene();
        }
        else {
            Alert wrongCredentials = new Alert(Alert.AlertType.ERROR);
            wrongCredentials.setTitle("Invalid login");
            wrongCredentials.setHeaderText("Invalid username or password.");
            wrongCredentials.setContentText(null);
            wrongCredentials.showAndWait();
        }
    }

}




