package databaseConnection;

import javafx.collections.ObservableList;
import model.Booking;
import model.Customer;
import model.Motorhome;
import model.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * This class can be used whenever we need a connection to the Database
 * We can make two types of interactions with the database,
 * queries that return a result set
 * and updates that return an integer
 * more information on the methods description
 * Created by Stefanos on 19/05/2017.
 */
public class DBConnector {

    private Connection conn = null;

    /**
     * this is the generic constructor of the class. its empty because we don't need it to do anything yet.
     */
    public DBConnector() {

    }

    /**
     * Use this method whenever you wanna get data from the database.
     * @param query an SQL command as a string ex. "SELECT * FROM fleet"
     * @return a resultset object, have a look at this to understand how to use it:
     * http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
     * REMEMBER you have to close the connection after reading the ResultSet,
     * when you close the connection the ResultSet disappears
     * @throws SQLException handle this appropriately informing the user when necessary
     */
    public ResultSet makeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);
        return res;
    }

    /**
     * Use this method after every query in order to close the connection and avoid problems
     * with other parts of the application trying to connect with the database.
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *Use this method whenever you want to insert data to the database
     * or delete data. You cannot get data with this method.
     * The connection is closed automatically.
     * @param s an SQL command as a string ex. "DELETE * FROM fleet"
     * @return an integer, if "1" then everything went OK.
     * @throws SQLException handle this appropriately informing the user when necessary
     */
    public int makeUpdate(String s) throws SQLException {
        Connection conn = getConnection();
        int val =conn.createStatement().executeUpdate(s);
        if(val==1)
            System.out.println("Successfully inserted value");
        conn.close();
        return val;
    }

    /**
     * this method can be used only be the DBConnector object,
     * it is called everytime we call the makeUpdate() and makeQuery()
     * as it consumes resources we should close it after using it.
     * @return a Connection object to our database
     * have a look at this for more info : https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html
     */
    private Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "nordicmotorhomes";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url+dbName,userName,password);
            System.out.println("We got the connection"); //TODO remove, this was used for debugging
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean addMotorhome(Fleet fleet, String brand, int capacity, double price) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(motorhomeid) from motorhome");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Motorhome newMotorhome= new Motorhome(brand,price,capacity);
            newMotorhome.setId(id);
            res = makeUpdate("INSERT INTO motorhome (motorhomeid, capacity, price, brand) VALUES ('"+id+"','"+capacity+"','"+price+"','"+brand+"')");
            ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
            if(res==1)motorhomeList.add(newMotorhome);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

    public boolean deleteMotorhome(Fleet fleet, Motorhome byeMotorhome) {
        try {
            boolean flag= makeUpdate("DELETE FROM motorhome WHERE motorhomeid="+byeMotorhome.getId())==1;
            ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
            if(flag) motorhomeList.remove(byeMotorhome);
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public boolean addBooking(Bookings bookings, String status, Double distance1, Double distance2, LocalDate startDate, LocalDate endDate,
                               Boolean extra1, Boolean extra2, Boolean extra3, Boolean extra4) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(bookingid) from bookings");
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
            res = makeUpdate("INSERT INTO bookings (bookingid, startDate, endDate, distance1, distance2, extra1,extra2,extra3,extra4,status) VALUES" +
                    " ('"+id+"','"+startDate+"','"+endDate+"','"+distance1+"','"+distance2+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+status+"')");
            ObservableList<Booking> bookingList = bookings.getTheBookingList();
            if(res==1) bookingList.add(newBooking);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

    public boolean addPayment(Payments payments, String cardType, String cardNumber, String cardHolder, String cardCVC, String cardExpiry, double amount) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(paymentid) from payments");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Payment newPayment= new Payment(cardType, cardNumber, cardHolder, cardCVC, cardExpiry, amount);
            newPayment.setId(id);
            res = makeUpdate("INSERT INTO payments (paymentid,cardtype,cardnumber,cardcvc,cardholder,cardexpiry,amount) VALUES" +
                    " ('"+id+"','"+cardType+"','"+cardNumber+"','"+cardCVC+"','"+cardHolder+"','"+cardExpiry+"','"+amount+"')");
            List<Payment> paymentList = payments.getPaymentList();
            if(res==1) paymentList.add(newPayment);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }





   public boolean addCustomer(Customers customers, String title, String name, String email,LocalDate dob, String tel) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(customerid) from customers");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Customer newCustomer= new Customer(title, name, email, dob, tel);
            newCustomer.setId(id);

            res = makeUpdate("INSERT INTO customers (customerid, name, email, title, telephone, dob) VALUES" +
                    " ('"+id+"','"+name+"','"+email+"','"+title+"','"+tel+"','"+dob+"')");
            ObservableList<Customer> customerList = customers.getTheCustomerList();
            if(res==1) customerList.add(newCustomer);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }


}
