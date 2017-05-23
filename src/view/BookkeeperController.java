package view;

import databaseConnection.DBConnector;
import databaseConnection.Fleet;
import databaseConnection.Staff;
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
import model.Motorhome;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class BookkeeperController implements Initializable {

    private Fleet fleet = Fleet.getInstance();
    private DBConnector db = new DBConnector();

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

    public Button motorhomeRemoveButton;
    public TextField newBrand;
    public TextField newPrice;
    public TextField newNbrPersons;
    public Button motorhomeAddButton;


    public void initializeMotorhomeTable() {
        //initializes players tab

        ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();

        System.out.println(motorhomeList.get(0).getNbrPersons());
        motorhomesTable.setEditable(true);
        nbrPersons.setCellValueFactory(new PropertyValueFactory<>("nbrPersons"));
        motorhomeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        motorhomeBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        motorhomeBrand.setCellFactory(TextFieldTableCell.forTableColumn());
        motorhomePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        motorhomesTable.setItems(motorhomeList);
        //disables the delete button when there is nothing selected
        motorhomesTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) {
                motorhomeRemoveButton.setDisable(true);
            } else
                motorhomeRemoveButton.setDisable(false);
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = new Staff();
        initializeMotorhomeTable();

    }

    @FXML
    public void delete(ActionEvent event) throws IOException {

    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        if (event.getSource().equals(motorhomeAddButton)) {
            System.out.println("HERE");
            if (db.addMotorhome(fleet, newBrand.getText(), Integer.parseInt(newPrice.getText()), Double.parseDouble(newNbrPersons.getText()))) {
                System.out.println("Motorhome added");
                newBrand.clear();
                newPrice.clear();
                newNbrPersons.clear();
            } else {
                System.out.println(("Could not add Motorhome right now."));
            }
        }

    }
}


