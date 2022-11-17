package com.avinty.hr.modules.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Filter options for Employee listing
 * @author mredly
 */
@Data
@AllArgsConstructor
public class EmployeeFilter {

    private String fullName, email;
}
