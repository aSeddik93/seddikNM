package databaseConnection;

import model.Employee;
import model.Mechanic;
import model.Owner;
import model.SalesAssistant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by aSeddik on 13-May-17.
 */
public class Staff {

    List<Employee> staff = new ArrayList();

    public Staff() {
        this.staff.add(new SalesAssistant(1, "salesperson1", 1234));
        this.staff.add(new Owner(2, "bookkeeper", 1234));
        this.staff.add(new SalesAssistant(3, "salesperson2", 1234));
        this.staff.add(new Mechanic(4, "mechanic", 1234));
    }

    public List getStaff() {
        return staff;
    }

}
