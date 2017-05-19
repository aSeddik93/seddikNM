package view;

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

    private ObservableList<Motorhome> motorhomeList;

    private Fleet fleet = Fleet.getInstance();

    @FXML
    TableView<Motorhome> motorhomesTable;
    @FXML
    TableColumn<Motorhome, String> motorhomeBrand;
    @FXML
    TableColumn<Motorhome, Integer> nbrPersons,motorhomeId;
    @FXML
    TableColumn<Motorhome, Double> motorhomePrice;

    public Button motorhomeRemoveButton;
    public TextField newBrand;
    public TextField newPrice;
    public TextField newNbrPersons;
    public Button motorhomeAddButton;



    public void initializeMotorhomeTable(){
        //initializes players tab

            motorhomeList = fleet.getTheFleetList();

        motorhomesTable.setEditable(true);
        motorhomeBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        motorhomeBrand.setCellFactory(TextFieldTableCell.forTableColumn());
        nbrPersons.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        //nbrPersons.setCellFactory(TextFieldTableCell.forTableColumn());
        motorhomePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //motorhomePrice.setCellFactory(TextFieldTableCell.forTableColumn());
        motorhomesTable.setItems(motorhomeList);
        //disables the delete button when there is nothing selected
        motorhomesTable.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
            if(newValue==null){
                motorhomeRemoveButton.setDisable(true);
            }
            else
                motorhomeRemoveButton.setDisable(false);
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff= new Staff();

    }

    @FXML
    public void delete(ActionEvent event) throws IOException {

    }

    @FXML
    public void add(ActionEvent event) throws IOException {

    }
}
