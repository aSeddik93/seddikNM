package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Booking;
import model.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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

    /**
     *this is a private constructor, the whole idea of the singleton is based on this private constructor:
     *the constructor can be called only be the ourInstance field, and its called only once per runtime.
     * This constructor loads all the Bookings from the database to the Observable list theBookingList.
     * At the same time it populates every booking with a list of relevant Payment objects.
     * in case you are still in doubt call 0045 71587288
     */
    private Bookings(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from bookings");
            while(result.next()){
                int bookingId=result.getInt("bookingid");
                //construct Booking object with data from the DB.
                Booking toAdd= new Booking(bookingId, result.getString("status"),result.getDouble("distance1"),
                        result.getDouble("distance2"), result.getString("startDate"), result.getString("endDate"),
                        result.getBoolean("extra1"), result.getBoolean("extra2"), result.getBoolean("extra3"),
                        result.getBoolean("extra4"),result.getInt("customerid"),result.getInt("motorhomeid"));
                //find all relevant Payment Objects from the Payments singleton
                ArrayList<Payment> paymentsOfTheBooking= Payments.getInstance().getPaymentsOfBooking(bookingId);
                //add ArrayList of relevant payments to the Booking
                toAdd.setPaymentList(paymentsOfTheBooking);
                //add Booking to the Bookings ObservableList.
                theBookingList.add(toAdd);
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
                              Boolean extra1, Boolean extra2, Boolean extra3, Boolean extra4, int customerId, int motorhomeId) {
        int res = 0;
        try {
            DBConnector db = new DBConnector();
            ResultSet getId=db.makeQuery("select max(bookingid) from bookings");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Booking newBooking= new Booking(status, distance1, distance2, startDate, endDate,
                    extra1, extra2, extra3, extra4,customerId,motorhomeId);
            newBooking.setId(id);
            int ex1 = 0, ex2 = 0, ex3 = 0, ex4 =0 ;

            if (extra1 == true) { ex1 = 1;}
            if (extra2 == true) { ex2 = 1;}
            if (extra3 == true) { ex3 = 1;}
            if (extra4 == true) { ex4 = 1;}
            res = db.makeUpdate("INSERT INTO bookings (bookingid, startDate, endDate, distance1, distance2, extra1,extra2,extra3,extra4,status,customerid,motorhomeid) VALUES" +
                    " ('"+id+"','"+startDate+"','"+endDate+"','"+distance1+"','"+distance2+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+status+"','"+customerId+"','"+motorhomeId+"')");
            if(res==1) theBookingList.add(newBooking);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }
    /**
     * this method iterates through theBookingList and looks for bookings that
     * are relevant to a motorhome id.
     * @param motorhomeId a valid id of a Motorhome
     * @return a List of all the bookings that their motorhomeId field is equal to the parameter passed.
     * in case you are still in doubt call 0045 71587288
     */

    public ArrayList<Booking> getPaymentsOfBooking(int motorhomeId) {
        ArrayList<Booking> listOfRelevantBookings = new ArrayList<>();
        for(Booking b:theBookingList){
            if(b.getMotorhomeId()==motorhomeId){
                listOfRelevantBookings.add(b);
            }
        }
        return listOfRelevantBookings;
    }
}
