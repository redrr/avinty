package com.avinty.hr.web;

import com.avinty.hr.config.Constants;
import com.avinty.hr.config.exception.AvintyException;
import com.avinty.hr.modules.department.Department;
import com.avinty.hr.modules.department.DepartmentDTO;
import com.avinty.hr.modules.department.DepartmentService;
import com.avinty.hr.modules.employee.Employee;
import com.avinty.hr.modules.employee.EmployeeDTO;
import com.avinty.hr.modules.employee.EmployeeFilter;
import com.avinty.hr.modules.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API methods to manage the HR application
 * @author mredly
 */
@RestController
@RequiredArgsConstructor
public class MainController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    // List all Employees
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping(Constants.ALL_EMPLOYEES_URL)
    public ResponseEntity<List<EmployeeDTO>> listEmployees(@RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        return ResponseEntity.ok(employeeService.filterAll(new EmployeeFilter(name, email)));
    }

    // List all Departments all employees
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping(Constants.ALL_DEPARTMENT_EMPLOYEES_URL)
    public ResponseEntity<List<DepartmentDTO>> listDepartmentEmployees() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    // List all departments
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping(Constants.DEPARTMENT_URL)
    public ResponseEntity<DepartmentDTO> getDepartment(@RequestParam String name) throws AvintyException {
        return ResponseEntity.ok(departmentService.findByName(name));
    }

    // Promote an employee to department manager
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping(Constants.DEPARTMENT_MANAGER_URL + Constants.ID_PATH_VARIABLE)
    public ResponseEntity<?> setDepartmentManager(@PathVariable("id") Integer employeeId) throws AvintyException {
        Employee employee = employeeService.getRepository().findById(employeeId).orElseThrow(() -> new AvintyException("Employee not found"));
        Department department = employee.getDepartment();
        department.setManager(employee);
        return ResponseEntity.ok(new DepartmentDTO(departmentService.getRepository().save(department)));
    }

    // Delete a given department
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping(Constants.DEPARTMENT_URL + Constants.ID_PATH_VARIABLE)
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) throws AvintyException {
        Department department = departmentService.getRepository().findById(id).orElseThrow(() -> new AvintyException("Department not found"));
        List<Employee> employees = department.getEmployees().stream()
                .map(Employee::detachFromDepartment)
                .collect(Collectors.toList());
        employeeService.getRepository().saveAll(employees);
        department.setEmployees(new HashSet<>());
        departmentService.getRepository().delete(department);
        return ResponseEntity.ok().build();
    }
}
