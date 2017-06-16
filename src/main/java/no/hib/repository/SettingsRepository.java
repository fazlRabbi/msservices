package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SettingsRepository {

    private CloudantClient client;
    private Database database;

    private void init() {
        client = ClientBuilder.account("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix:91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a@58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .username("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .password("91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a")
                .build();

        database = client.database("msservice",true);
    }

    public Settings getSettings() {
        init();
        String searchString = SearchStringGenerator.getSearchString("appointmentPreperationMinStart","exists","true");
        List<Settings> settings = database.findByIndex(searchString, Settings.class);
        return settings.get(0);
    }

    public Settings updateSettings(Settings settings) {
        init();
        Settings old = getSettings();
        settings.set_id(old.get_id());
        settings.set_rev(old.get_rev());
        settings.setDefaultOtherSubjects(new ArrayList<OtherSubject>());
        settings.setDefaultSymptoms(new ArrayList<Symptom>());
        database.update(settings);
        return settings;
    }

    public void deleteSettings(Settings old) {
        database.remove(old);
    }

    public void save(Settings settings) {
        database.save(settings);
    }

    public void updateSymptom(Symptom symptom) {
        init();
        Symptom old = database.find(Symptom.class, symptom.getUuid());
        symptom.set_id(old.get_id());
        symptom.set_rev(old.get_rev());
        database.update(symptom);
    }

    public void updateSubject(OtherSubject otherSubject) {
        init();
        OtherSubject old = database.find(OtherSubject.class, otherSubject.getUuid());
        otherSubject.set_id(old.get_id());
        otherSubject.set_rev(old.get_rev());
        database.update(otherSubject);
    }

    public void removeSubject(OtherSubject old) {
        init();
        database.remove(old);
    }

    public OtherSubject getSubject(String uuid) {
        init();
        OtherSubject subject = database.find(OtherSubject.class, uuid);
        return subject;
    }

    public Symptom getSymptom(String uuid) {
        init();
        Symptom symptom = database.find(Symptom.class, uuid);
        return symptom;
    }

    public void removeSymptom(Symptom old) {
        init();
        database.remove(old);
    }

    public void save(OtherSubject otherSubject) {
        init();
        database.save(otherSubject);
    }

    public void save(Symptom symptom) {
        init();
        database.save(symptom);
    }

    public List<Symptom> getSymptoms() {
        init();
        String searchString = SearchStringGenerator.getSearchString("severity","exists","true");
        List<Symptom> symptoms = database.findByIndex(searchString, Symptom.class);
        return symptoms;
    }

    public List<OtherSubject> getSubjects() {
        init();
        String searchString = SearchStringGenerator.getSearchString("severity","exists","false", "name","exists","true");
        List<OtherSubject> subjects = database.findByIndex(searchString, OtherSubject.class);
        return subjects;
    }
}
