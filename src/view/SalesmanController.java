package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 26/05/2017.
 */
public class SalesmanController implements Initializable {

    final String capacity[] = {"2","4","6","8"};
    final String titles[] = {"Mr","Ms","Mrs"};
    final String cardTypes[] = {"VISA","MasterCard","DanKort"};
    public DatePicker PickUpDate;
    public DatePicker DropOffDate;
    @FXML
    public ChoiceBox <String> numberOfPersons;
    public TextField customerName1;
    public TextField customerEmail1;
    @FXML
    public ChoiceBox <String> title;
    public TextField customerTelephone1;
    public TextField cardName;
    public TextField cardNumber;
    public TextField cardExpiry;
    @FXML
    public ChoiceBox cardType;
    public TextField cardCVC;
    public TableView existingBookings;
    public TableColumn bookingID;
    public TableColumn nameColumn;
    public TableColumn emailColumn;
    public TableColumn motorhomeColumn;
    public TableColumn pickUp;
    public TableColumn dropOff;
    public TableColumn customerId;
    public TableColumn customerTitle;
    public TableColumn customerName;
    public TableColumn customerDoB;
    public TableColumn customerEmail;
    public TableColumn customerTelephone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numberOfPersons.getItems().clear();
        numberOfPersons.getItems().addAll(capacity);
        title.getItems().addAll(titles);
        cardType.getItems().addAll(cardTypes);



    }

    public void selectBooking(MouseEvent mouseEvent) throws IOException {
        SceneManager.getInstance().loadBookkeeperScene();
    }

    public void bookOnAction(ActionEvent event) throws IOException {
        SceneManager.getInstance().loadBookkeeperScene();
    }
}
