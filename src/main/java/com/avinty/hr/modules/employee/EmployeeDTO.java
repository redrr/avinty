package com.avinty.hr.modules.employee;

import lombok.Data;

import java.util.Objects;

@Data
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
