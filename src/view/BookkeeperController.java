package view;

import databaseConnection.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Booking;
import model.Customer;
import model.Motorhome;
import model.Payment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class BookkeeperController implements Initializable {

    public Button logout;

    private Fleet fleet = Fleet.getInstance();
    private ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
    private DBConnector db = new DBConnector();
    private ObservableList<Customer> customerList = Customers.getInstance().getTheCustomerList();
    private ObservableList<Booking> bookingList = Bookings.getInstance().getTheBookingList();
    private List<Payment> paymentList = Payments.getInstance().getPaymentList();

    @FXML
    TableView<Booking> BookingsTable;
    @FXML
    TableColumn<Booking, Integer> bookingId;
    @FXML
    TableColumn<Booking, LocalDate> bookingStartDate, bookingEndDate;
    @FXML
    TableColumn<Booking, Double> bookingDistance1, bookingDistance2;
    @FXML
    TableColumn<Booking, Boolean> bookingExtra1, bookingExtra2, bookingExtra3, bookingExtra4;
    @FXML
    TableColumn<Booking, String> bookingStatus;

    @FXML
    TableView<Payment> paymentsTable;
    @FXML
    TableColumn<Payment,Integer> paymentId,cardNumber,cardBookingid,cardCVC;
    @FXML
    TableColumn<Payment,Double> paymentamount;
    @FXML
    TableColumn<Payment,String> cardHolder,cardType,cardExpiry;




    @FXML
    TableView<Motorhome> motorhomesTable;
    @FXML
    TableColumn<Motorhome, Integer> motorhomeId;
    @FXML
    TableColumn<Motorhome, String> motorhomeBrand;
    @FXML
    TableColumn<Motorhome, Integer> nbrPersons;
    @FXML
    TableColumn<Motorhome, Double> motorhomePrice;
    @FXML
    TableView<Customer> customersTable;
    @FXML
    TableColumn<Customer, Integer> customerId, customerTitle, customerTel;
    @FXML
    TableColumn<Customer, String> customerName, customerEmail;

    @FXML
    TableColumn<Customer, LocalDate> costumerDoB;

    public Button motorhomeRemoveButton;
    public TextField newBrand;
    public TextField newPrice;
    public TextField newNbrPersons;
    public Button motorhomeAddButton;

    private void initializeMotorhomeTable() {
        //initializes players tab

        motorhomesTable.setEditable(true);
        nbrPersons.setCellValueFactory(new PropertyValueFactory<>("nbrPersons"));
        nbrPersons.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        motorhomeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        motorhomeBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        motorhomeBrand.setCellFactory(TextFieldTableCell.forTableColumn());
        motorhomePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        motorhomePrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        motorhomesTable.setItems(motorhomeList);
        //disables the delete button when there is nothing selected
        motorhomeRemoveButton.disableProperty().bind(Bindings.isEmpty(motorhomesTable.getSelectionModel().getSelectedItems()));
    }

    private void initializeCustomerTable() {
        //initializes players tab

        customersTable.setEditable(true);
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        costumerDoB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        customersTable.setItems(customerList);

    }

    private void initializeBookingTable() {

        bookingId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookingStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        bookingEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        bookingDistance1.setCellValueFactory(new PropertyValueFactory<>("distance1"));
        bookingDistance2.setCellValueFactory(new PropertyValueFactory<>("distance2"));
        bookingExtra1.setCellValueFactory(new PropertyValueFactory<>("extra1"));
        bookingExtra2.setCellValueFactory(new PropertyValueFactory<>("extra2"));
        bookingExtra3.setCellValueFactory(new PropertyValueFactory<>("extra3"));
        bookingExtra4.setCellValueFactory(new PropertyValueFactory<>("extra4"));
        bookingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        BookingsTable.setItems(bookingList);

    }

    private void initializePaymentTable() {

        paymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        cardHolder.setCellValueFactory(new PropertyValueFactory<>("cardHolder"));
        cardType.setCellValueFactory(new PropertyValueFactory<>("cardType"));
        cardExpiry.setCellValueFactory(new PropertyValueFactory<>("cardExpiry"));
        cardBookingid.setCellValueFactory(new PropertyValueFactory<>("cardBookingid"));
        paymentamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cardCVC.setCellValueFactory(new PropertyValueFactory<>("cardCVC"));
        paymentsTable.setItems(FXCollections.observableArrayList(paymentList));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMotorhomeTable();
        initializeCustomerTable();
        initializeBookingTable();
        initializePaymentTable();

    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        if (event.getSource().equals(motorhomeAddButton)) {
            if (db.addMotorhome(fleet, newBrand.getText(), Integer.parseInt(newNbrPersons.getText()), Double.parseDouble(newPrice.getText()))) {
                newBrand.clear();
                newPrice.clear();
                newNbrPersons.clear();
                SceneManager.getInstance().displayConfirmation("Confirmation","Motorhome added","Motorhome successfully added to the system");
            } else {
                System.out.println(("Could not add Motorhome right now."));
            }
        }

    }

    public void delete(ActionEvent event){
        if(event.getSource().equals(motorhomeRemoveButton)){
            Motorhome byeMotorhome = motorhomesTable.getSelectionModel().getSelectedItem();
            if(!db.deleteMotorhome(fleet,byeMotorhome))
                System.out.println("Could not delete Motorhome right now.");
            else {
                SceneManager.getInstance().displayConfirmation("Confirmation", "Motorhome remove", "Motorhome successfully removed from the system");
            }

        }
    }

    public void logOutButton(ActionEvent event) throws IOException {
        if (event.getSource().equals(logout)) {
            SceneManager.getInstance().loadLoginScene();
        }
    }
}


