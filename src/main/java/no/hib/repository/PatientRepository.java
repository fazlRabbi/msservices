package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.Patient;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepository {

    private CloudantClient client;
    private Database database;

    private void init() {
        client = ClientBuilder.account("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix:91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a@58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .username("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .password("91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a")
                .build();

        database = client.database("msservice",true);
    }

    public Patient getPatient(String ssn) {
        init();
        String searchString = SearchStringGenerator.getSearchString("ssn","eq",ssn, "birthday","exists","true");
        List<Patient> patients = database.findByIndex(searchString, Patient.class);
        if(patients.size() == 0) return null;
        return patients.get(0);
    }

    public void deletePatient(String ssn) {
        init();
        Patient patient = getPatient(ssn);
        database.remove(patient);
    }

    public List<Patient> getPatients() {
        init();
        String searchString = SearchStringGenerator.getSearchString("birthday","exists","true");
        List<Patient> patients = database.findByIndex(searchString, Patient.class);
        return patients;
    }

    public Patient updatePatient(Patient patient) {
        init();
        Patient old = getPatient(patient.getSsn());
        patient.set_id(old.get_id());
        patient.set_rev(old.get_rev());
        database.update(patient);
        return patient;
    }

    public Patient createPatient(Patient patient) {
        init();
        database.save(patient);
        return patient;
    }
}
