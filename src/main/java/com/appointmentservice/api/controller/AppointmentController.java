package com.appointmentservice.api.controller;

import com.appointmentservice.api.entity.Appointment;
import com.appointmentservice.api.payload.Doctor;
import com.appointmentservice.api.payload.Patient;
import com.appointmentservice.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final RestTemplate restTemplate;

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(RestTemplate restTemplate,AppointmentService appointmentService) {
        this.restTemplate = restTemplate;
        this.appointmentService = appointmentService;
    }

//http://localhost:8082/appointments
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        ResponseEntity<Patient> patientResponse = restTemplate.getForEntity(
                "http://patient-service/patients/"+appointment.getPatientId(),
                Patient.class

        );
        if (patientResponse.getStatusCode() != HttpStatus.OK || patientResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = patientResponse.getBody();

        ResponseEntity<Doctor> doctorResponse = restTemplate.getForEntity(
                "http://doctor-service/doctors/"+appointment.getDoctorId(),
                Doctor.class

        );
        if (doctorResponse.getStatusCode() != HttpStatus.OK || doctorResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        Doctor doctor = doctorResponse.getBody();

        Appointment newAppointment = appointmentService.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
    }

}