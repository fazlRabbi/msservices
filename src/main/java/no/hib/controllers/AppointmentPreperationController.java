package no.hib.controllers;

import no.hib.logic.interfaces.AppointmentPreperationService;
import no.hib.models.AppointmentPreperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/preperation")
public class AppointmentPreperationController {

    @Autowired
    AppointmentPreperationService appointmentPreperationService;

    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}", produces = "application/json; charset=utf-8")
    public ResponseEntity<AppointmentPreperation> getAppointmentPreperation(@PathVariable(value = "uuid") String uuid){
        AppointmentPreperation appointmentPreperation = appointmentPreperationService.getAppointmentPreperation(uuid);
        return new ResponseEntity<>(appointmentPreperation, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/all")
    public ResponseEntity<List<AppointmentPreperation>> getAppointmentPreperations(@PathVariable(value = "patientSsn") String patientSsn){
        List<AppointmentPreperation> appointmentPreperations = appointmentPreperationService.getAppointmentPreperations(patientSsn);
        return new ResponseEntity<>(appointmentPreperations, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/next")
    public ResponseEntity<AppointmentPreperation> getNextAppointmentPreperation(@PathVariable(value = "patientSsn") String patientSsn){
        AppointmentPreperation appointmentPreperation = appointmentPreperationService.getNextAppointmentPreperation(patientSsn);
        return new ResponseEntity<>(appointmentPreperation, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
    public ResponseEntity<AppointmentPreperation> updateAppointmentPreperation(@PathVariable(value = "uuid") String uuid,
                                                         @RequestBody AppointmentPreperation appointmentPreperation){
        if(!uuid.equals(appointmentPreperation.getUuid())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AppointmentPreperation updated = appointmentPreperationService.updateAppointmentPreperation(appointmentPreperation);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
