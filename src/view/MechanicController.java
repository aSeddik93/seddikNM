package view;

import databaseConnection.Fleet;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Motorhome;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 29/05/2017.
 */
public class MechanicController implements Initializable{

    public TextField kmdriven;
    public CheckBox fuel;
    public Button logout;


    public TableColumn <Motorhome,Integer> motorhomeid;

    public Button pickedUp;
    public Button pass;
    public Button fail;
    public TableColumn <Motorhome,Integer> motorhomeid1;

    public Button repaired;
    public TableView <Motorhome> UnderInspection;
    public TableColumn <Motorhome,Integer> motorhomeid2;
    public TableView <Motorhome> OutOfOrder;
    public TableView <Motorhome> dropOffsToday;


    private void initializeRentedOutTable() {

        ObservableList<Motorhome> droppedOffToday = Fleet.getInstance().setRentedOutMotorhomeList();
        motorhomeid.setCellValueFactory(new PropertyValueFactory<>("id"));
        dropOffsToday.setItems(droppedOffToday);
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

    public void pickedUpButton(ActionEvent event) {
        if (event.getSource().equals(pickedUp)) {
            Fleet.getInstance().updateMotorhome(dropOffsToday.getSelectionModel().getSelectedItem(),"status","UnderIncpection");
            SceneManager.getInstance().displayConfirmation("Confirmation","Motorhome picked up", "Motorhome is picked up and added to Under Inspection list");
        }

        initializeRentedOutTable();
        initializeUnderInspectionTable();

    }

    public void passButton(ActionEvent event) {
        if (event.getSource().equals(pass)) {
            Fleet.getInstance().updateMotorhome(UnderInspection.getSelectionModel().getSelectedItem(), "status", "Available");
            int bookingindex = UnderInspection.getSelectionModel().getSelectedItem().getIndexOfActualBooking();
            int motorhomeindex = Fleet.getInstance().getIndexOfActualMotorhome(UnderInspection.getSelectionModel().getSelectedItem().getId());
            Fleet.getInstance().getTheFleetList().get(motorhomeindex).getBookingList().get(bookingindex).addInspectionPayment(Double.valueOf(kmdriven.getText()),fuel.isSelected());
            SceneManager.getInstance().displayConfirmation("Confirmation","Motorhome passed Incpection", "Motorhome passed Incpection and is back on the system");
            initializeRentedOutTable();
            initializeUnderInspectionTable();
        }
    }

    public void failButton(ActionEvent event) {
        if (event.getSource().equals(fail)) {
            Fleet.getInstance().updateMotorhome(UnderInspection.getSelectionModel().getSelectedItem(), "status", "OutOfOrder");
            int bookingindex = UnderInspection.getSelectionModel().getSelectedItem().getIndexOfActualBooking();
            int motorhomeindex = Fleet.getInstance().getIndexOfActualMotorhome(UnderInspection.getSelectionModel().getSelectedItem().getId());
            Fleet.getInstance().getTheFleetList().get(motorhomeindex).getBookingList().get(bookingindex).addInspectionPayment(Double.valueOf(kmdriven.getText()),fuel.isSelected());
            SceneManager.getInstance().displayConfirmation("Confirmation","Motorhome failed Incpection", "Motorhome failed Incpection and is added to Out Of Order List");
            initializeUnderInspectionTable();
            initializeOutOfOrderTable();
        }
        }


    public void passButton2(ActionEvent event) {
        if (event.getSource().equals(repaired)) {
            Fleet.getInstance().updateMotorhome(OutOfOrder.getSelectionModel().getSelectedItem(), "status", "Available");
            SceneManager.getInstance().displayConfirmation("Confirmation","Motorhome Repaired", "Motorhome has been repaired and is back on the system");
            initializeOutOfOrderTable();
        }
    }



    public void logOutButton(ActionEvent event) throws IOException {
        if (event.getSource().equals(logout)) {
            SceneManager.getInstance().loadLoginScene();
        }
    }
}
