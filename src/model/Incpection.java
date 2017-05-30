package model;

/**
 * Created by ADMIN on 30/05/2017.
 */
public class Incpection {

    private int id;
    private double kmDriven;
    private String comments;

    public Incpection(int id, double kmDriven, String comments) {
        this.id = id;
        this.kmDriven = kmDriven;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(double kmDriven) {
        this.kmDriven = kmDriven;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
