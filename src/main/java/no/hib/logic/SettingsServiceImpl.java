package no.hib.logic;

import no.hib.logic.interfaces.SettingsService;
import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;
import no.hib.repository.SettingsRepository;
import no.hib.utils.UtfConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("settingsService")
@Transactional
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    SettingsRepository settingsRepository;

    @Override
    public Settings getSettings() {
        Settings settings = settingsRepository.getSettings();
        List<OtherSubject> subjects = settingsRepository.getSubjects();
        List<Symptom> symptoms = settingsRepository.getSymptoms();

        settings.setDefaultOtherSubjects(subjects);
        settings.setDefaultSymptoms(symptoms);
        Settings utf = convertToUtfSettings(settings);
        return utf;
    }

    @Override
    public Settings updateSettings(Settings settings) {
        Settings utf = convertFromUtfSettings(settings);
        Settings updated = settingsRepository.updateSettings(utf);
        return updated;
    }

    @Override
    public void deleteSubject(String uuid) {
        OtherSubject old = settingsRepository.getSubject(uuid);
        settingsRepository.removeSubject(old);
    }

    @Override
    public void deleteSymptom(String uuid) {
        Symptom old = settingsRepository.getSymptom(uuid);
        settingsRepository.removeSymptom(old);
    }

    @Override
    public OtherSubject updateSubject(OtherSubject otherSubject) {
        OtherSubject utf = convertFromUtfOtherSubject(otherSubject);
        settingsRepository.updateSubject(utf);
        return otherSubject;
    }

    @Override
    public Symptom updateSymptom(Symptom symptom) {
        symptom.setSeverity("");
        symptom.setChange("");
        Symptom utf = convertFromUtfSymptom(symptom);
        settingsRepository.updateSymptom(utf);
        return symptom;
    }

    @Override
    public Symptom addSymptom(Symptom symptom) {
        String uuid = UUID.randomUUID().toString();
        symptom.set_id(uuid);
        symptom.setUuid(uuid);
        symptom.setSeverity("");
        symptom.setChange("");

        Symptom utf = convertFromUtfSymptom(symptom);
        settingsRepository.save(utf);
        return symptom;
    }

    @Override
    public OtherSubject addSubject(OtherSubject otherSubject) {
        String uuid = UUID.randomUUID().toString();
        otherSubject.set_id(uuid);
        otherSubject.setUuid(uuid);
        OtherSubject utf = convertFromUtfOtherSubject(otherSubject);
        settingsRepository.save(utf);
        return otherSubject;
    }

    private Settings convertToUtfSettings(Settings settings) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : settings.getDefaultSymptoms()) {
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }

        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : settings.getDefaultOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        settings.setDefaultSymptoms(nonSymptoms);
        settings.setDefaultOtherSubjects(nonOthers);

        return settings;
    }

    private Settings convertFromUtfSettings(Settings settings) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : settings.getDefaultSymptoms()) {
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }

        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : settings.getDefaultOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        settings.setDefaultSymptoms(nonSymptoms);
        settings.setDefaultOtherSubjects(nonOthers);

        return settings;
    }


    private List<Symptom> convertToUtfSymptoms(List<Symptom> symptoms) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : symptoms) {
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }
        return nonSymptoms;
    }

    private List<Symptom> convertFromUtfSymptoms(List<Symptom> symptoms) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : symptoms) {
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }
        return nonSymptoms;
    }

    private List<OtherSubject> convertToUtfOtherSubjects(List<OtherSubject> otherSubjects) {
        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : otherSubjects) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }
        return nonOthers;
    }

    private List<OtherSubject> convertFromUtfOtherSubjects(List<OtherSubject> otherSubjects) {
        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : otherSubjects) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        return nonOthers;
    }

    private Symptom convertToUtfSymptom(Symptom symptom) {
        symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
        symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
        return symptom;
    }

    private Symptom convertFromUtfSymptom(Symptom symptom) {
        symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
        symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
        return symptom;
    }

    private OtherSubject convertToUtfOtherSubject(OtherSubject otherSubject) {
        otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
        return otherSubject;
    }

    private OtherSubject convertFromUtfOtherSubject(OtherSubject otherSubject) {
        otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
        return otherSubject;
    }
}
