//Abstract class for everyone in the system

public abstract class Person {
    //add attributes

    private int id;
    private String name;
    private String address;
    //constructor for Initializing fields

    public Person(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }
    //setter & getter for return and set attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
