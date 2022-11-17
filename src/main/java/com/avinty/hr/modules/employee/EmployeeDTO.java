package com.avinty.hr.modules.employee;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Objects;

/**
 * Employee representation for REST API
 * @author mredly
 */
@Data
@FieldNameConstants
public class EmployeeDTO {

    private String fullName, email;
    private Position position;

    public EmployeeDTO(Employee employee) {
        if (Objects.nonNull(employee)) {
            this.fullName = employee.getFullName();
            this.email = employee.getEmail();
            this.position = employee.getPosition();
        }
    }
}
