package com.avinty.hr.modules.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<EmployeeDTO> filterAll(EmployeeFilter employeeFilter) {
        repository.asd();
        return repository.findAll(new EmployeeSpecification(employeeFilter)).stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }
}
