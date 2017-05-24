package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Antonia on 19-05-2017.
 */

public class Customers {


    private static Customers ourInstance = new Customers();
    private ObservableList<Customer> theCustomerList = FXCollections.observableArrayList();

    public ObservableList<Customer> getTheCustomerList() {
        return theCustomerList;
    }

    public static Customers getInstance() {
        return ourInstance;
    }

    private Customers(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from customers");
            while(result.next()){
                Customer toAdd= new Customer(result.getString("title"),result.getString("name"),
                        result.getString("email"), result.getString("dob"),result.getInt("telephone")
                        ,result.getInt("id"));
                final boolean add = theCustomerList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        //TODO remove, this is just for debugging
        for(Customer c: theCustomerList){
            System.out.println(c);
        }}

    public void updateCustomers(Customer toUpdate, String column, String newValue){
        DBConnector db = new DBConnector();
        try {
            db.makeUpdate("UPDATE customer SET "+column+"='"+newValue+"' WHERE id="+toUpdate.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}