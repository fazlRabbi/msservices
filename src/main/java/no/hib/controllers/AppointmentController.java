package no.hib.controllers;

import com.google.gson.Gson;
import no.hib.logic.interfaces.AppointmentService;
import no.hib.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/appointments")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getAppointments(){
        List<Appointment> appointments = appointmentService.getAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable(value = "uuid") String uuid){
        Appointment appointment = appointmentService.getAppointment(uuid);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/all")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient(@PathVariable(value = "patientSsn") String patientSsn){
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patientSsn);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment){
        System.out.println("Appointment:");
        System.out.println(new Gson().toJson(appointment));
        Appointment created = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable(value = "uuid") String uuid,
                                                         @RequestBody Appointment appointment){
        if(!uuid.equals(appointment.getUuid())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Appointment updated = appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{uuid}")
    public ResponseEntity deleteAppointment(@PathVariable(value = "uuid") String uuid){
        appointmentService.deleteAppointment(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
