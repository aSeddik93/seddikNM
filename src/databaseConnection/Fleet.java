package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Motorhome;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a singleton, meaning there can be only one instance of this class during runtime.
 * It contains an observable list (javafx kind of list) where all  the motorhomes are stored.
 * When you first call the UNIQUE (because its a singleton) instance of this class, the constructor loads all the motorhomes from the database.
 * Created by Stefanos on 19/05/2017.
 */
public class Fleet {

    //this is the field that holds the UNIQUE instance of the Fleet.
    private static Fleet ourInstance = new Fleet();
    //this field is the observable list where all the motorhomes are stored and accessed
    private ObservableList<Motorhome> theFleetList = FXCollections.observableArrayList();

    //this is the method to call whenever you want to get access to the Fleet

    public ObservableList<Motorhome> getTheFleetList() {
        return theFleetList;
    }

    public static Fleet getInstance() {
        return ourInstance;
    }

    /**
    *this is a private constructor, the whole idea of the singleton is based on this private constructor:
    *the constructor can be called only be the ourInstance field, and its called only once per runtime.
    * This constructor loads all the motorhomes from the database to the Observable list TheFleet.
     * in case you are still in doubt call 0045 71587288
    */
    private Fleet(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from motorhome");
            while(result.next()){
                Motorhome toAdd= new Motorhome(result.getString("brand"),result.getDouble("price"),
                        result.getInt("capacity"),result.getInt("id"));
                theFleetList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        //TODO remove, this is just for debugging
        for(Motorhome m: theFleetList){
            System.out.println(m);
        }
    }
}
