package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.Appointment;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentRepository {

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

    }

    public void saveAppointment(Appointment appointment) {
        try {
            init();
            database.save(appointment);
        }catch (Exception ex){
            System.out.println("==================================== Exception: " + ex);
        }
    }

    public List<Appointment> getAppointments() {
        init();
        String searchString = SearchStringGenerator.getSearchString("appointmentTime","exists","true","appointmentPreperation","exists","true");
        List<Appointment> appointments = database.findByIndex(searchString, Appointment.class);
        return appointments;
    }

    public void updateAppointment(Appointment appointment) {
        init();
        Appointment old = database.find(Appointment.class, appointment.getUuid());
        appointment.set_id(old.get_id());
        appointment.set_rev(old.get_rev());
        database.update(appointment);
    }

    public List<Appointment> getAppointmentsForPatient(String patientSsn) {
        init();
        String searchString = SearchStringGenerator.getSearchString("patientSsn","eq",patientSsn);
        List<Appointment> appointments = database.findByIndex(searchString, Appointment.class);
        return appointments;
    }

    public void deleteAppointment(String uuid) {
        init();
        Appointment appointment = database.find(Appointment.class, uuid);
        database.remove(appointment);
    }

    public Appointment getAppointment(String uuid) {
        init();
        Appointment appointment = database.find(Appointment.class, uuid);
        return appointment;
    }
}
