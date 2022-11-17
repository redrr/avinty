package com.avinty.hr.modules.department;

import com.avinty.hr.modules.employee.EmployeeDTO;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Department representation for REST API
 * @author mredly
 */
@Data
@FieldNameConstants
public class DepartmentDTO {

    private String name;
    private EmployeeDTO manager;
    private List<EmployeeDTO> employees;

    public DepartmentDTO(Department department) {
        if (Objects.nonNull(department)) {
            this.name = department.getName();
            this.manager = new EmployeeDTO(department.getManager());
            this.employees = department.getEmployees().stream()
                    .map(EmployeeDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
