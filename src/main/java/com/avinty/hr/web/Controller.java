package com.avinty.hr.web;

import com.avinty.hr.modules.department.Department;
import com.avinty.hr.modules.department.DepartmentDTO;
import com.avinty.hr.modules.department.DepartmentService;
import com.avinty.hr.modules.employee.Employee;
import com.avinty.hr.modules.employee.EmployeeDTO;
import com.avinty.hr.modules.employee.EmployeeFilter;
import com.avinty.hr.modules.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> listEmployees(@RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        return ResponseEntity.ok(employeeService.filterAll(new EmployeeFilter(name, email)));
    }

    @GetMapping("/dep-emp")
    public ResponseEntity<List<DepartmentDTO>> listDepartmentEmployees() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/department")
    public ResponseEntity<DepartmentDTO> getDepartment(@RequestParam String name) {
        return ResponseEntity.ok(departmentService.findByName(name));
    }

    @PatchMapping("/department/set-manager/{employeeId}")
    public ResponseEntity<?> setDepartmentManager(@PathVariable Integer employeeId) {
        Employee employee = employeeService.getRepository().findById(employeeId).orElseThrow();
        Department department = employee.getDepartment();
        department.setManager(employee);
        return ResponseEntity.ok(new DepartmentDTO(departmentService.getRepository().save(department)));
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        Department department = departmentService.getRepository().findById(id).orElseThrow();
        List<Employee> employees = department.getEmployees().stream()
                .map(Employee::detachFromDepartment)
                .collect(Collectors.toList());
        employeeService.getRepository().saveAll(employees);
        department.setEmployees(new HashSet<>());
        departmentService.getRepository().delete(department);
        return ResponseEntity.ok().build();
    }
}
