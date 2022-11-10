package com.avinty.hr.modules.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeFilter {

    private String fullName, email;
}
