//A class that holds specific attributes for library Employeeس

public class Employee extends Person{

    //Attributes for Employees
    private String username;
    private String password;

    //constructor for Initializing fields
    public Employee(int id, String name, String username, String password) {
        super(id, name);
        this.username = username == null ? "" : username;
        this.password = password == null ? "" : password;

    }

    //setter & getter for return and set salary
    public String getUsername() { return username;}
    public String getPassword() { return password;}
    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password) { this.password = password;}

    //methods for read and write info from files
    public String toCSV() {
        return getId() + "," + getName() + "," + username + "," + password;
    }

    public static Employee fromCSV(String line) {
        String[] p = line.split(",", -1);
        int id = Integer.parseInt(p[0]);
        String name = p[1];
        String username = p[2];
        String password = p[3];
        return new Employee(id, name, username, password);
    }

    //override toString method for showing information
    @Override
    public String toString() {
        return super.toString() + " | staff: " + username;
    }
}
