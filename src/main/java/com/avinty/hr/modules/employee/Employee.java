package com.avinty.hr.modules.employee;

import com.avinty.hr.modules.BaseModel;
import com.avinty.hr.modules.department.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
public class Employee extends BaseModel {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee detachFromDepartment() {
        this.department = null;
        return this;
    }
}
