package sample;

public class Admin {
    private String ID;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String office;
    private String location;
    private String phone_number;

    public Admin(String ID, String firstname, String lastname, String username, String password, String office, String location, String phone_number) {
        System.out.println(office);
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.office = office;
        this.location = location;
        this.phone_number = phone_number;
    }

    public Admin() {

    }

    public Admin(String fn_ln_sp) {
        String[] strings = fn_ln_sp.split("\\s+");
        this.firstname = strings[0];
        this.lastname = strings[1];
        this.office = strings[2].replaceAll("[()]", "");
    }

    public Admin(String ID, String firstname, String lastname, String office, String location, String phone_number) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.office = office;
        this.location = location;
        this.phone_number = phone_number;
    }

    public Admin(Admin admin) {
        this.ID = admin.getID();
        this.firstname = admin.getFirstname();
        this.lastname = admin.getLastname();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.office = admin.getOffice();
        this.location = admin.getLocation();
        this.phone_number = admin.phone_number;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
