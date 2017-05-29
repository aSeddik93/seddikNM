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

    private List<Payment> paymentList = new ArrayList<Payment>();

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public Payments(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from payments");
            while(result.next()){
                //Payment(int id, String cardType, int cardNumber, String cardHolder, int cardCVC, String cardExpiry)
                Payment toAdd= new Payment(result.getInt(("paymentid")), result.getString("cardtype"), result.getInt("cardnumber"),
                        result.getString("cardholder"), result.getInt("cardcvc") ,result.getString("cardexpiry"),
                        result.getDouble("amount"));
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

}