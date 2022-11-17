package com.avinty.hr.modules.department.validation;

import com.avinty.hr.modules.employee.Employee;
import com.avinty.hr.modules.employee.Position;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Manager validation implementation
 * @author mredly
 */
public class ManagerValidator implements ConstraintValidator<ManagerValidation, Employee> {

    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(employee))
            return true;
        return Position.MANAGER.equals(employee.getPosition());
    }
}
