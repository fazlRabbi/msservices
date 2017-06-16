package no.hib.models;

public class Doctor {
    private String _id;
    private String _rev;
    private String uuid;
    private String ssn;
    private String firstName;
    private String lastName;
    private String specialization;

    public Doctor(String uuid, String ssn, String firstName, String lastName, String specialization) {
        this.uuid = uuid;
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Doctor(String ssn, String firstName, String lastName, String specialization) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }
}
