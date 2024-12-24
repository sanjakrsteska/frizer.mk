package mk.frizer.repository;

import mk.frizer.domain.Appointment;
import mk.frizer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.customer LEFT JOIN FETCH a.employee LEFT JOIN FETCH a.salon LEFT JOIN a.treatment WHERE a.customer.baseUser.id = :id")
    List<Appointment> getAllAppointmentsForCustomer(@Param("id") Long id);

    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.customer LEFT JOIN FETCH a.employee LEFT JOIN FETCH a.salon LEFT JOIN a.treatment WHERE a.employee.baseUser.id = :id")
    List<Appointment> getAllAppointmentsForEmployee(@Param("id") Long id);
}
