package com.avinty.hr.modules.department;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    Optional<Department> findFirstByName(String name);

    @Override
    List<Department> findAll();
}
