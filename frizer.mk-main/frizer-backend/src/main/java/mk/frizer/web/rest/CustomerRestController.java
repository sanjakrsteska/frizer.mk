package mk.frizer.web.rest;

import mk.frizer.domain.Customer;
import mk.frizer.domain.dto.simple.CustomerSimpleDTO;
import mk.frizer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/customers", "/api/customer" })
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<CustomerSimpleDTO> getAllCustomers() {
        return customerService.getCustomers().stream().map(Customer::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSimpleDTO> getCustomerById(@PathVariable Long id) {
        return this.customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok().body(customer.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by")
    public ResponseEntity<CustomerSimpleDTO> getCustomerByEmail(@RequestParam String email) {
        return this.customerService.getCustomerByEmail(email)
                .map(customer -> ResponseEntity.ok().body(customer.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<CustomerSimpleDTO> createCustomer(@PathVariable Long id) {
        return this.customerService.createCustomer(id)
                .map(customer -> ResponseEntity.ok().body(customer.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Does this even make sense?
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerSimpleDTO> deleteCustomerById(@PathVariable Long id) {
       return this.customerService.deleteCustomerById(id)
               .map(customer -> ResponseEntity.ok().body(customer.toDto()))
               .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
