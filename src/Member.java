//A class that holds specific attributes for library Members

public class Member extends Person{

    //define specific attributes for library Members

    private String membershipId;
    private String phoneNumber;

    //constructor for Initializing fields
    public Member(int id, String name, String address, String membershipId
            , String phoneNumber) {
                super(id, name, address);
                this.membershipId = membershipId;
                this.phoneNumber = phoneNumber;
    }

    //setter & getter for return and set specific attributes

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
