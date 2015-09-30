package be.foreseegroup.micro.resourceservice.customersidecomposite.model;

/**
 * Created by Kaj on 29/09/15.
 */
public class Consultant {
    private String id;

    private String firstname;

    private String lastname;

    private String address;

    public Consultant() {

    }

    public Consultant(Consultant consultant) {
        this.id = consultant.getId();
        this.firstname = consultant.getFirstname();
        this.lastname = consultant.getLastname();
        this.address = consultant.getAddress();
    }

    public Consultant(String firstname, String lastname, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public Consultant(String id, String firstname, String lastname, String address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}