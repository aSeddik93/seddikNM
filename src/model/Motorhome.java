package model;

import databaseConnection.DBConnector;
import databaseConnection.Fleet;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class Motorhome {

    private StringProperty brand = new SimpleStringProperty(this,"brand","unknown");
    private DoubleProperty price= new SimpleDoubleProperty(this, "price",0);
    private IntegerProperty nbrPersons = new SimpleIntegerProperty(this, "nbrPersons",0);
    private ObservableList<Booking> bookingList = null;
    private String status;


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

    /**
     * this is the constructor
     */
    public Motorhome(String brand, double price, int nbrPersons, int id) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        this.id = id;
        setListeners();
    }
    /**
     * this is the generic constructor
     */
    public Motorhome() {
        setListeners();
    }


    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getNbrPersons() {
        return nbrPersons.get();
    }

    public IntegerProperty nbrPersonsProperty() {
        return nbrPersons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkAvailability(int capacity, LocalDate startDate, LocalDate endDate) {


        if(!checkCapacity(capacity)) {
            System.out.println("WHAT'S UP2");
            return false;
        }

        if(bookingList == null) {
            return true;
        }

        for(Booking b : bookingList) {

            if(!b.isWithinRange(startDate,endDate)) {
                System.out.println("WHAT'S UP3");
                return false;
            }
        }

        return true;

    }

    boolean checkCapacity(int capacity) {
        return ((capacity == getNbrPersons()));
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
}
