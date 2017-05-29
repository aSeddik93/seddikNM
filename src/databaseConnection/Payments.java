package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonia on 2017/05/25.
 */
public class Payments {

    private static Payments ourInstance = new Payments();
    private List<Payment> paymentList = new ArrayList<Payment>();

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public static Payments getInstance() {
        return ourInstance;
    }

    /**
     *this is a private constructor, the whole idea of the singleton is based on this private constructor:
     *the constructor can be called only be the ourInstance field, and its called only once per runtime.
     * This constructor loads all the Payments from the database to the Observable list thePaymentList.
     * in case you are still in doubt call 0045 71587288
     */
    private Payments(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from payments");
            while(result.next()){
                //Payment(int id, String cardType, int cardNumber, String cardHolder, int cardCVC, String cardExpiry)
                Payment toAdd= new Payment(result.getInt(("paymentid")), result.getString("cardtype"), result.getInt("cardnumber"),
                        result.getString("cardholder"), result.getInt("cardcvc") ,result.getString("cardexpiry"),
                        result.getDouble("amount"), result.getInt("bookingid"));
                paymentList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        //TODO remove, this is just for debugging
        for(Payment p: paymentList){
            System.out.println(p);
        }}

    public void updatePayments(Payment toUpdate, String column, String newValue){
        DBConnector db = new DBConnector();
        try {
            db.makeUpdate("UPDATE payments SET "+column+"='"+newValue+"' WHERE id="+toUpdate.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPayment(Payments payments, String cardType, String cardNumber, String cardHolder, String cardCVC, String cardExpiry, double amount,int bookingId){
        int res = 0;
        try {
            DBConnector db = new DBConnector();
            ResultSet getId=db.makeQuery("select max(paymentid) from payments");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Payment newPayment= new Payment(cardType, cardNumber, cardHolder, cardCVC, cardExpiry, amount,bookingId);
            newPayment.setId(id);
            res = db.makeUpdate("INSERT INTO payments (paymentid,cardtype,cardnumber,cardcvc,cardholder,cardexpiry,amount,bookingid) VALUES" +
                    " ('"+id+"','"+cardType+"','"+cardNumber+"','"+cardCVC+"','"+cardHolder+"','"+cardExpiry+"','"+amount+"','"+bookingId+"')");
            List<Payment> paymentList = payments.getPaymentList();
            if(res==1) paymentList.add(newPayment);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

    /**
     * this method iterates through thePaymentList and looks for payments that
     * are relevant to a booking id.
     * @param bookingId a valid id of a Booking
     * @return a List of all the payments that their bookingId field is equal to the parameter passed.
     * in case you are still in doubt call 0045 71587288
     */
    public ArrayList<Payment> getPaymentsOfBooking(int bookingId) {
        ArrayList<Payment> listOfRelevantPayments = new ArrayList<>();
        for(Payment p: paymentList){
            if(p.getBookingId()==bookingId){
                listOfRelevantPayments.add(p);
            }
        }
        return listOfRelevantPayments;
    }
}