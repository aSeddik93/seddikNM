package view;

import databaseConnection.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class BookkeeperController implements Initializable {

    public TableView playersTable;
    public TableColumn motorhomeBrand;
    public TableColumn nbrPersons;
    public TableColumn motorhomeId;
    public TableColumn motorhomePrice;
    public Button motorhomeRemoveButton;
    public TextField newBrand;
    public TextField newPrice;
    public TextField newNbrPersons;
    public Button motorhomeAddButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff= new Staff();

    }

    @FXML
    public void delete(ActionEvent event) throws IOException {
        if(event.getSource().equals(motorhomeRemoveButton)){
            SceneManager.getInstance().loadAvailableScene();
        }

        }

    @FXML
    public void add(ActionEvent event) throws IOException {
        if(event.getSource().equals(motorhomeAddButton)){
            SceneManager.getInstance().loadAvailableScene();
        }
    }
}
