package model;

import databaseConnection.Fleet;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class Motorhome {

    private StringProperty brand = new SimpleStringProperty(this,"brand","unknown");
    private DoubleProperty price= new SimpleDoubleProperty(this, "price",0);
    private IntegerProperty nbrPersons = new SimpleIntegerProperty(this, "nbrPersons",0);

    public ObservableList<Booking> getBookingList() {
        return bookingList;
    }

    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int id;

    /**
     * this is the constructor
     */
    public Motorhome(String brand, double price, int nbrPersons) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        setListeners();
    }

    public Motorhome(String brand, double price, int nbrPersons, String status) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        this.status = status;
        setListeners();
    }

    /**
     * this is the constructor
     */
    public Motorhome(String brand, double price, int nbrPersons, int id,String status) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        this.id = id;
        this.status = status;
        setListeners();
    }
    /**
     * this is the generic constructor
     */
    public Motorhome() {
        setListeners();
    }


    double getPrice() {
        return price.get();
    }


    private int getNbrPersons() {
        return nbrPersons.get();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkAvailability(int capacity, LocalDate startDate, LocalDate endDate) {


        if(!checkCapacity(capacity)) {
            return false;
        }

        if(bookingList == null) {
            return true;
        }

        for(Booking b : bookingList) {

            if(!b.isWithinRange(startDate,endDate)) {
                return false;
            }
        }

        return true;

    }

    public boolean isDroppedOffToday() {

        for(Booking b : bookingList) {

            if (b.dropOffToday() && Objects.equals(getStatus(), "Available")) {
                return true;
            }
        }

        return false;
    }



    public void addBookingToMotorhome (Booking b) {

        bookingList.add(b);
    }

    private boolean checkCapacity(int capacity) {
        return ((capacity == getNbrPersons()));
    }

    public boolean isUnderInspection() {return Objects.equals(getStatus(), "UnderIncpection");}

    public boolean isOutOfOrder() {return Objects.equals(getStatus(), "OutOfOrder");}


    public int getIndexOfBooking(Booking booking) {
        for (Booking b : getBookingList()) {
            if (b.getId() == booking.getId()) {
                return getBookingList().indexOf(b);
            }

        }
        return -1;
    }

    public int getIndexOfActualBooking() {
        for(Booking b : bookingList) {
            if (b.isBookedNow()) {
                return bookingList.indexOf(b);
            }
        }
        return -1;
    }


    /**
     *this method should be called when a new instance (a new motorhome) is created.
     *in other words you should call this method at the last line of any constructor we make for this class.
     *EXPLANATION: this method adds "listeners" in every field of the motorhome,
     *this means that every time the state (the data, like the price or the model, or the capacity) of a motorhome changes
     *then the listeners "hear" that change and RUN a method.
     *ADVANCED: the method that runs when the listeners "hear" a change is written as a lambda expression.
     * more to be explained on comments inside the method
     */
    private void setListeners(){
        //first we take the property object that wraps the brand.
        //properties are objects that you can add listeners to.
        //check this page:
        brand.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    Fleet.getInstance().updateMotorhome(this, "brand", newValue);
                });
        price.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    String price = Double.toString(newValue.doubleValue());
                    Fleet.getInstance().updateMotorhome(this, "price", price);
                });
        nbrPersons.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    String capacity = Integer.toString(newValue.intValue());
                    Fleet.getInstance().updateMotorhome(this, "capacity", capacity);
                });

    }

    /**
     * this method is called by the TheFleet singleton,
     * whenever we launch the app, all Motorhomes are loaded from the DB
     * and constructed by the TheFleet.
     * Bookings singleton is already constructed,
     * so by iterating we get a list of
     *@param bookingsOfThisMotorhome all relevant for this Motorhome bookings.
     */
    public void setBookingList(ArrayList<Booking> bookingsOfThisMotorhome) {
        //here we get an ArrayList but the destination is an ObservableList,
        //so we pass the ArrayList (which implements the Collection Interface)
        //to the method addAll() of the ObservableList which can receive a Collection.
            bookingList.addAll(bookingsOfThisMotorhome);

    }

    /**
     * to be used later
     * @param newBooking latest booking to be added to the Motorhome's list of Bookings.
     */
    public void addBooking(Booking newBooking){
        this.bookingList.add(newBooking);
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public IntegerProperty nbrPersonsProperty() {
        return nbrPersons;
    }

    public void setNbrPersons(int nbrPersons) {
        this.nbrPersons.set(nbrPersons);
    }

    public void setBookingList(ObservableList<Booking> bookingList) {
        this.bookingList = bookingList;
    }
}
