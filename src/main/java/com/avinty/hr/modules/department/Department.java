package com.avinty.hr.modules.department;

import com.avinty.hr.modules.BaseModel;
import com.avinty.hr.modules.department.validation.ManagerValidation;
import com.avinty.hr.modules.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
public class Department extends BaseModel {

    @Column(name = "name", unique = true)
    private String name;

    @ManagerValidation
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();
}
