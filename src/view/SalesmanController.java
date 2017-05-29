package view;

import databaseConnection.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import model.Booking;
import model.Customer;
import model.Motorhome;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 26/05/2017.
 */
public class SalesmanController implements Initializable {


    public DatePicker customerdob1;
    DBConnector db = new DBConnector();

    public TextField newPickUp;
    public TextField newDropOff;
    public CheckBox childseat;
    public CheckBox bikerack;
    public CheckBox picnic;
    public CheckBox chairs;

    private Fleet fleet = Fleet.getInstance();
    private ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
    private Bookings bookings = Bookings.getInstance();
    private ObservableList<Booking> bookingList = bookings.getTheBookingList();
    private Customers customers = Customers.getInstance();
    private ObservableList<Customer> customerList = customers.getTheCustomerList();
    private Payments payments = new Payments();

    private final String capacity[] = {"2", "4", "6", "8"};
    private final String titles[] = {"Mr", "Ms", "Mrs"};
    private final String cardTypes[] = {"VISA", "MasterCard", "DanKort"};
    public DatePicker PickUpDate;
    public DatePicker DropOffDate;
    @FXML
    public ChoiceBox<String> numberOfPersons;
    public TextField customerName1;
    public TextField customerEmail1;
    @FXML
    public ChoiceBox<String> title;
    public TextField customerTelephone1;
    public TextField cardName;
    public TextField cardNumber;
    public TextField cardExpiry;
    @FXML
    public ChoiceBox<String> cardType;
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
    public Button search;
    public Button book;


    @FXML
    TableView<Motorhome> availablemotorhomes;
    @FXML
    TableColumn<Motorhome, Integer> motorhomeId;
    @FXML
    TableColumn<Motorhome, String> motorhomeBrand;
    @FXML
    TableColumn<Motorhome, Integer> motorhomeCapacity;
    @FXML
    TableColumn<Motorhome, Double> motorhomePrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numberOfPersons.getItems().clear();
        numberOfPersons.getItems().addAll(capacity);
        title.getItems().addAll(titles);
        cardType.getItems().addAll(cardTypes);


    }

    public ObservableList<Motorhome> availableMotorhomes(int capacity, LocalDate startDate, LocalDate endDate) {

        ObservableList<Motorhome> availableMotorhomes = FXCollections.observableArrayList();

        for(Motorhome m : motorhomeList ) {

            if (m.checkAvailability(capacity, startDate, endDate)) {
                availableMotorhomes.add(m);
            }
        }

        return availableMotorhomes;
    }

    public void selectBooking(MouseEvent mouseEvent) throws IOException {
        SceneManager.getInstance().loadBookkeeperScene();
    }


    public void bookOnAction(ActionEvent event) throws IOException {
        if (event.getSource().equals(book)) {

            Payments.getInstance().addPayment(payments, cardType.getValue(), cardNumber.getText() , cardName.getText(), cardCVC.getText(), cardExpiry.getText(),999);

            Bookings.getInstance().addBooking(bookings,"Booked", Double.parseDouble(newPickUp.getText()), Double.parseDouble(newDropOff.getText()),
                    PickUpDate.getValue(), DropOffDate.getValue(), bikerack.isSelected(), childseat.isSelected(),picnic.isSelected(),chairs.isSelected()
            );

            Customers.getInstance().addCustomer(customers, title.getValue(), customerName1.getText(), customerEmail1.getText(), customerdob1.getValue(), customerTelephone1.getText());

        }


    }

    public void searchAvailable(ActionEvent event) {
        if (event.getSource().equals(search)) {

            ObservableList<Motorhome> available = availableMotorhomes(Integer.parseInt(numberOfPersons.getValue()),
                    PickUpDate.getValue(), DropOffDate.getValue());

            motorhomeCapacity.setCellValueFactory(new PropertyValueFactory<>("nbrPersons"));
            motorhomeId.setCellValueFactory(new PropertyValueFactory<>("id"));
            motorhomeBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            motorhomeBrand.setCellFactory(TextFieldTableCell.forTableColumn());
            motorhomePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            availablemotorhomes.setItems(available);
            System.out.println("WE ARE HERREEE!!!");  //TODO remove heheheh
            for(Motorhome m : available){

                System.out.println(m.getId());
            }



        }
    }

}
