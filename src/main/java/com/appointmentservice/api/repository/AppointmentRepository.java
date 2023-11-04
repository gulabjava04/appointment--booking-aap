package com.appointmentservice.api.repository;

import com.appointmentservice.api.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Add custom repository methods if needed
}
