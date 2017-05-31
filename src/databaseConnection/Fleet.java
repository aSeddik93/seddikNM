package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Booking;
import model.Motorhome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * it also locates all relevant Bookings of a Motorhome and adds them to the Motorhome's instance
    */
    private Fleet(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from motorhome");
            while(result.next()){
                //construct Motorhome object with data from the DB.
                int motorhomeId=result.getInt("motorhomeid");
                Motorhome toAdd= new Motorhome(result.getString("brand"),result.getDouble("price"),
                        result.getInt("capacity"),motorhomeId,result.getString("status"));
                //find all relevant Booking Objects from the Bookings singleton
                ArrayList<Booking> bookingsOfThisMotorhome= Bookings.getInstance().getBookingsOfMotorhome(motorhomeId);
                //add ArrayList of relevant Bookings to the Motorhome
                    toAdd.setBookingList(bookingsOfThisMotorhome);
                //add Booking to the Bookings ObservableList.
                theFleetList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
    }

    public void updateMotorhome(Motorhome toUpdate, String column, String newValue){
        DBConnector db = new DBConnector();
        try {
            db.makeUpdate("UPDATE motorhome SET "+column+"='"+newValue+"' WHERE motorhomeid="+toUpdate.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBookingtToBookedMotorhome(int id, Booking booking) {

        for(Motorhome m : getTheFleetList()) {

            if(m.getId() == id) {

                m.addBookingToMotorhome(booking);
            }
        }

    }

    public ObservableList<Motorhome> setRentedOutMotorhomeList() {

        ObservableList<Motorhome> rentedOut = FXCollections.observableArrayList();
        for (Motorhome m : getTheFleetList())
        {
            if (m.isDroppedOffToday()) {
                rentedOut.add(m);
            }
        }

        return rentedOut;
    }

    public ObservableList<Motorhome> seUnderInspectionMotorhomeList() {

        ObservableList<Motorhome> underInspection = FXCollections.observableArrayList();
        for (Motorhome m : getTheFleetList())
        {
            if (m.isUnderInspection()) {
                underInspection.add(m);
            }
        }

        return underInspection;
    }

    public ObservableList<Motorhome> seOutOfOrderMotorhomeList() {

        ObservableList<Motorhome> outOfOrder = FXCollections.observableArrayList();
        for (Motorhome m : getTheFleetList())
        {
            if (m.isOutOfOrder()) {
                outOfOrder.add(m);
            }
        }

        return outOfOrder;
    }

    public Motorhome searchById(int motorhomeid) {
        for(Motorhome m: theFleetList){
            if(m.getId()==motorhomeid){
                return m;
            }
        }
        return null;
    }

    public int getIndexOfActualMotorhome(int motorhomeid){
        for(Motorhome m : getTheFleetList()) {
            if(m.getId() == motorhomeid)
            {
                return getTheFleetList().indexOf(m);
            }
        }

        return -1;
    }
}
