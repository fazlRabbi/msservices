package no.hib.logic;

import no.hib.logic.interfaces.AppointmentService;
import no.hib.models.Appointment;
import no.hib.models.AppointmentPreperation;
import no.hib.repository.AppointmentPreperationRepository;
import no.hib.repository.AppointmentRepository;
import no.hib.repository.DoctorRepository;
import no.hib.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("appointmentService")
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentPreperationRepository appointmentPreperationRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        String uuid = UUID.randomUUID().toString();
        appointment.setUuid(uuid);
        appointment.set_id(uuid);

        AppointmentPreperation appointmentPreperation = new AppointmentPreperation(appointment.getUuid(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime());
        String newUuid = UUID.randomUUID().toString();
        appointmentPreperation.setUuid(newUuid);
        appointment.setAppointmentPreperation(appointmentPreperation);
        appointmentRepository.saveAppointment(appointment);
        return appointment;
    }

    @Override
    public List<Appointment> getAppointments() {
        List<Appointment> appointments = appointmentRepository.getAppointments();
        return appointments;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        AppointmentPreperation appointmentPreperation = appointmentPreperationRepository.getAppointmentPreperationFromAppointmentUuid(appointment.getUuid());
        appointmentPreperation.setAppointmentDate(appointment.getAppointmentDate());
        appointmentPreperation.setAppointmentTime(appointment.getAppointmentTime());
        appointment.setAppointmentPreperation(appointmentPreperation);
        appointmentRepository.updateAppointment(appointment);
        return appointment;
    }

    @Override
    public Appointment getAppointment(String uuid) {
        Appointment appointment = appointmentRepository.getAppointment(uuid);
        return appointment;
    }

    @Override
    public void deleteAppointment(String uuid) {
        appointmentRepository.deleteAppointment(uuid);
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(String patientSsn) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsForPatient(patientSsn);
        return appointments;
    }
}
