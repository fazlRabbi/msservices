package no.hib.models;

import java.util.List;

public class Settings {
    private String _id;
    private String _rev;
    private String uuid;
    private int appointmentPreperationMinStart;
    private List<Symptom> defaultSymptoms;
    private List<OtherSubject> defaultOtherSubjects;

    public Settings(int appointmentPreperationMinStart, List<Symptom> defaultSymptoms, List<OtherSubject> defaultOtherSubjects) {
        this.appointmentPreperationMinStart = appointmentPreperationMinStart;
        this.defaultSymptoms = defaultSymptoms;
        this.defaultOtherSubjects = defaultOtherSubjects;
    }

    public int getAppointmentPreperationMinStart() {
        return appointmentPreperationMinStart;
    }

    public void setAppointmentPreperationMinStart(int appointmentPreperationMinStart) {
        this.appointmentPreperationMinStart = appointmentPreperationMinStart;
    }

    public List<Symptom> getDefaultSymptoms() {
        return defaultSymptoms;
    }

    public void setDefaultSymptoms(List<Symptom> defaultSymptoms) {
        this.defaultSymptoms = defaultSymptoms;
    }

    public List<OtherSubject> getDefaultOtherSubjects() {
        return defaultOtherSubjects;
    }

    public void setDefaultOtherSubjects(List<OtherSubject> defaultOtherSubjects) {
        this.defaultOtherSubjects = defaultOtherSubjects;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
