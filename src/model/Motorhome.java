package model;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class Motorhome {

    private String brand;
    private double price;
    private int nbrPersons, id;

    public Motorhome(String brand, double price, int nbrPersons) {
        this.brand = brand;
        this.price = price;
        this.nbrPersons = nbrPersons;
    }

    public Motorhome(String brand, double price, int nbrPersons, int id) {
        this.brand = brand;
        this.price = price;
        this.nbrPersons = nbrPersons;
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNbrPersons() {
        return nbrPersons;
    }

    public void setNbrPersons(int nbrPersons) {
        this.nbrPersons = nbrPersons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
