package no.hib.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPreperation {
    private String _id;
    private String _rev;
    private String uuid;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String needForConsultation;
    private List<Symptom> symptoms;
    private String symptomListUpdated;
    private String hasSideEffects;
    private String newSideEffectsDegree;
    private String oldSideEffectsDegree;
    private String sideEffectsNote;
    private boolean sideEffectsAreImportant;
    private String sideEffectsUpdated;
    private List<OtherSubject> otherSubjects;
    private String otherSubjectsNote;
    private String appointmentUuid;

    public AppointmentPreperation(String appointmentUuid,LocalDate appointmentDate,LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.symptoms = new ArrayList<>();
        this.otherSubjects = new ArrayList<>();
        this.appointmentUuid = appointmentUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNeedForConsultation() {
        return needForConsultation;
    }

    public void setNeedForConsultation(String needForConsultation) {
        this.needForConsultation = needForConsultation;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptomListUpdated() {
        return symptomListUpdated;
    }

    public void setSymptomListUpdated(String symptomListUpdated) {
        this.symptomListUpdated = symptomListUpdated;
    }

    public String getHasSideEffects() {
        return hasSideEffects;
    }

    public void setHasSideEffects(String hasSideEffects) {
        this.hasSideEffects = hasSideEffects;
    }

    public String getNewSideEffectsDegree() {
        return newSideEffectsDegree;
    }

    public void setNewSideEffectsDegree(String newSideEffectsDegree) {
        this.newSideEffectsDegree = newSideEffectsDegree;
    }

    public String getOldSideEffectsDegree() {
        return oldSideEffectsDegree;
    }

    public void setOldSideEffectsDegree(String oldSideEffectsDegree) {
        this.oldSideEffectsDegree = oldSideEffectsDegree;
    }

    public String getSideEffectsNote() {
        return sideEffectsNote;
    }

    public void setSideEffectsNote(String sideEffectsNote) {
        this.sideEffectsNote = sideEffectsNote;
    }

    public boolean isSideEffectsAreImportant() {
        return sideEffectsAreImportant;
    }

    public void setSideEffectsAreImportant(boolean sideEffectsAreImportant) {
        this.sideEffectsAreImportant = sideEffectsAreImportant;
    }

    public String getSideEffectsUpdated() {
        return sideEffectsUpdated;
    }

    public void setSideEffectsUpdated(String sideEffectsUpdated) {
        this.sideEffectsUpdated = sideEffectsUpdated;
    }

    public List<OtherSubject> getOtherSubjects() {
        return otherSubjects;
    }

    public void setOtherSubjects(List<OtherSubject> otherSubjects) {
        this.otherSubjects = otherSubjects;
    }

    public String getOtherSubjectsNote() {
        return otherSubjectsNote;
    }

    public void setOtherSubjectsNote(String otherSubjectsNote) {
        this.otherSubjectsNote = otherSubjectsNote;
    }

    public String getAppointmentUuid() {
        return appointmentUuid;
    }

    public void setAppointmentUuid(String appointmentUuid) {
        this.appointmentUuid = appointmentUuid;
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

    public void replaceSymptom(Symptom symptom) {
        List<Symptom> newsymptoms = new ArrayList<>();
        for(Symptom s : this.getSymptoms()){
            if(!s.getUuid().equals(symptom.getUuid())){
                newsymptoms.add(s);
            }
        }
        newsymptoms.add(symptom);
        setSymptoms(newsymptoms);
    }

    public void replaceOther(OtherSubject otherSubject) {
        List<OtherSubject> newothers = new ArrayList<>();
        for(OtherSubject o : this.getOtherSubjects()){
            if(!o.getUuid().equals(otherSubject.getUuid())){
                newothers.add(o);
            }
        }
        newothers.add(otherSubject);
        setOtherSubjects(newothers);
    }

    public void addNewSymptom(Symptom symptom) {
        symptoms.add(symptom);
    }

    public void addNewOther(OtherSubject otherSubject){
        otherSubjects.add(otherSubject);
    }
}
