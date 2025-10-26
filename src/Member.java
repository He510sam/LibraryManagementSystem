//A class that holds specific attributes for library Members

public class Member extends Person{

    //define specific attributes for library Members

    private String membershipId;
    private String phoneNumber;
    private String username;
    private String password;
    private String address;
    private double penalty;


    //constructor for Initializing fields
    public Member(int id, String name, String membershipId, String phone, String address,
                  String username, String password, double penalty) {
                super(id, name);
                this.membershipId = membershipId == null ? "" : membershipId.replace(",", "؛");
                this.phoneNumber = phone == null ? "" : phone.replace(",", "؛");
                this.address = address == null ? "" : address.replace(",", "؛");
                this.username = username == null ? "" : username;
                this.password = password == null ? "" : password;
                this.penalty = penalty;
    }
    //second constructor for read from file
    public Member(int id, String name, String membershipId, String phone, String address,
                  String username, String password) {
        this(id, name, membershipId, phone, address, username, password, 0.0);
    }


    //setter & getter for return and set specific attributes
    public String getMembershipId() { return membershipId; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getPenalty() { return penalty; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public void setMembershipId(String membershipId) { this.membershipId = membershipId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setPenalty(double penalty) { this.penalty = penalty; }

    //method to Checking for fines
    public boolean hasPenalty() { return penalty > 0.0; }

    //methods for read and write info from files
    public String toCSV() {
        return getId() + "," + getName() + "," + membershipId + "," + phoneNumber + "," + address + "," +
                username + "," + password + "," + penalty;
    }

    public static Member fromCSV(String line) {
        String[] p = line.split(",", -1);
        int id = Integer.parseInt(p[0]);
        String name = p[1];
        String membershipId = p[2];
        String phone = p[3];
        String address = p[4];
        String username = p[5];
        String password = p[6];
        double penalty = p.length > 7 && !p[7].isEmpty() ? Double.parseDouble(p[7]) : 0.0;
        return new Member(id, name, membershipId, phone, address, username, password, penalty);
    }

    //override toString method for showing information
    @Override
    public String toString() {
        return super.toString() + " | " + membershipId + " | user: " + username + " | penalty: " + penalty;
    }
}
