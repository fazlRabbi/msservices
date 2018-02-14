package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.Appointment;
import no.hib.models.AppointmentPreperation;
import no.hib.utils.SearchStringGenerator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentPreperationRepository {

    private CloudantClient client;
    private Database database;

    private void init() {
//        client = ClientBuilder.account("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix:91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a@58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
//                .username("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
//                .password("91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a")
//                .build();
//
//        database = client.database("msservice",true);

        database = CloudantClientMgr.getDB();
        if(database==null)
            System.out.println("Database was null");
    }

    public List<AppointmentPreperation> getAppointmentPreperations(String patientSsn) {
        init();
        String searchString = SearchStringGenerator.getSearchString("patientSsn","eq",patientSsn, "appointmentTime","exists","true");
        List<Appointment> appointments = database.findByIndex(searchString, Appointment.class);
        List<AppointmentPreperation> appointmentPreperations = new ArrayList<>();
        for(Appointment appointment : appointments){
            appointmentPreperations.add(appointment.getAppointmentPreperation());
        }
        return appointmentPreperations;
    }

    public void updateAppointmentPreperation(AppointmentPreperation appointmentPreperation) {
        init();
        Appointment oldAppointment = database.find(Appointment.class, appointmentPreperation.getAppointmentUuid());
        AppointmentPreperation oldPrep = oldAppointment.getAppointmentPreperation();

        if(appointmentPreperation.getNeedForConsultation() != null ){
            oldPrep.setNeedForConsultation(appointmentPreperation.getNeedForConsultation());
        }

        if(appointmentPreperation.getSymptoms() != null){
            oldPrep.setSymptoms(appointmentPreperation.getSymptoms());
        }

        if(appointmentPreperation.getSymptomListUpdated() != null ){
            oldPrep.setSymptomListUpdated(appointmentPreperation.getSymptomListUpdated());
        }

        if(appointmentPreperation.getHasSideEffects() != null ){
            oldPrep.setHasSideEffects(appointmentPreperation.getHasSideEffects());
        }

        if(appointmentPreperation.getNewSideEffectsDegree() != null ){
            oldPrep.setNewSideEffectsDegree(appointmentPreperation.getNewSideEffectsDegree());
        }

        if(appointmentPreperation.getOldSideEffectsDegree() != null ){
            oldPrep.setOldSideEffectsDegree(appointmentPreperation.getOldSideEffectsDegree());
        }

        if(appointmentPreperation.getSideEffectsNote() != null ){
            oldPrep.setSideEffectsNote(appointmentPreperation.getSideEffectsNote());
        }

        if(appointmentPreperation.getSideEffectsUpdated() != null ){
            oldPrep.setSideEffectsUpdated(appointmentPreperation.getSideEffectsUpdated());
        }

        if(appointmentPreperation.getOtherSubjects() != null){
            oldPrep.setOtherSubjects(appointmentPreperation.getOtherSubjects());
        }

        if(appointmentPreperation.getOtherSubjectsNote() != null ){
            oldPrep.setOtherSubjectsNote(appointmentPreperation.getOtherSubjectsNote());
        }

        oldAppointment.setAppointmentPreperation(oldPrep);
        database.update(oldAppointment);
    }

    public AppointmentPreperation getAppointmentPreperation(String uuid) {
        init();
        String searchString = SearchStringGenerator.getSearchString("appointmentPreperation.uuid","eq",uuid);
        List<Appointment> appointments = database.findByIndex(searchString, Appointment.class);
        AppointmentPreperation appointmentPreperation = appointments.get(0).getAppointmentPreperation();
        return appointmentPreperation;
    }

    public AppointmentPreperation getAppointmentPreperationFromAppointmentUuid(String uuid) {
        init();
        String searchString = SearchStringGenerator.getSearchString("appointmentPreperation.appointmentUuid","eq",uuid);
        List<Appointment> appointments = database.findByIndex(searchString, Appointment.class);
        AppointmentPreperation appointmentPreperation = appointments.get(0).getAppointmentPreperation();
        return appointmentPreperation;
    }
}
