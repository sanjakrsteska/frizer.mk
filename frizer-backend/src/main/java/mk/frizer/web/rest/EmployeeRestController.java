package mk.frizer.web.rest;

import mk.frizer.domain.Customer;
import mk.frizer.domain.Employee;
import mk.frizer.domain.dto.EmployeeAddDTO;
import mk.frizer.domain.dto.simple.EmployeeSimpleDTO;
import mk.frizer.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping({"/api/employees", "/api/employee" })
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class EmployeeRestController {
    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<EmployeeSimpleDTO> getAllEmployees() {
        return employeeService.getEmployees().stream().map(Employee::toDto).toList();
    }
    @GetMapping("/ids")
    public List<EmployeeSimpleDTO> getAllEmployeesByIds(@RequestParam(required = false) List<Long> ids) {
       if(ids == null || ids.isEmpty()) {
           return new ArrayList<>();
       }
        return employeeService.getEmployeesByIds(ids).stream().map(Employee::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeSimpleDTO> getEmployee(@PathVariable Long id){
        return this.employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok().body(employee.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeSimpleDTO> createEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        return this.employeeService.createEmployee(employeeAddDTO)
                .map(employee -> ResponseEntity.ok().body(employee.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmployeeSimpleDTO> deleteEmployeeById(@PathVariable Long id) {
        return this.employeeService.deleteEmployeeById(id)
                .map(employee -> ResponseEntity.ok().body(employee.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
