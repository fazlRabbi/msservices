package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SettingsRepository {

    private CloudantClient client;
    private Database database;

    private void init() {
//        client = ClientBuilder.account("b6768e5f-ff3f-4b05-8894-f2445cc2d875-bluemix")
//                .username("b6768e5f-ff3f-4b05-8894-f2445cc2d875-bluemix")
//                .password("9a63dfff816095013e2278d65df3d1aa0171ad5a6f03e5c5de111ed5ece9dc4b")
//                .build();
//
//        database = client.database("sample_nosql_db",true);

        database = CloudantClientMgr.getDB();

    }

    public Settings getSettings() {
        init();
        String searchString = SearchStringGenerator.getSearchString("appointmentPreperationMinStart","exists","true");

        List<Settings> settings = database.findByIndex(searchString, Settings.class);
        if (settings.size() == 0) {
            Settings settings1 = createSettings();
            return settings1;
        } else {
            return settings.get(0);

        }
    }

    public Settings createSettings(){
        Settings s = new Settings(3, new ArrayList<Symptom>(), new ArrayList<OtherSubject>());
        database.save(s);
        return s;
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
