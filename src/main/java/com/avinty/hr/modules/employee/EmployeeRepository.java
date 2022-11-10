package com.avinty.hr.modules.employee;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @Query("select d.id from Employee e join Department d on e.department = d")
    public List<Integer> asd();

}
