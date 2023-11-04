package com.appointmentservice.api.service;

import com.appointmentservice.api.entity.Appointment;
import com.appointmentservice.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {

        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {

        return appointmentRepository.findById(id).orElse(null);
    }

    public void deleteAppointment(Long id) {

        appointmentRepository.deleteById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}


