package mk.frizer.service;

import mk.frizer.domain.Appointment;
import mk.frizer.domain.BaseUser;
import mk.frizer.domain.Customer;
import mk.frizer.domain.enums.Role;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(Long id);
    Optional<Customer> getCustomerByBaseUserId(Long id);
    Optional<Customer> createCustomer(Long baseUserId);
    Optional<Customer> deleteCustomerById(Long id);
    Optional<Customer> addActiveAppointmentForCustomer(Appointment appointment);
    Optional<Customer> addHistoryAppointmentForCustomer(Appointment appointment);
    Optional<Customer> getCustomerByEmail(String email);
}
