package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Booking;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public boolean addBooking(Bookings bookings, String status, Double distance1, Double distance2, LocalDate startDate, LocalDate endDate,
                              Boolean extra1, Boolean extra2, Boolean extra3, Boolean extra4) {
        int res = 0;
        try {
            DBConnector db = new DBConnector();
            ResultSet getId=db.makeQuery("select max(bookingid) from bookings");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Booking newBooking= new Booking(status, distance1, distance2, startDate, endDate,
                    extra1, extra2, extra3, extra4);
            newBooking.setId(id);
            int ex1 = 0, ex2 = 0, ex3 = 0, ex4 =0 ;

            if (extra1 == true) { ex1 = 1;}
            if (extra2 == true) { ex2 = 1;}
            if (extra3 == true) { ex3 = 1;}
            if (extra4 == true) { ex4 = 1;}
            res = db.makeUpdate("INSERT INTO bookings (bookingid, startDate, endDate, distance1, distance2, extra1,extra2,extra3,extra4,status) VALUES" +
                    " ('"+id+"','"+startDate+"','"+endDate+"','"+distance1+"','"+distance2+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+status+"')");
            ObservableList<Booking> bookingList = bookings.getTheBookingList();
            if(res==1) bookingList.add(newBooking);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

}
