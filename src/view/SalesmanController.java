package view;

import databaseConnection.Bookings;
import databaseConnection.Customers;
import databaseConnection.Fleet;
import databaseConnection.Payments;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Booking;
import model.Customer;
import model.Motorhome;
import model.Payment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 26/05/2017.
 */
public class SalesmanController implements Initializable {


    public TableView <Booking> existingBookings;
    public TableColumn <Booking, Integer> bookingID;
    public TableColumn <Booking, LocalDate> endDate,startDate;
    public TableColumn <Booking, String> status;
    public TableColumn <Booking, Double> distance1, distance2;
    public TableColumn <Booking, Boolean> bikerack1, childseat1, picnictable, chairs1;
    public Button cancelBooking;


    public Button logout;


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
    private Payments payments = Payments.getInstance();


    private final String capacity[] = {"2", "4", "6", "8"};
    private final String titles[] = {"Mr", "Ms", "Mrs"};
    private final String cardTypes[] = {"VISA", "MasterCard", "DanKort"};
    public DatePicker PickUpDate;
    public DatePicker DropOffDate;
    @FXML
    public ChoiceBox<String> numberOfPersons;
    public TextField customerName1;
    public TextField customerEmail1;
    public DatePicker customerdob1;
    @FXML
    public ChoiceBox<String> title;
    public TextField customerTelephone1;
    public TextField cardName;
    public TextField cardNumber;
    public TextField cardExpiry;
    @FXML
    public Label priceLabel;
    public ChoiceBox<String> cardType;
    public TextField cardCVC;

    public TableView<Customer> customerTable;
    public TableColumn<Customer,Integer> customerId,customerTelephone;
    public TableColumn<Customer,String> customerTitle,customerName,customerEmail;
    public TableColumn<Customer,LocalDate> customerDoB;
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
    Tab bookingsTab;
    Customer currentCustomer=null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numberOfPersons.getItems().clear();
        numberOfPersons.getItems().addAll(capacity);
        title.getItems().addAll(titles);
        cardType.getItems().addAll(cardTypes);
        DropOffDate.setDisable(true);
        initializeExistingBookings();
        initializeCustomerTable();
        //will enable the dropoffdate DatePicker and allow to select only future dates.
        PickUpDate.valueProperty().addListener((v,oldValue,newValue)->{
            DropOffDate.setDisable(false);
            DropOffDate.setDayCellFactory((DropOffDate)->
                    new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(PickUpDate.getValue().plusDays(1))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    });
            if (DropOffDate.valueProperty().isNotNull().get()&&(DropOffDate.getValue().isBefore(newValue)||DropOffDate.getValue().equals(newValue)))
                DropOffDate.setValue(null);
        });
        PickUpDate.setOnAction(this::searchAvailable);
        PickUpDate.setDayCellFactory((DropOffDate)->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                });
        DropOffDate.setOnAction(this::searchAvailable);
        numberOfPersons.setOnAction(this::searchAvailable);
        newDropOff.textProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        newPickUp.textProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        bikerack.selectedProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        picnic.selectedProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        chairs.selectedProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        childseat.selectedProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        availablemotorhomes.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->{
            showPrice();
        });
        customerEmail1.textProperty().addListener((v,oldValue,newValue)->{
            boolean flag =false;
            for(Customer c: Customers.getInstance().getTheCustomerList()){
                if(c.getEmail().equalsIgnoreCase(newValue)){
                    customerName1.setText(c.getname());
                    customerName1.setEditable(false);
                    customerdob1.setValue(c.getDob());
                    customerdob1.setDisable(true);
                    title.setValue(c.getTitle());
                    title.setDisable(true);
                    customerTelephone1.setText(Integer.toString(c.getTel()));
                    customerTelephone1.setEditable(false);
                    flag=true;
                    currentCustomer=c;
                }
            }
            if(!flag){
                customerName1.setEditable(true);
                customerName1.clear();
                customerdob1.setDisable(false);
                customerdob1.setValue(null);
                title.setDisable(false);
                title.getSelectionModel().clearSelection();
                customerTelephone1.setEditable(true);
                customerTelephone1.clear();
                currentCustomer=null;
            }
        });
    }


    private ObservableList<Motorhome> availableMotorhomes(int capacity, LocalDate startDate, LocalDate endDate) {

        ObservableList<Motorhome> availableMotorhomes = FXCollections.observableArrayList();

        for(Motorhome m : motorhomeList ) {

            if (m.checkAvailability(capacity, startDate, endDate)) {
                availableMotorhomes.add(m);
            }
        }

        return availableMotorhomes;
    }

    private double showPrice(){
        if(!availablemotorhomes.getSelectionModel().isEmpty()){
            Booking tempBooking = new Booking();
            //we pass the id of the selected Motorhome
            tempBooking.setMotorhomeId(availablemotorhomes.getSelectionModel().getSelectedItem().getId());
            if(!newPickUp.textProperty().getValue().isEmpty())
                tempBooking.setDistance1(Double.parseDouble(newPickUp.getText()));
            if(!newDropOff.textProperty().getValue().isEmpty())
                tempBooking.setDistance2(Double.parseDouble(newDropOff.getText()));
            tempBooking.setExtra1(bikerack.isSelected());
            tempBooking.setExtra2(childseat.isSelected());
            tempBooking.setExtra3(picnic.isSelected());
            tempBooking.setExtra4(chairs.isSelected());
            tempBooking.setStartDate(PickUpDate.getValue());
            tempBooking.setEndDate(DropOffDate.getValue());
            tempBooking.calculatePrice();
            priceLabel.setText(Double.toString(tempBooking.getAmount())+"€");
            return tempBooking.getAmount();
        }else{
            System.out.println("Not enough data to calculate price.");
            priceLabel.setText("0.0€");
            return 0;
        }
    }

    public void bookOnAction(ActionEvent event) throws IOException {
        //checks that data are valid to make a new booking
        if (event.getSource().equals(book) && customerAndPaymentFieldsValid()) {
            int customerid;
            if (currentCustomer == null) {
                customerid = Customers.getInstance().addCustomer(customers, title.getValue(), customerName1.getText(), customerEmail1.getText(), customerdob1.getValue(), customerTelephone1.getText());
            } else {
                customerid = currentCustomer.getId();
            }
            int motorhomeid = availablemotorhomes.getSelectionModel().getSelectedItem().getId();
            double distance1;
            double distance2;
            if (newPickUp.getText().isEmpty()) {
                distance1 = 0;
            } else {
                distance1 = Double.parseDouble(newPickUp.getText());
            }
            if (newDropOff.getText().isEmpty()) {
                distance2 = 0;
            } else {
                distance2 = Double.parseDouble(newDropOff.getText());
            }
            int bookingid = Bookings.getInstance().addBooking(bookings, "Booked", distance1, distance2,
                    PickUpDate.getValue(), DropOffDate.getValue(), bikerack.isSelected(), childseat.isSelected(), picnic.isSelected(), chairs.isSelected(),
                    motorhomeid, customerid);
            double price = showPrice();
            int paymentid = Payments.getInstance().addPayment(payments, cardType.getValue(), cardNumber.getText(), cardName.getText(), cardCVC.getText(), cardExpiry.getText(), price, bookingid);

            Booking newBooking = bookings.searchBooking(bookingid);
            Payment newPayment = payments.searchPayment(paymentid);

            fleet.addBookingtToBookedMotorhome(motorhomeid, newBooking);
            bookings.addPaymentToNewBooking(bookingid, newPayment);
            book.setTooltip(null);
            SceneManager.getInstance().displayConfirmation("Confirmation","Booking saved", "Booking saved and added to the system");
        } else {
            book.setTooltip(new Tooltip("You need to fill in all the Fields in order to proceed with the booking."));
        }
    }


    private boolean customerAndPaymentFieldsValid() {
        if(!priceLabel.textProperty().getValue().equals("0.0,-")&&
                (currentCustomer!=null||
                        !title.getSelectionModel().isEmpty()&&
                                customerName1.textProperty().isNotNull().get()&&
                                customerTelephone1.textProperty().isNotNull().get()&&
                                customerdob1.valueProperty().isNotNull().get()&&
                                customerEmail1.textProperty().isNotNull().get()) &&
                !cardType.getSelectionModel().isEmpty()&&
                cardCVC.textProperty().isNotNull().get()&&
                cardExpiry.textProperty().isNotNull().get()&&
                cardName.textProperty().isNotNull().get()&&
                cardNumber.textProperty().isNotNull().get()
                ){
            return true;
        }else{
            return false;
        }
    }


    public void cancelBookingAction(ActionEvent event) throws IOException {
        if(event.getSource().equals(cancelBooking)) {

            Booking b = existingBookings.getSelectionModel().getSelectedItem();
            int motorhomeid = b.getMotorhomeId();
            int motorhomeindex = Fleet.getInstance().getIndexOfActualMotorhome(motorhomeid);
            int bookingindex = Fleet.getInstance().getTheFleetList().get(motorhomeindex).getIndexOfBooking(b);
            Fleet.getInstance().getTheFleetList().get(motorhomeindex).getBookingList().get(bookingindex).cancelBooking();
            SceneManager.getInstance().displayConfirmation("Confirmation","Booking Cancelled","Booking cancelled & system updated");

        }

    }


    private void searchAvailable(ActionEvent event) {
        availablemotorhomes.getItems().clear();
        if(!numberOfPersons.getSelectionModel().isEmpty()&&PickUpDate.valueProperty().isNotNull().get()&&DropOffDate.valueProperty().isNotNull().get()) {
            String number = numberOfPersons.getValue();
            LocalDate start = PickUpDate.getValue();
            LocalDate end = DropOffDate.getValue();
            ObservableList<Motorhome> available = availableMotorhomes(Integer.parseInt(number), start, end);

            motorhomeCapacity.setCellValueFactory(new PropertyValueFactory<>("nbrPersons"));
            motorhomeId.setCellValueFactory(new PropertyValueFactory<>("id"));
            motorhomeBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            motorhomeBrand.setCellFactory(TextFieldTableCell.forTableColumn());
            motorhomePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            availablemotorhomes.setItems(available);
            availablemotorhomes.selectionModelProperty().addListener((v,oldValue,newValue)->{
                showPrice();
            });
            for (Motorhome m : available) {
                System.out.println(m.getId());
            }
    }else{
            System.out.println("Not enough data to find available Motorhomes");
        }
    }

    private void initializeExistingBookings() {
        bookingID.setCellValueFactory(new PropertyValueFactory<>("id"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        distance1.setCellValueFactory(new PropertyValueFactory<>("distance1"));
        distance2.setCellValueFactory(new PropertyValueFactory<>("distance2"));
        bikerack1.setCellValueFactory(new PropertyValueFactory<>("extra1"));
        childseat1.setCellValueFactory(new PropertyValueFactory<>("extra2"));
        picnictable.setCellValueFactory(new PropertyValueFactory<>("extra3"));
        chairs1.setCellValueFactory(new PropertyValueFactory<>("extra4"));
        existingBookings.setItems(bookingList);
        cancelBooking.disableProperty().bind(Bindings.isEmpty(existingBookings.getSelectionModel().getSelectedItems()));

    }

    private void initializeCustomerTable() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        customerDoB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        customerTable.setItems(customerList);
    }

    public void logOutButton(ActionEvent event) throws IOException {
        if (event.getSource().equals(logout)) {
            SceneManager.getInstance().loadLoginScene();
        }
    }

}


