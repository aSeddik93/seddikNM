
package model;

import databaseConnection.Customers;
import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Created by Antonia on 19-05-2017.
 */

public class Customer {
    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObjectProperty<LocalDate> dobProperty() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob.set(dob);
    }

    public IntegerProperty telProperty() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel.set(tel);
    }

    private StringProperty title = new SimpleStringProperty(this, "title", "unknown");
    private StringProperty name = new SimpleStringProperty(this, "name", "unknown");
    private StringProperty email = new SimpleStringProperty(this, "email", "unknown");
    private ObjectProperty<LocalDate> dob = new SimpleObjectProperty<>(this, "dob", null);
    private IntegerProperty tel = new SimpleIntegerProperty(this, "tel", -1);


    private int id;

    public Customer(String title, String name, String email,LocalDate dob, String tel) {
        this.title.setValue(title);
        this.name.setValue(name);
        this.email.setValue(email);
        this.dob.setValue(dob);
        this.tel.setValue(Integer.parseInt(tel));
        setListeners();
    }

    public Customer(String title, String name, String email,String dob, int tel, int id) {
        this.title.setValue(title);
        this.name.setValue(name);
        this.email.setValue(email);
        this.dob.setValue(LocalDate.parse(dob));
        this.tel.setValue(tel);

        this.id = id;
        setListeners();
    }

    public LocalDate getDob() {
        return dob.get();
    }

    public int getTel() {
        return tel.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getname() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "Title=" + title.getValue() +
                ", Name" + name.getValue() +
                ", Email=" + email.getValue() +
                '}';
    }

    private void setListeners() {
        title.addListener(
                (v, oldValue, newValue) -> {
                    Customers.getInstance().updateCustomers(this, "title", newValue);
                });
        name.addListener(
                (v, oldValue, newValue) -> {
                    Customers.getInstance().updateCustomers(this, "name", newValue);
                });
        email.addListener(
                (v, oldValue, newValue) -> {
                    Customers.getInstance().updateCustomers(this, "email", newValue);
                });
        dob.addListener(
                (v, oldValue, newValue) -> {
                    Customers.getInstance().updateCustomers(this, "dob", newValue.toString());
                });
        tel.addListener(
                (v, oldValue, newValue) -> {
                    Customers.getInstance().updateCustomers(this, "title", newValue.toString());
                });

    }
}