//A class that holds specific attributes for library Employee

public class Employee extends Person{

    //Attribute for show salary
    private double salary;

    //constructor for Initializing fields
    public Employee(int id, String name, String address , double salary) {
        super(id, name, address);
        this.salary = salary;
    }

    //setter & getter for return and set salary

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
