package model;

import javafx.beans.property.*;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class Motorhome {

    private StringProperty brand = new SimpleStringProperty(this,"brand","unknown");
    private DoubleProperty price= new SimpleDoubleProperty(this, "price",0);
    private IntegerProperty nbrPersons = new SimpleIntegerProperty(this, "nbrPersons",0);
    private int id;

    public Motorhome(String brand, double price, int nbrPersons) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
    }

    public Motorhome(String brand, double price, int nbrPersons, int id) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        this.id = id;
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
}
