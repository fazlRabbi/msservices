package no.hib.logic;

import no.hib.logic.interfaces.DoctorService;
import no.hib.models.Doctor;
import no.hib.repository.DoctorRepository;
import no.hib.utils.UtfConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("doctorService")
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Doctor getDoctor(String ssn) {
        Doctor doctor = doctorRepository.getDoctor(ssn);
        doctor = convertToUtf(doctor);
        return doctor;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        String uuid = UUID.randomUUID().toString();
        doctor.setUuid(uuid);
        doctor.set_id(uuid);
        doctor = convertFromUtf(doctor);
        Doctor created = doctorRepository.createDoctor(doctor);
        return created;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        doctor = convertFromUtf(doctor);
        Doctor updated = doctorRepository.updateDoctor(doctor);
        return updated;
    }

    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> doctors = doctorRepository.getDoctors();
        List<Doctor> utfVersions = new ArrayList<>();
        for(Doctor d : doctors){
            utfVersions.add(convertToUtf(d));
        }
        return utfVersions;
    }

    @Override
    public void deleteDoctor(String ssn) {
        doctorRepository.deleteDoctor(ssn);
    }

    private Doctor convertToUtf(Doctor doctor){
        doctor.setFirstName(UtfConverter.convertToUtf(doctor.getFirstName()));
        doctor.setLastName(UtfConverter.convertToUtf(doctor.getLastName()));
        doctor.setSpecialization(UtfConverter.convertToUtf(doctor.getSpecialization()));
        return doctor;
    }

    private Doctor convertFromUtf(Doctor doctor) {
        doctor.setFirstName(UtfConverter.convertFromUtf(doctor.getFirstName()));
        doctor.setLastName(UtfConverter.convertFromUtf(doctor.getLastName()));
        doctor.setSpecialization(UtfConverter.convertFromUtf(doctor.getSpecialization()));
        return doctor;
    }
}
