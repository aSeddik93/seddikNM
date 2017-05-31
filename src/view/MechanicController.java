package view;

import databaseConnection.Bookings;
import databaseConnection.DBConnector;
import databaseConnection.Fleet;
import databaseConnection.Payments;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Motorhome;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 29/05/2017.
 */
public class MechanicController implements Initializable{

    public TextField kmdriven;
    public CheckBox fuel;
    private Fleet fleet = Fleet.getInstance();
    private List<Motorhome> motorhomeList = fleet.getTheFleetList();
    private DBConnector db = new DBConnector();



    public TableColumn <Motorhome,Integer> motorhomeid;
    public Button notReturned;
    public Button pickedUp;
    public Button pass;
    public Button fail;
    public TableColumn <Motorhome,Integer> motorhomeid1;
    public TableColumn <Motorhome,Integer> comments;
    public Button repaired;
    public TableView <Motorhome> UnderInspection;
    public TableColumn <Motorhome,Integer> motorhomeid2;
    public TableView <Motorhome> OutOfOrder;
    public TableView <Motorhome> dropOffsToday;


    private void initializeRentedOutTable() {

        ObservableList<Motorhome> droppedOffToday = Fleet.getInstance().setRentedOutMotorhomeList();
        motorhomeid.setCellValueFactory(new PropertyValueFactory<>("id"));
        dropOffsToday.setItems(droppedOffToday);

        notReturned.disableProperty().bind(Bindings.isEmpty(dropOffsToday.getSelectionModel().getSelectedItems()));
        pickedUp.disableProperty().bind(Bindings.isEmpty(dropOffsToday.getSelectionModel().getSelectedItems()));
    }

    private void initializeUnderInspectionTable() {

        ObservableList<Motorhome> underInspection = Fleet.getInstance().seUnderInspectionMotorhomeList();
        motorhomeid1.setCellValueFactory(new PropertyValueFactory<>("id"));
        UnderInspection.setItems(underInspection);
        pass.disableProperty().bind(Bindings.isEmpty(UnderInspection.getSelectionModel().getSelectedItems()));
        fail.disableProperty().bind(Bindings.isEmpty(UnderInspection.getSelectionModel().getSelectedItems()));
}

    private void initializeOutOfOrderTable() {

        ObservableList<Motorhome> outOfOrder = Fleet.getInstance().seOutOfOrderMotorhomeList();
        motorhomeid2.setCellValueFactory(new PropertyValueFactory<>("id"));
        OutOfOrder.setItems(outOfOrder);
        repaired.disableProperty().bind(Bindings.isEmpty(OutOfOrder.getSelectionModel().getSelectedItems()));
    }


    public void initialize(URL location, ResourceBundle resources) {

        initializeRentedOutTable();
        initializeUnderInspectionTable();
        initializeOutOfOrderTable();


    }


    public void notReturnedButton(ActionEvent event) {
        if (event.getSource().equals(notReturned)) {
            Fleet.getInstance().updateMotorhome(dropOffsToday.getSelectionModel().getSelectedItem(),"status","NotReturned");


    }


    }

    public void pickedUpButton(ActionEvent event) {
        if (event.getSource().equals(pickedUp)) {
            Fleet.getInstance().updateMotorhome(dropOffsToday.getSelectionModel().getSelectedItem(),"status","UnderIncpection");
        }
    }

    public void passButton(ActionEvent event) {
        if (event.getSource().equals(pass)) {
            Fleet.getInstance().updateMotorhome(UnderInspection.getSelectionModel().getSelectedItem(), "status", "Available");
            int bookingindex = UnderInspection.getSelectionModel().getSelectedItem().getIndexOfActualBooking();
            int motorhomeindex = Fleet.getInstance().getIndexOfActualMotorhome(UnderInspection.getSelectionModel().getSelectedItem().getId());
            Fleet.getInstance().getTheFleetList().get(motorhomeindex).getBookingList().get(bookingindex).addInspectionPayment(Double.valueOf(kmdriven.getText()),fuel.isSelected());

        }
    }

    public void failButton(ActionEvent event) {
        if (event.getSource().equals(fail)) {
            Fleet.getInstance().updateMotorhome(UnderInspection.getSelectionModel().getSelectedItem(), "status", "OutOfOrder");
            int bookingindex = UnderInspection.getSelectionModel().getSelectedItem().getIndexOfActualBooking();
            int motorhomeindex = Fleet.getInstance().getIndexOfActualMotorhome(UnderInspection.getSelectionModel().getSelectedItem().getId());
            Fleet.getInstance().getTheFleetList().get(motorhomeindex).getBookingList().get(bookingindex).addInspectionPayment(Double.valueOf(kmdriven.getText()),fuel.isSelected());

        }
    }

    public void passButton2(ActionEvent event) {
    }
}
