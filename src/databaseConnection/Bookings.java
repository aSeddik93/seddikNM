package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Booking;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ADMIN on 24-05-2017.
 */
public class Bookings {

    private static Bookings ourInstance = new Bookings();
    private ObservableList<Booking> theBookingList = FXCollections.observableArrayList();

    public ObservableList<Booking> getTheBookingList() {
        return theBookingList;
    }

    public static Bookings getInstance() {
        return ourInstance;
    }

    private Bookings(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from bookings");
            while(result.next()){
                Booking toAdd= new Booking(result.getInt("bookingid"), result.getString("status"),result.getDouble("distance1"),
                        result.getDouble("distance2"), result.getString("startDate"), result.getString("endDate"),
                        result.getBoolean("extra1"), result.getBoolean("extra2"), result.getBoolean("extra3"),
                        result.getBoolean("extra4"));
                final boolean add = theBookingList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        //TODO remove, this is just for debugging
        for(Booking b: theBookingList){
            System.out.println(b);
        }
    }

    public void updateBookings(Booking toUpdate, String column, String newValue){
        DBConnector db = new DBConnector();
        try {
            db.makeUpdate("UPDATE customer SET "+column+"='"+newValue+"' WHERE id="+toUpdate.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
