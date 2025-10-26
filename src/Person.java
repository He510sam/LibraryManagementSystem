//Abstract class for everyone in the system

public abstract class Person {
    //add attributes

    private int id;
    private String name;
    //constructor for Initializing fields

    public Person(int id, String name){
        this.id = id;
        this.name = name == null ? "" : name.replace(",", "؛");
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
        this.name = name == null ? "" : name.replace(",", "؛");
    }


    //override toString method for showing information
    @Override
    public String toString() {
        return id + " - " + name;
    }

}
