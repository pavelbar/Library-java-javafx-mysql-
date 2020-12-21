package sample;

public class User_z {
    private String ID;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String office;
    private String phone_number;


    public User_z(String ID, String firstname, String lastname, String username, String password, String office, String phone_number) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.office = office;
        this.phone_number = phone_number;
    }

    public User_z(String firstname, String lastname, String username, String password, String office, String phone_number) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.office = office;
        this.phone_number = phone_number;
    }

    public User_z() {
    }

    public User_z(User_z userZ) {
        this.ID = userZ.getID();
        this.firstname = userZ.getFirstname();
        this.lastname = userZ.getLastname();
        this.username = userZ.getUsername();
        this.password = userZ.getPassword();
        this.office = userZ.getOffice();
        this.phone_number = userZ.getPhone_number();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }
}
