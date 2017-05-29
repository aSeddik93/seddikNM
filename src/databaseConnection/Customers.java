package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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


    /**
     *this is a private constructor, the whole idea of the singleton is based on this private constructor:
     *the constructor can be called only be the ourInstance field, and its called only once per runtime.
     * This constructor loads all the customers from the database to the Observable list theCustomerList.
     * in case you are still in doubt call 0045 71587288
     */
    private Customers(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from customers");
            while(result.next()){
                Customer toAdd= new Customer(result.getString("title"),result.getString("name"),
                        result.getString("email"), result.getString("dob"),result.getInt("telephone")
                        ,result.getInt("customerid"));
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

    public boolean addCustomer(Customers customers, String title, String name, String email, LocalDate dob, String tel) {
        int res = 0;
        try {
            DBConnector db = new DBConnector();
            ResultSet getId=db.makeQuery("select max(customerid) from customers");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Customer newCustomer= new Customer(title, name, email, dob, tel);
            newCustomer.setId(id);

            res = db.makeUpdate("INSERT INTO customers (customerid, name, email, title, telephone, dob) VALUES" +
                    " ('"+id+"','"+name+"','"+email+"','"+title+"','"+tel+"','"+dob+"')");
            if(res==1) theCustomerList.add(newCustomer);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }


}