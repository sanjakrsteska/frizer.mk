package mk.frizer.service;

import mk.frizer.domain.Appointment;
import mk.frizer.domain.Employee;
import mk.frizer.domain.Review;
import mk.frizer.domain.Salon;
import mk.frizer.domain.dto.EmployeeAddDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getEmployees();
    List<Employee> getEmployeesByIds(List<Long> ids);
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> getEmployeeByBaseUserId(Long id);
    List<Employee> getEmployeesForSalon(Long id);
    Optional<Employee> createEmployee(EmployeeAddDTO employeeAddDTO);
    Optional<Employee> deleteEmployeeById(Long id);
    Optional<Employee> deleteEmployeeByIdFromSalon(Long id, Long salonId);
    Optional<Employee> addActiveAppointmentForEmployee(Appointment appointment);
    Optional<Employee> addHistoryAppointmentForEmployee(Appointment appointment);
    Optional<Employee> addReview(Review review);
    Optional<Employee> deleteReview(Review review);
    Optional<Employee> updateReview(Review review, Double oldReview);

}
