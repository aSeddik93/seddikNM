package model;

import databaseConnection.Bookings;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by ADMIN on 24-05-2017.
 */
public class Booking {

    private int id;



    private double amount;

    private StringProperty status = new SimpleStringProperty(this, "title", "unknown");
    private DoubleProperty distance1 = new SimpleDoubleProperty(this, "distance1", -1);
    private DoubleProperty distance2 = new SimpleDoubleProperty(this, "distance1", -1);
    private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>(this, "startDate", null);
    private ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>(this, "endDate", null);
    private BooleanProperty extra1 = new SimpleBooleanProperty(this, "extra1", false);
    private BooleanProperty extra2 = new SimpleBooleanProperty(this, "extra2", false);
    private BooleanProperty extra3 = new SimpleBooleanProperty(this, "extra3", false);
    private BooleanProperty extra4 = new SimpleBooleanProperty(this, "extra4", false);
    private Motorhome bookedMotorhome = null;

    public Booking(int id, String status, Double distance1, Double distance2, String startDate, String endDate,
                   Boolean extra1, Boolean extra2, Boolean extra3, Boolean extra4) {
        this.id = id;
        this.status.setValue(status);
        this.distance1.setValue(distance1);
        this.distance2.setValue(distance2);
        this.startDate.setValue(LocalDate.parse(startDate));
        this.endDate.setValue(LocalDate.parse(endDate));
        this.extra1.setValue(extra1);
        this.extra2.setValue(extra2);
        this.extra3.setValue(extra3);
        this.extra4.setValue(extra4);
        setAmount(999);
    }

    public Booking(String status, Double distance1, Double distance2, LocalDate startDate, LocalDate endDate,
                   Boolean extra1, Boolean extra2, Boolean extra3, Boolean extra4) {

        this.status.setValue(status);
        this.distance1.setValue(distance1);
        this.distance2.setValue(distance2);
        this.startDate.setValue(startDate);
        this.startDate.setValue(endDate);
        this.extra1.setValue(extra1);
        this.extra2.setValue(extra2);
        this.extra3.setValue(extra3);
        this.extra4.setValue(extra4);
        setAmount(999);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public double getDistance1() {
        return distance1.get();
    }

    public DoubleProperty distance1Property() {
        return distance1;
    }

    public void setDistance1(double distance1) {
        this.distance1.set(distance1);
    }

    public double getDistance2() {
        return distance2.get();
    }

    public DoubleProperty distance2Property() {
        return distance2;
    }

    public void setDistance2(double distance2) {
        this.distance2.set(distance2);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public boolean isExtra1() {
        return extra1.get();
    }

    public BooleanProperty extra1Property() {
        return extra1;
    }

    public void setExtra1(boolean extra1) {
        this.extra1.set(extra1);
    }

    public boolean isExtra2() {
        return extra2.get();
    }

    public BooleanProperty extra2Property() {
        return extra2;
    }

    public void setExtra2(boolean extra2) {
        this.extra2.set(extra2);
    }

    public boolean isExtra3() {
        return extra3.get();
    }

    public BooleanProperty extra3Property() {
        return extra3;
    }

    public void setExtra3(boolean extra3) {
        this.extra3.set(extra3);
    }

    public boolean isExtra4() {
        return extra4.get();
    }

    public BooleanProperty extra4Property() {
        return extra4;
    }

    public void setExtra4(boolean extra4) {
        this.extra4.set(extra4);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    boolean isWithinRange(LocalDate startDate, LocalDate endDate) {
        return !(startDate.isBefore(getStartDate()) || startDate.isAfter(getEndDate())
        || endDate.isBefore(getStartDate()) || endDate.isAfter(getEndDate()));
    }


    private void setListeners() {

        status.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "status", newValue);
                });
        distance1.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "distance1", newValue.toString());
                });
        distance2.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "distance2", newValue.toString());
                });
        startDate.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "startDate", newValue.toString());
                });
        endDate.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "endDate", newValue.toString());
                });

        extra1.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "extra1", newValue.toString());
                });
        extra2.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "extra2", newValue.toString());
                });
        extra3.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "extra3", newValue.toString());
                });
        extra4.addListener(
                (v, oldValue, newValue) -> {
                    Bookings.getInstance().updateBookings(this, "extra4", newValue.toString());
                });
    }



}
