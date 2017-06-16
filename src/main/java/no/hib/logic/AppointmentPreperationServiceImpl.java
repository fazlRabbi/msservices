package no.hib.logic;

import no.hib.logic.interfaces.AppointmentPreperationService;
import no.hib.models.AppointmentPreperation;
import no.hib.models.OtherSubject;
import no.hib.models.Symptom;
import no.hib.repository.AppointmentPreperationRepository;
import no.hib.utils.UtfConverter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("appointmentPreperationService")
@Transactional
public class AppointmentPreperationServiceImpl implements AppointmentPreperationService {

    @Autowired
    AppointmentPreperationRepository appointmentPreperationRepository;

    @Override
    public AppointmentPreperation getAppointmentPreperation(String uuid) {
        AppointmentPreperation appointmentPreperation = appointmentPreperationRepository.getAppointmentPreperation(uuid);
        appointmentPreperation = convertToUtf(appointmentPreperation);
        return appointmentPreperation;
    }

    @Override
    public AppointmentPreperation updateAppointmentPreperation(AppointmentPreperation appointmentPreperation) {
        AppointmentPreperation old = getAppointmentPreperation(appointmentPreperation.getUuid());
        old = convertToUtf(old);
        old = replaceUpdatedSymptomsAndAddNewOnes(old, appointmentPreperation);
        old = removeOldSymptoms(old, appointmentPreperation);

        old = replaveUpdatedOthersAndAddNewOnes(old, appointmentPreperation);
        old = removeOldOthers(old, appointmentPreperation);

        appointmentPreperation.setSymptoms(old.getSymptoms());
        appointmentPreperation.setOtherSubjects(old.getOtherSubjects());
        appointmentPreperation = convertFromUtf(appointmentPreperation);
        appointmentPreperationRepository.updateAppointmentPreperation(appointmentPreperation);
        return appointmentPreperation;
    }

    @Override
    public List<AppointmentPreperation> getAppointmentPreperations(String patientSsn) {
        List<AppointmentPreperation> appointmentPreperations = appointmentPreperationRepository.getAppointmentPreperations(patientSsn);
        List<AppointmentPreperation> utfVersions = new ArrayList<>();
        for (AppointmentPreperation appointmentPreperation : appointmentPreperations) {
            utfVersions.add(convertToUtf(appointmentPreperation));
        }
        return utfVersions;
    }

    @Override
    public AppointmentPreperation getNextAppointmentPreperation(String patientSsn) {
        List<AppointmentPreperation> appointmentPreperations = getAppointmentPreperations(patientSsn);
        AppointmentPreperation next = null;
        DateTime nextDateTime = null;
        DateTime now = new DateTime();

        for (AppointmentPreperation appointmentPreperation : appointmentPreperations) {

            DateTime appointmentTime = new DateTime(appointmentPreperation.getAppointmentDate().getYear(),
                    appointmentPreperation.getAppointmentDate().getMonthValue(), appointmentPreperation.getAppointmentDate().getDayOfMonth(),
                    appointmentPreperation.getAppointmentTime().getHour(), appointmentPreperation.getAppointmentTime().getMinute());

            if (next == null && appointmentTime.isAfter(now)) {
                next = appointmentPreperation;
                nextDateTime = appointmentTime;
                continue;
            }
            if (next == null) continue;
            if (appointmentTime.isAfter(now) && nextDateTime.isAfter(appointmentTime)) {
                next = appointmentPreperation;
                nextDateTime = appointmentTime;
            }
        }
        return next;
    }

    private AppointmentPreperation convertToUtf(AppointmentPreperation appointmentPreperation) {
        appointmentPreperation.setNeedForConsultation(UtfConverter.convertToUtf(appointmentPreperation.getNeedForConsultation()));
        appointmentPreperation.setSymptomListUpdated(UtfConverter.convertToUtf(appointmentPreperation.getSymptomListUpdated()));
        appointmentPreperation.setSideEffectsUpdated(UtfConverter.convertToUtf(appointmentPreperation.getSideEffectsUpdated()));
        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
        }

        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
        }
        return appointmentPreperation;
    }

    private AppointmentPreperation convertFromUtf(AppointmentPreperation appointmentPreperation) {
        appointmentPreperation.setNeedForConsultation(UtfConverter.convertFromUtf(appointmentPreperation.getNeedForConsultation()));
        appointmentPreperation.setSymptomListUpdated(UtfConverter.convertFromUtf(appointmentPreperation.getSymptomListUpdated()));
        appointmentPreperation.setSideEffectsUpdated(UtfConverter.convertFromUtf(appointmentPreperation.getSideEffectsUpdated()));

        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
        }

        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
        }
        return appointmentPreperation;
    }

    private AppointmentPreperation removeOldOthers(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        List<OtherSubject> newOthers = new ArrayList<>();
        for (OtherSubject oldOther : old.getOtherSubjects()) {
            if (appointmentPreperation.getOtherSubjects().contains(oldOther)) {
                newOthers.add(oldOther);
            }
        }
        old.setOtherSubjects(newOthers);
        return old;
    }

    private AppointmentPreperation replaveUpdatedOthersAndAddNewOnes(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            if (!old.getOtherSubjects().contains(otherSubject)) {
                String uuid = UUID.randomUUID().toString();
                otherSubject.setUuid(uuid);
                old.addNewOther(otherSubject);
            }
        }
        return old;
    }

    private AppointmentPreperation removeOldSymptoms(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        List<Symptom> newsymptoms = new ArrayList<>();
        for (Symptom oldSymptom : old.getSymptoms()) {
            if (appointmentPreperation.getSymptoms().contains(oldSymptom)) {
                newsymptoms.add(oldSymptom);
            }
        }
        old.setSymptoms(newsymptoms);
        return old;
    }

    private AppointmentPreperation replaceUpdatedSymptomsAndAddNewOnes(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            if (old.getSymptoms().contains(symptom)) { //eksistere fra før (oppdatering)
                old.replaceSymptom(symptom);
            } else {
                String uuid = UUID.randomUUID().toString();
                symptom.setUuid(uuid);
                old.addNewSymptom(symptom); // eksistere ikke i old, en ny versjon
            }
        }
        return old;
    }


}
