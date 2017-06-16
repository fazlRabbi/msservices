package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.Doctor;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRepository {

    private CloudantClient client;
    private Database database;

    private void init() {
        client = ClientBuilder.account("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix:91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a@58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .username("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .password("91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a")
                .build();

        database = client.database("msservice",true);
    }

    public Doctor getDoctor(String ssn) {
        init();
        String searchString = SearchStringGenerator.getSearchString("ssn","eq",ssn, "specialization", "exists", "true");
        List<Doctor> doctors = database.findByIndex(searchString, Doctor.class);
        if(doctors.size() == 0) return null;
        return doctors.get(0);
    }

    public Doctor createDoctor(Doctor doctor) {
        init();
        database.save(doctor);
        return doctor;
    }

    public Doctor updateDoctor(Doctor doctor) {
        init();
        Doctor old = getDoctor(doctor.getSsn());
        doctor.set_id(old.get_id());
        doctor.set_rev(old.get_rev());
        database.update(doctor);
        return doctor;
    }

    public List<Doctor> getDoctors() {
        init();
        String searchString = SearchStringGenerator.getSearchString("specialization", "exists", "true");
        List<Doctor> doctors = database.findByIndex(searchString, Doctor.class);
        return doctors;
    }

    public void deleteDoctor(String ssn) {
        init();
        Doctor doctor = getDoctor(ssn);
        database.remove(doctor);
    }
}
